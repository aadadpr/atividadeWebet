import java.util.ArrayList;
import java.util.Vector;

public class BubbleSort {

    private CountForMetodos count = new CountForMetodos();

    private String name = "Bubble";

    private long time = 0;


    private static BubbleSort bubbleSort = null;

    public static BubbleSort getInstance(){

        if(bubbleSort == null){
            bubbleSort = new BubbleSort();
        }

        return bubbleSort;
    }

    public void bubbleSort(int[] vec){
        int v[] =  Util.getInstance().passVector(vec);

        for (int i = v.length; i >= 1; i--) {
            for (int j = 1; j < i; j++) {
                if (v[j - 1] > v[j]) {
                    int aux = v[j];
                    v[j] = v[j - 1];
                    v[j - 1] = aux;
                }
                count.coutCiclo();
            }

            count.coutCiclo();
        }

        count.insertCountInList(v,count);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public CountForMetodos getCount() {
        return count;
    }

    public void setCount(CountForMetodos count) {
        this.count = count;
    }
}
