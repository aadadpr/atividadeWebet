import java.awt.*;
import java.util.ArrayList;

public class HeapSort {

    private String name = "Heap";
    private long time = 0;

    private CountForMetodos count = new CountForMetodos();

    private static HeapSort heapSort = null;


    public static HeapSort getInstance(){
        if (heapSort == null){
            heapSort = new HeapSort();
        }

        return heapSort;
    }

    public int[] heapSort(int[] vec) {

        int v[] =  Util.getInstance().passVector(vec);

        time = System.currentTimeMillis();
        buildMaxHeap(v);
        int n = v.length;

        for (int i = v.length - 1; i > 0; i--) {
            swap(v, i, 0);
            maxHeapify(v, 0, --n);
        }

        time = System.currentTimeMillis() - time;
        count.insertCountInList(v,count);
        return v;
    }

    private void buildMaxHeap(int[] v) {
        for (int i = v.length / 2 - 1; i >= 0; i--) {
            maxHeapify(v, i, v.length);
        }

    }

    private void maxHeapify(int[] vetor, int pos, int tamanhoDoVetor) {

        int max = 2 * pos + 1, right = max + 1;
        if (max < tamanhoDoVetor) {

            if (right < tamanhoDoVetor && vetor[max] < vetor[right]) {
                max = right;
            }
            count.coutCiclo();

            if (vetor[max] > vetor[pos]) {
                swap(vetor, max, pos);
                maxHeapify(vetor, max, tamanhoDoVetor);
            }
            count.coutCiclo();
        }
        count.coutCiclo();

    }

    private void swap(int[] v, int j, int aposJ) {
        int aux = v[j];
        v[j] = v[aposJ];
        v[aposJ] = aux;
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
