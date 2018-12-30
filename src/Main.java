import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String args[]){
        String idCai = "3";
        while (idCai.length()<6){
            idCai = "0"+idCai;
        }
        System.out.println(idCai);

//        Scanner input = new Scanner(System.in);
//
//        for(int i = 0; i < 10; i++){
//
//            System.out.println("A:  "+ i);
//            for(int j = 0; j < 10; j++){
//                System.out.println("B:  "+ j);
//                break;
//            }
//        }
//        Set<String> list	=	new LinkedHashSet<>();
//        while(true){
//
//            list.add(input.nextLine());
//            System.out.println(list);
//
//        }

    	
        BufferedReader br = null;
        byte[] buffer = new byte[1024];

        try {
            File fileExt = new File("E:\\Downloads\\STK DE\\DELLAS\\DELLAS.ZIP");
            File fileOut = new File("E:\\Downloads\\STK DE\\DELLAS\\lalalend");

            ZipUtils.extract(fileExt,fileOut);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
