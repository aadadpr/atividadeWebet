import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Util {
    private static Util util = null;

    public static Util getInstance(){
        if (util == null){
            util = new Util();
        }
        return util;
    }

    public int[] passVector(int [] vec){
        int v[] = new int[vec.length];
        for (int i = 0; i < vec.length; i++) {
            v[i] = vec[i];
        }
        return v;
    }

    public void generateCsvFile(String sFileName, BubbleSort bubbleSort, HeapSort heapSort, InsertSort insertSort, MergeSort mergeSort)
    {
        if(!sFileName.contains(".csv")){
            sFileName.concat(".csv");
        }
        try
        {
            FileWriter writer = new FileWriter(sFileName);

            writer.append("Nome");
            writer.append(';');
            writer.append("Cinco");
            writer.append(';');
            writer.append("Dez");
            writer.append(';');
            writer.append("Cinquenta");
            writer.append(';');
            writer.append("Cem");
            writer.append(';');
            writer.append("Mil");
            writer.append(';');
            writer.append("Dez Mil");
            writer.append('\n');


            writer.append(bubbleSort.getName());
            writer.append(';');
            writer.append(getMedia(bubbleSort.getListTime5()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getLisTime10()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getListTime50()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getListTime100()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getListTime1000()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getListTime10000()));
            writer.append('\n');

            writer.append(heapSort.getName());
            writer.append(';');
            writer.append(getMedia(heapSort.getListTime5()));
            writer.append(';');
            writer.append(getMedia(heapSort.getLisTime10()));
            writer.append(';');
            writer.append(getMedia(heapSort.getListTime50()));
            writer.append(';');
            writer.append(getMedia(heapSort.getListTime100()));
            writer.append(';');
            writer.append(getMedia(heapSort.getListTime1000()));
            writer.append(';');
            writer.append(getMedia(heapSort.getListTime10000()));
            writer.append('\n');

            writer.append(insertSort.getName());
            writer.append(';');
            writer.append(getMedia(insertSort.getListTime5()));
            writer.append(';');
            writer.append(getMedia(insertSort.getLisTime10()));
            writer.append(';');
            writer.append(getMedia(insertSort.getListTime50()));
            writer.append(';');
            writer.append(getMedia(insertSort.getListTime100()));
            writer.append(';');
            writer.append(getMedia(insertSort.getListTime1000()));
            writer.append(';');
            writer.append(getMedia(insertSort.getListTime10000()));
            writer.append('\n');

            writer.append(mergeSort.getName());
            writer.append(';');
            writer.append(getMedia(mergeSort.getListTime5()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getLisTime10()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getListTime50()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getListTime100()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getListTime1000()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getListTime10000()));
            writer.append('\n');


//            writer.append(radixSort.getName());
//            writer.append(';');
//            writer.append(getMedia(radixSort.getListTime5()).toString());
//            writer.append(';');
//            writer.append(getMedia(radixSort.getLisTime10()).toString());
//            writer.append(';');
//            writer.append(getMedia(radixSort.getListTime50()).toString());
//            writer.append(';');
//            writer.append(getMedia(radixSort.getListTime100()).toString());
//            writer.append(';');
//            writer.append(getMedia(radixSort.getListTime1000()).toString());
//            writer.append(';');
//            writer.append(getMedia(radixSort.getListTime10000()).toString());
//            writer.append('\n');

            //generate whatever data you want

            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public String getMedia (ArrayList<Long> list){
        Double media = 0.0;
        String stringMedia = "";
        Boolean count = false;
        int contador = 0;

        for (Long item:list) {
            media = media + (item.doubleValue()/1000.00);
        }

        media = media / list.size();

        for (char caracter:media.toString().toCharArray()) {
            if (caracter == ',' || caracter == '.'){
                count = true;
            }
            if (count)
                contador++;
            if (contador >= 4)
                break;
            stringMedia = stringMedia + caracter;
        }

        return stringMedia;
    }
}
