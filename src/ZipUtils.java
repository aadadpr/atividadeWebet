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

            }

//            arquivo.delete();
        }

        validatingData(archivMap);

    }

    private static void validatingData(HashMap<String,File> map){

        ArrayList<String> lines;
    try{
        FileWriter writer = new FileWriter("E:\\Downloads\\STK DE\\DELLAS\\lalalend\\CSV.csv");

        writer.append("ID_ERP_RESELL");
        writer.append(';');
        writer.append("DAY_SELL");
        writer.append(';');
        writer.append("MONTH_SELL");
        writer.append(';');
        writer.append("YEAR_SELL");
        writer.append(';');
        writer.append("ID_CAI_PRODUCT");
        writer.append(';');
        writer.append("QUANTITY_SELL");
        writer.append(';');
        writer.append("UNIT_PRICE_PRODUCT");
        writer.append(';');
        writer.append("TP_CUSTOMER");
        writer.append(';');
        writer.append("ID_CUSTOMER");
        writer.append(';');
        writer.append("NM_CUSTOMER");
        writer.append(';');
        writer.append("STATE_CUSTOMER");
        writer.append(';');
        writer.append("CITY_CUSTOMER");
        writer.append(';');
        writer.append("ZIPCODE_CUSTOMER");
        writer.append(';');
        writer.append("ADDRESS_CUSTOMER");
        writer.append(';');
        writer.append("ADDRESS_EXTRA_CUSTOMER");
        writer.append(';');
        writer.append("NEIGHBORHOOD_CUSTOMER");
        writer.append(';');
        writer.append("PHONE_COUNTRY_CODE_CUSTOMER");
        writer.append(';');
        writer.append("PHONE_AREA_CODE_CUSTOMER");
        writer.append(';');
        writer.append("PHONE_CUSTOMER");
        writer.append(';');
        writer.append("CELLPHONE_COUNTRY_CODE_CUSTOMER");
        writer.append(';');
        writer.append("CELLPHONE_AREA_CODE_CUSTOMER");
        writer.append(';');
        writer.append("CELLPHONE_CUSTOMER");
        writer.append(';');
        writer.append("EMAIL_CUSTOMER");
        writer.append(';');
        writer.append("NM_SELLER");
        writer.append(';');
        writer.append("ADDITIONAL");
        writer.append('\n');

        String idGrup = "";
        idGrup = validatingDataCadSite(map.get("cadsite"), writer);
        lines = validatingDataSellOut(map.get("sellout"),map.get("pdvs"),idGrup);

        for(String line : lines){
            writer.append(line);
            writer.append('\n');
        }
        writer.flush();
        writer.close();

        for(Map.Entry<String, File> entry : map.entrySet()) {
            File file = entry.getValue();
            file.delete();
        }


    }
    catch(IOException e)
    {
        e.printStackTrace();
    }

    }


    private static String validatingDataCadSite(File file,FileWriter writer){
        String idGrup = "";
        String linha;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            linha = br.readLine();
            idGrup = linha.split(";")[1];

            System.out.println("CadSite");
        } catch(IOException e) {
            e.printStackTrace();
        }
        return idGrup;
    }

    private static ArrayList<String> validatingDataSellOut(File fileSellOut,File filePdv,String idGrup){


        ArrayList<String> listFormatedItens = new ArrayList<>();
        HashMap<String, String[]> listLinesPdv;
        ArrayList<String> data;

        String linhaFile;
        String idErp = "9258227";
        String daySell;
        String monsthSell;
        String yearSell;
//        String cnpjResell;
        String idCai;
        String qtdSell;
        String vlSell;
        String tpCustomer = "J";
        String idCustomer = "";
        String nmCustomer;
        String state;
        String cityCustomer;
        String zipcode;
        String addressCustomer;
        String additional;
        String idForPdv;



        try (BufferedReader br = new BufferedReader(new FileReader(fileSellOut))) {
            listLinesPdv = searchDataPdv(filePdv,idGrup);
            linhaFile = br.readLine();
            String idGrupFile = linhaFile.split(";")[1];
            //Valida se o arquivo pertence ao mesmo grupo
            if(idGrupFile.equals(idGrup)){
             //Esse é o loop para percorrer as linhas do arquivo e fazer as validacoes de cada campo para o CSV
                while((linhaFile = br.readLine()) != null){
                    String[] campos = linhaFile.split(";");
                    if(campos.length > 1){
                        idForPdv = campos[11];
                        String[] camposPdv = listLinesPdv.get(idForPdv);

//                        cnpjResell = campos[1];

                        idCustomer = campos[2];
                        if(idCustomer.length() != 14)tpCustomer = "F";

                        idCai = formatIdCai(campos[3]);


                        /*
                         * Para a quantidade vendida e o preco por unidade
                         * primeiro se pega a quantidade vendida para logo apos
                         * fazer o a divisao e obter o preco por unidade,
                         * antes se remover os ultimos caracteres da quantidade vendida.
                         * Isto foi feito para nao ser necessario remover os caracteres do preco por unidade.
                         */

                        qtdSell = campos[4];
                        //Para pegar o vlSell, devemos pegar o valor por unidade, dividindo o valor total pela total vendido
                        int vlSellUnidade = Integer.parseInt(campos[5]);
                        vlSell = String.valueOf(vlSellUnidade / Integer.parseInt(qtdSell));
                        //Removendo os 3 ultimos caracteres da quantidade vendida
                        qtdSell = qtdSell.substring(0, qtdSell.length() - 3);

                        //O metodo formatDate e para deixar no padrao dd/MM/yyyy
                        data = formatDate(campos[8]);

                        daySell = data.get(0);
                        monsthSell = data.get(1);
                        yearSell = data.get(2);

                        nmCustomer = campos[12];

                        state = camposPdv[6];
                        cityCustomer = camposPdv[7];
                        addressCustomer = camposPdv[8] + camposPdv[9] + camposPdv[10] + camposPdv[11];
                        zipcode = camposPdv[16];

                        additional = campos[15];

                        listFormatedItens.add(idErp + ";" +daySell + ";" +monsthSell + ";" +yearSell + ";" +idCai + ";" +qtdSell + ";" +vlSell +";" +tpCustomer + ";" +idCustomer + ";" +nmCustomer + ";" +state + ";" +cityCustomer
                                + ";" +zipcode + ";" +addressCustomer + ";;;;;;;;;;;" +additional);
                    }

                }

            }else{

            }

        } catch(IOException e) {
            e.printStackTrace();
        }
        return  listFormatedItens;
    }

    private static ArrayList<String> formatDate(String date){
        ArrayList<String> listDate = new ArrayList<>();
        String year = "";
        String month = "";
        String day = "";

        for(char letter:date.toCharArray()){

            if(day.toCharArray().length < 2 && month.toCharArray().length == 2){
                day = day + letter;
            }

            if(month.toCharArray().length < 2 && year.toCharArray().length == 4){
                month = month + letter;
            }

            if(year.toCharArray().length < 4){
                year = year + letter;
            }
        }
        listDate.add(day);
        listDate.add(month);
        listDate.add(year);

        return listDate;
    }

    private static String formatIdCai(String idCai){

        if(idCai.length() == 7) idCai = idCai.substring(0,idCai.length() - 1);
        if(idCai.length() == 13) idCai = idCai.substring(idCai.length() - 6);

            while (idCai.length()<6){
                idCai = "0"+idCai;
            }


        return idCai;
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
                    if(campos.length > 1){
                        idPdv = campos[1];
                        listLines.put(idPdv,campos);
                    }
                }

            }else{

            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return listLines;
    }

    // Construtor privado - Náo há razão em instanciar esta classe
    private ZipUtils() {}
}