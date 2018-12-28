import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Classe utilit&aacute;ria para compacta&ccedil;&atilde;o e descompacta&ccedil;&atilde;o de arquivos ZIP
 *
 * @author Ricardo Artur Staroski
 */
public final class ZipUtils {

    /**
     * Compacta determindado arquivo ou diret&oacute;rio para o arquivo ZIP especificado
     *
     * @param input
     *            O arquivo ou diret&oacute;rio de entrada
     * @param output
     *            O arquivo ZIP de sa&iacute;da
     *
     * @return O checksum da compacta&ccedil;&atilde;o do arquivo
     */
    public static long compress(final File input, final File output) throws IOException {
        if (!input.exists()) {
            throw new IOException(input.getName() + " não existe!");
        }
        if (output.exists()) {
            if (output.isDirectory()) {
                throw new IllegalArgumentException("\"" + output.getAbsolutePath() + "\" não é um arquivo!");
            }
        } else {
            final File parent = output.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            output.createNewFile();
        }
        Checksum checksum = createChecksum();
        final ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(output));
        zip.setLevel(Deflater.BEST_COMPRESSION);
        compressInternal(null, input, zip, checksum);
        zip.finish();
        zip.flush();
        zip.close();
        return checksum.getValue();
    }

    /**
     * Extrai um arquivo ZIP para o diret&oacute;rio especificado
     *
     * @param input
     *            O arquivo ZIP de entrada
     * @param output
     *            O diret&oacute;rio de sa&iacute;da
     * @return O checksum da descompacta&ccedil;&atilde;o do arquivo
     */
    public static long extract(final File input, final File output) throws IOException {
        if (input.exists()) {
            if (input.isDirectory()) {
                throw new IllegalArgumentException("\"" + input.getAbsolutePath() + "\" não é um arquivo!");
            }
        } else {
            throw new IllegalArgumentException("\"" + input.getAbsolutePath() + "\" não existe!");
        }
        if (output.exists()) {
            if (output.isFile()) {
                throw new IllegalArgumentException("\"" + output.getAbsolutePath() + "\" não é um diretório!");
            }
        }
        Checksum checksum = createChecksum();
        final ZipInputStream zip = new ZipInputStream(new FileInputStream(input));
//        extractInternal(zip, output, checksum);
        convertToCsv(zip, output, checksum);
        zip.close();
        return checksum.getValue();
    }

    // Adiciona determinado arquivo ao ZIP
    private static void compressInternal(final String caminho, final File arquivo, final ZipOutputStream zip, Checksum checksum) throws IOException {
        final boolean dir = arquivo.isDirectory();
        String nome = arquivo.getName();
        nome = (caminho != null ? caminho + "/" + nome : nome);
        final ZipEntry item = new ZipEntry(nome + (dir ? "/" : ""));
        item.setTime(arquivo.lastModified());
        zip.putNextEntry(item);
        if (dir) {
            zip.closeEntry();
            final File[] arquivos = arquivo.listFiles();
            for (int i = 0; i < arquivos.length; i++) {
                // recursivamente adiciona outro arquivo ao ZIP
                compressInternal(nome, arquivos[i], zip, checksum);
            }
        } else {
            item.setSize(arquivo.length());
            final FileInputStream entrada = new FileInputStream(arquivo);
            copy(entrada, zip, checksum);
            entrada.close();
            zip.closeEntry();
        }
    }

    /**
     * Copia o conte&uacute;do do stream de entrada para o stream de sa&iacute;da.
     *
     * @param from
     *            O stream de entrada.
     * @param to
     *            O stream de sa&iacute;da.
     * @param checksum
     *            O checksum da escrita.
     * @throws IOException
     */
    private static void copy(InputStream from, OutputStream to, Checksum checksum) throws IOException {
        byte[] bytes = new byte[8192];
        for (int read = -1; (read = from.read(bytes)) != -1; to.write(bytes, 0, read)) {
            checksum.update(bytes, 0, read);
        }
        to.flush();
    }

    private static Checksum createChecksum() {
        return new CRC32();
    }

    // Retira determinado elemento do arquivo ZIP
    private static void extractInternal(final ZipInputStream zip, final File pasta, Checksum checksum) throws IOException {
        ZipEntry elemento = null;
        while ((elemento = zip.getNextEntry()) != null) {
            String nome = elemento.getName();
            nome = nome.replace('/', File.separatorChar);
            nome = nome.replace('\\', File.separatorChar);
            File arquivo = new File(pasta, nome);
            if (elemento.isDirectory()) {
                arquivo.mkdirs();
            } else {
                boolean existe = arquivo.exists();
                if (!existe) {
                    final File parent = arquivo.getParentFile();
                    if (parent != null) {
                        parent.mkdirs();
                    }
                    arquivo.createNewFile();
                }
                boolean oculto = false;
                boolean somenteLeitura = false;
                if (existe) {
                    oculto = arquivo.isHidden();
                    if (oculto) {
                        Files.setAttribute(arquivo.toPath(), "dos:hidden", false);
                    }
                    somenteLeitura = !arquivo.canWrite();
                    if (somenteLeitura) {
                        arquivo.setWritable(true);
                    }
                }

                OutputStream saida = new FileOutputStream(arquivo);
                copy(zip, saida, checksum);
                saida.close();

                if (existe) {
                    if (somenteLeitura) {
                        arquivo.setWritable(false);
                    }
                    if (oculto) {
                        Files.setAttribute(arquivo.toPath(), "dos:hidden", true);
                    }
                }
            }
            arquivo.setLastModified(elemento.getTime());
        }
    }

    // Le o conteudo dentro de um ZIP a fim de converter arquivos TXT para nosso padrão de CSV
    private static void convertToCsv(final ZipInputStream zip, final File pasta, Checksum checksum) throws IOException {
        ZipEntry elemento = null;
        HashMap<String, File> archivMap = new HashMap<>();
        while ((elemento = zip.getNextEntry()) != null) {
            String nome = elemento.getName();
            nome = nome.replace('/', File.separatorChar);
            nome = nome.replace('\\', File.separatorChar);
            File arquivo = new File(pasta, nome);
            if (elemento.isDirectory()) {
                arquivo.mkdirs();
            } else {
                boolean existe = arquivo.exists();
                if (!existe) {
                    final File parent = arquivo.getParentFile();
                    if (parent != null) {
                        parent.mkdirs();
                    }

                    arquivo.createNewFile();
                    arquivo.setReadable(true);

                    if(arquivo.getName().contains("nfs")){
                        archivMap.put("nfs", arquivo);
//                        System.out.println("nfs!");
                    }else if(arquivo.getName().contains("pdvs")){
                        archivMap.put("pdvs", arquivo);
//                        System.out.println("pdvs!");
                    }else if(arquivo.getName().contains("sellout")){
                        archivMap.put("sellout", arquivo);
//                        System.out.println("sellout!");
                    }else if(arquivo.getName().contains("cadprod")){
                        archivMap.put("cadprod", arquivo);
//                        System.out.println("cadprod!");
                    }else if(arquivo.getName().contains("cadsite")){
                        archivMap.put("cadsite", arquivo);
//                        System.out.println("cadsite!");
                    }else if(arquivo.getName().contains("posestq")){
                        archivMap.put("posestq", arquivo);
//                        System.out.println("posestq!");
                    }



                }

                OutputStream saida = new FileOutputStream(arquivo);
                copy(zip, saida, checksum);
                saida.close();

                if(arquivo.getName().contains("cadsite")){

                    BufferedReader br = new BufferedReader(new FileReader(archivMap.get("cadsite")));
                    while(br.ready()){
                        String linha = br.readLine();
                        System.out.println(linha);
                    }
                    br.close();
                }


            }

//            arquivo.delete();
        }

        validatingData(archivMap);

//        BufferedReader br = new BufferedReader(new FileReader(archivMap.get("pdvs")));
//                    String linha = br.readLine();
//                    System.out.println(linha);
//
//                br.close();

    }

    private static void validatingData(HashMap<String,File> map){
        File file;

        ArrayList<String> lines = new ArrayList<>();
    try{
        FileWriter writer = new FileWriter("CSV");

//        TIPO_EMISSAO	TIPO_DOCUMENTO	NUMERO_NOTA	VALOR_NOTA	DATA_EMISSAO	DATA_VENCIMENTO	NOME_SOLICITANTE	EMAIL_SOLICITANTE	NUMERO_PEDIDO	NATUREZA_COMPRA	INSS

        writer.append("TIPO_EMISSAO");
        writer.append(';');
        writer.append("TIPO_DOCUMENTO");
        writer.append(';');
        writer.append("NUMERO_NOTA");
        writer.append(';');
        writer.append("VALOR_NOTA");
        writer.append(';');
        writer.append("DATA_EMISSAO");
        writer.append(';');
        writer.append("DATA_VENCIMENTO");
        writer.append(';');
        writer.append("NOME_SOLICITANTE");
        writer.append(';');
        writer.append("EMAIL_SOLICITANTE");
        writer.append(';');
        writer.append("NUMERO_PEDIDO");
        writer.append(';');
        writer.append("NATUREZA_COMPRA");
        writer.append(';');
        writer.append("INSS");
        writer.append('\n');

        String idGrup = "";

//        String idGrup = getIdGroupFile(map.get("cadsite"));
//        String idCadProd = getIdGroupFile(map.get("cadprod"));
//        String idPosestq = getIdGroupFile(map.get("posestq"));
//        String idSellOut = getIdGroupFile(map.get("sellout"));
//        String idPdv = getIdGroupFile(map.get("pdv"));
//
//        if(idCadProd.equals(idGrup) && idPosestq.equals(idGrup) && idSellOut.equals(idGrup) && idPdv.equals(idGrup)){
//
//            HashMap<String, String[]> linhas = new HashMap<>();
//            String linhaProd = "";
//            String linhaPosestq = "";
//            String linhaSellOut = "";
//            String linhaPdv = "";
//
//
//            Set<String> listCais	=	new LinkedHashSet<>();
////        ArrayList<String> listCais = new ArrayList<>();
//            try{
//
//                BufferedReader brProd = new BufferedReader(new FileReader(map.get("cadprod")));
//                BufferedReader brPosestq = new BufferedReader(new FileReader(map.get("posestq")));
//                BufferedReader brSellOut = new BufferedReader(new FileReader(map.get("sellout")));
//                BufferedReader brPdv = new BufferedReader(new FileReader(map.get("pdv")));
//
//
//
//
//                    //Esse é o loop para percorrer as linhas do arquivo e fazer as validacoes de cada campo para o CSV
//                    while(     (linhaProd = brProd.readLine()) != null
//                            || (linhaPosestq = brPosestq.readLine()) != null
//                            || (linhaSellOut = brSellOut.readLine()) != null
//                            || (linhaPdv = brPdv.readLine()) != null){
//                        if(linhaProd != null && !linhaProd.equals("")){
//                            String[] camposLinhaProd = linhaProd.split(";");
//
//                        }
//
//                        if(linhaPosestq != null && !linhaPosestq.equals("")){
//                            String[] camposLinhaPosestq = linhaPosestq.split(";");
//
//                        }
//
//                        if(linhaSellOut != null && !linhaSellOut.equals("")){
//                            String[] camposLinhaSellOut = linhaSellOut.split(";");
//
//                        }
//
//                        if(linhaPdv != null && !linhaPdv.equals("")){
//                            String[] camposLinhaPdv = linhaPdv.split(";");
//
//                        }
//
//                    }
//
//
//            } catch(IOException e) {
//                e.printStackTrace();
//            }
//
//
//
//        }


        validatingDataCadSite(map.get("cadsite"), writer, idGrup);
        lines = validatingDataSellOut(map.get("sellout"),map.get("pdvs"),writer,idGrup);

        for(String line : lines){
            writer.append(line);
            writer.append('\n');
        }
        writer.flush();
        writer.close();


    }
    catch(IOException e)
    {
        e.printStackTrace();
    }






    }

