import java.util.ArrayList;

public class InsertSort {

    private String name = "Insert";

    private CountForMetodos count = new CountForMetodos();

    private long time = 0;

    private static InsertSort insertSort = null;

    public static InsertSort getInstance(){
        if (insertSort == null){
            insertSort = new InsertSort();
        }

        return insertSort;
    }

    public int[] insertionSort(int[] vec) {

        int vetor[] =  Util.getInstance().passVector(vec);

        time = System.currentTimeMillis();
        for (int i = 0; i < vetor.length; i++) {
            int atual = vetor[i];
            int j = i - 1;
            while (j >= 0 && vetor[j] >= atual) {
                vetor[j + 1] = vetor[j];
                j--;
                count.coutCiclo();
            }
            vetor[j + 1] = atual;
            count.coutCiclo();
        }

        count.insertCountInList(vetor,count);


        return vetor;
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
