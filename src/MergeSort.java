import java.util.ArrayList;

public class MergeSort {

    private CountForMetodos count = new CountForMetodos();

    private String name = "Merge";
    private long time = 0;


    private static MergeSort mergeSort = null;

    public static MergeSort getInstance(){
        if (mergeSort == null){
            mergeSort = new MergeSort();
        }

        return mergeSort;
    }


    public int[] mergeSort(int[] vec) {
        int [] array = Util.getInstance().passVector(vec);
        time = System.currentTimeMillis();

        if (array.length <= 1) {

            return array;
        }
        count.coutCiclo();
        int meio = array.length / 2;
        int[] dir = array.length % 2 == 0 ? new int[meio] : new int[meio + 1];
        int[] esq = new int[meio];

//        int[] aux = new int[meio];

        for (int i = 0; i < meio; i++) {
            esq[i] = array[i];
            count.coutCiclo();
        }

        int auxIndex = 0;
        for (int i = meio; i < array.length; i++) {
            dir[auxIndex] = array[i];
            auxIndex++;
            count.coutCiclo();
        }

        esq = mergeSort(esq);
        dir = mergeSort(dir);

        int[] aux = sort(esq, dir);


        count.insertCountInList(vec, count);


        return aux;
    }

    private int[] sort(int[] esq, int[] dir) {
        int[] aux = new int[esq.length + dir.length];

        int indexDir = 0, indexEsq = 0, indexAux = 0;

        while (indexEsq < esq.length || indexDir < dir.length) {
            if (indexEsq < esq.length && indexDir < dir.length) {
                if (esq[indexEsq] <= dir[indexDir]) {
                    aux[indexAux] = esq[indexEsq];
                    indexAux++;
                    indexEsq++;
                } else {
                    aux[indexAux] = dir[indexDir];
                    indexAux++;
                    indexDir++;
                }
            } else if (indexEsq < esq.length) {
                aux[indexAux] = esq[indexEsq];
                indexAux++;
                indexEsq++;
            } else if (indexDir < dir.length) {
                aux[indexAux] = dir[indexDir];
                indexAux++;
                indexDir++;
            }

            count.somarCountsCiclos(4);
        }
        return aux;
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
