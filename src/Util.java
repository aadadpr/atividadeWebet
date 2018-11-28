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

    public void generateCsvFile(String sFileName, BubbleSort bubbleSort, HeapSort heapSort, InsertSort insertSort, MergeSort mergeSort,  CountingSort countingSort, BuckSort buckSort,QuickSort quickSort, SelectionSort selectionSort)
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
            writer.append(getMedia(bubbleSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(bubbleSort.getCount().getListTime10000()));
            writer.append('\n');

            writer.append(heapSort.getName());
            writer.append(';');
            writer.append(getMedia(heapSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(heapSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(heapSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(heapSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(heapSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(heapSort.getCount().getListTime10000()));
            writer.append('\n');

            writer.append(insertSort.getName());
            writer.append(';');
            writer.append(getMedia(insertSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(insertSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(insertSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(insertSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(insertSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(insertSort.getCount().getListTime10000()));
            writer.append('\n');

            writer.append(mergeSort.getName());
            writer.append(';');
            writer.append(getMedia(mergeSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(mergeSort.getCount().getListTime10000()));
            writer.append('\n');

            writer.append(countingSort.getName());
            writer.append(';');
            writer.append(getMedia(countingSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(countingSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(countingSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(countingSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(countingSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(countingSort.getCount().getListTime10000()));
            writer.append('\n');

            writer.append(buckSort.getName());
            writer.append(';');
            writer.append(getMedia(buckSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(buckSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(buckSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(buckSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(buckSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(buckSort.getCount().getListTime10000()));
            writer.append('\n');

            writer.append(quickSort.getName());
            writer.append(';');
            writer.append(getMedia(quickSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(quickSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(quickSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(quickSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(quickSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(quickSort.getCount().getListTime10000()));
            writer.append('\n');

            writer.append(selectionSort.getName());
            writer.append(';');
            writer.append(getMedia(selectionSort.getCount().getListTime5()));
            writer.append(';');
            writer.append(getMedia(selectionSort.getCount().getLisTime10()));
            writer.append(';');
            writer.append(getMedia(selectionSort.getCount().getListTime50()));
            writer.append(';');
            writer.append(getMedia(selectionSort.getCount().getListTime100()));
            writer.append(';');
            writer.append(getMedia(selectionSort.getCount().getListTime1000()));
            writer.append(';');
            writer.append(getMedia(selectionSort.getCount().getListTime10000()));
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
            media = media + (item.doubleValue());
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

        stringMedia = media.toString();

        return stringMedia.replace('.',',');
    }
}