//    private static String getIdGroupFile(File file){
//        String linha;
//        String idGrup = "";
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            //Pegando o identificador de grupo de arquivos que esta na primeira linha de cada TXT
//            linha = br.readLine();
//            idGrup = linha.split(";")[1];
//            System.out.println("CadSite");
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//        return  idGrup;
//    }

    private static void validatingDataCadSite(File file,FileWriter writer,String idGrup){
        String linha;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //Pegando o identificador de grupo de arquivos que esta na primeira linha de cada TXT

//            linha = br.readLine();
//            linha = br.readLine();

            while(br.ready()){
                linha = br.readLine();
                System.out.println(linha);
            }

//            while (linha.split(";").length < 3){
//                linha = br.readLine();
//                idGrup = linha.split(";")[1];
//            }
            System.out.println("CadSite");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> validatingDataSellOut(File fileSellOut,File filePdv,FileWriter writer,String idGrup){


        ArrayList<String> listLinesCsv = new ArrayList<>();
        HashMap<String, String[]> listLinesPdv = new HashMap<>();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data;


        String linhaFile;
        String idErp = "";
        String daySell;
        String monsthSell;
        String yearSell;
        String cnpjResell;

        String idCai;
        String qtdSell;
        String vlSell;
        String tpCustomer = "";
        String idCustomer;
        String nmCustomer;

        String state;
        String cityCustomer;
        String zipcode;
        String addressCustomer;

        String additional;

        String idForPdv;


//        String[] datasOfPdv;
//        Set<String> listIdForPdv	=	new LinkedHashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileSellOut))) {
            listLinesPdv = searchDataPdv(filePdv,idGrup);
//            linhaFile = br.readLine();
            linhaFile = br.readLine();
            String idGrupFile = linhaFile.split(";")[1];
            //Valida se o arquivo pertence ao mesmo grupo
            if(idGrupFile.equals(idGrup)){



                //Esse é o loop para percorrer as linhas do arquivo e fazer as validacoes de cada campo para o CSV
                while((linhaFile = br.readLine()) != null){
                    String[] campos = linhaFile.split(";");

                    idForPdv = campos[11];
                    String[] camposPdv = listLinesPdv.get(idForPdv);

                    cnpjResell = campos[1];
                    idCustomer = campos[2];
                    //Deixando aplicando a mascara de cnpj ou cpf e identificando o tipo de pessoa
                    formatIdCustomer(idCustomer,tpCustomer);

                    idCai = campos[3];
                    qtdSell = campos[4];

                    //Para pegar o vlSell, devemos pegar o valor por unidade, dividindo o valor total pela total vendido
                    int vlSellUnidade = Integer.parseInt(campos[5]);
                    vlSell = String.valueOf(vlSellUnidade / Integer.parseInt(qtdSell));

                    //O metodo formatDate e para deixar no padrao dd/MM/yyyy
                    data = formato.parse(formatDate(campos[8]));

                    daySell = String.valueOf(data.getDay());
                    monsthSell = String.valueOf(data.getMonth());
                    yearSell = String.valueOf(data.getYear());

                    nmCustomer = campos[12];

                    state = camposPdv[6];
                    cityCustomer = camposPdv[7];
                    addressCustomer = camposPdv[8] + camposPdv[9] + camposPdv[10] + camposPdv[11];
                    zipcode = camposPdv[16];

                    additional = campos[15];

                    listLinesCsv.add(idErp + ";" +daySell + ";" +monsthSell + ";" +yearSell + ";" +idCai + ";" +qtdSell + ";" +vlSell + ";" +tpCustomer + ";" +idCustomer + ";" +nmCustomer + ";" +state + ";" +cityCustomer
                            + ";" +zipcode + ";" +addressCustomer + ";" +additional);

                }

            }else{

            }

        } catch(IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  listLinesCsv;
    }


    private static String formatDate(String date){

        String year = "";
        String month = "";
        String day = "";

        for(char letter:date.toCharArray()){
            if(year.toCharArray().length < 4){
                year = year + letter;
            }

            if(month.toCharArray().length < 2 && year.toCharArray().length == 4){
                month = month + letter;
            }

            if(day.toCharArray().length < 2 && month.toCharArray().length == 2){
                day = day + letter;
            }
        }

        return day + "/" + month + "/" + year;
    }

    private static void formatIdCustomer(String idCustomer, String tpCustomer){
        int count = 0;
        String idCustomerFormated = "";

        if(idCustomer.length() == 13){
            tpCustomer = "F";

            for(char letter:idCustomer.toCharArray()){

                if(count == 2 || count == 5) idCustomerFormated = idCustomerFormated + ".";
                if(count == 8) idCustomerFormated = idCustomerFormated + "/";
                if(count == 12) idCustomerFormated = idCustomerFormated + "-";
                idCustomerFormated = idCustomerFormated + letter;
                count++;
            }

        }else{
            tpCustomer = "J";

            for(char letter:idCustomer.toCharArray()){

                if(count == 3 || count == 6) idCustomerFormated = idCustomerFormated + ".";
                if(count == 9) idCustomerFormated = idCustomerFormated + "-";
                idCustomerFormated = idCustomerFormated + letter;
                count++;
            }
        }

        idCustomer = idCustomerFormated;

    }

    private static HashMap<String, String[]> searchDataPdv(File file, String idGrup){
        HashMap<String, String[]> listLines = new HashMap<>();

        String linha;
        String idPdv;


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            linha = br.readLine();
            String idGrupFile = linha.split(";")[1];
            //Valida se o arquivo pertence ao mesmo grupo
            if(idGrupFile.equals(idGrup)){

                //Esse é o loop para percorrer as linhas do arquivo e fazer as validacoes de cada campo para o CSV
                while((linha = br.readLine()) != null){
                    String[] campos = linha.split(";");
                    idPdv = campos[1];
                    listLines.put(idPdv,campos);

                }

            }else{

            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return listLines;
    }

//    private static Set<String> validatingDataCadProd(File file,FileWriter writer,String idGrup){
//        String linha;
//        Set<String> listCais	=	new LinkedHashSet<>();
////        ArrayList<String> listCais = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//
//            linha = br.readLine();
//            String idGrupFile = linha.split(";")[1];
//            //Valida se o arquivo pertence ao mesmo grupo
//            if(idGrupFile == idGrup){
//
//                //Esse é o loop para percorrer as linhas do arquivo e fazer as validacoes de cada campo para o CSV
//                while((linha = br.readLine()) != null){
//                    String[] campos = linha.split(";");
//
//                    //CAI do produto
//                    String cai = campos[3];
//                    if(cai.length() <= 6){
//                        //Implementar ida ao banco e validacao se existe
//                        String paraCriar = "Implementar ida ao banco e validacao se existe";
//                        if(paraCriar.equals("Implementar ida ao banco e validacao se existe")){
//                            listCais.add(cai);
//                        }else{
//
//                        }
//                    }
//
//
//
//
//
//                }
//
//            }else{
//
//            }
//
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//
//        return listCais;
//    }

//    private static void validatingDataPosestq(File file,FileWriter writer,String idGrup, ArrayList<String> listCais){
//        String linha;
//        Set<String> listCnpjCustomer	=	new LinkedHashSet<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//
//            linha = br.readLine();
//            String idGrupFile = linha.split(";")[1];
//            //Valida se o arquivo pertence ao mesmo grupo
//            if(idGrupFile == idGrup){
//
//                //Esse é o loop para percorrer as linhas do arquivo e fazer as validacoes de cada campo para o CSV
//                while((linha = br.readLine()) != null){
//                    String[] campos = linha.split(";");
//
//                    for (String cai:listCais) {
//                        //Verifica se o CAI da coluna 3 da linha é igual ao CAI valido pego em cadProd
//                        if(campos[3].equals(cai)){
//                            //Pegando CNPJ do cliente
//                            listCnpjCustomer.add(campos[2]);
//                        }
//                    }
//
//
//                }
//
//            }else{
//
//            }
//
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }
















    // Construtor privado - Náo há razão em instanciar esta classe
    private ZipUtils() {}
}