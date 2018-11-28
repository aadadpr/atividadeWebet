import java.util.ArrayList;

public class QuickSort {

    private String name = "Quick";
    private long time = 0;

    private CountForMetodos count = new CountForMetodos();

    private static QuickSort quickSort = null;

    public static QuickSort getInstance(){
        if (quickSort == null){
            quickSort = new QuickSort();
        }

        return quickSort;
    }

    public int[] quicksort(int vet[], int ini, int fim) {

        int meio;

        count.coutCiclo();
        if (ini < fim) {

            meio = partition(vet, ini, fim);

            quicksort(vet, ini, meio);

            quicksort(vet, meio + 1, fim);


        }

        count.insertCountInList(vet,count);
        return vet;

    }



    public int partition(int vet[], int ini, int fim) {

        int pivo, topo, i;

        pivo = vet[ini];

        topo = ini;



        for (i = ini + 1; i <= fim; i++) {
            count.coutCiclo();
            if (vet[i] < pivo) {

                vet[topo] = vet[i];

                vet[i] = vet[topo + 1];

                topo++;

            }

        }

        vet[topo] = pivo;

        return topo;

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
