import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String args[]){

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
