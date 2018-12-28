import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String args[]){
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
            File fileExt = new File("DELLAS.ZIP");
            File fileOut = new File("lalalend");

            ZipUtils.extract(fileExt,fileOut);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
