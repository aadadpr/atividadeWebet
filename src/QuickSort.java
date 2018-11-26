import java.util.ArrayList;

public class QuickSort {

    private String name = "Quick";
    private long time = 0;

    private ArrayList<Long> listTime5 = new ArrayList<>();
    private ArrayList<Long> lisTime10 = new ArrayList<>();
    private ArrayList<Long> listTime50 = new ArrayList<>();
    private ArrayList<Long> listTime100 = new ArrayList<>();
    private ArrayList<Long> listTime1000 = new ArrayList<>();
    private ArrayList<Long> listTime10000 = new ArrayList<>();

    private static QuickSort quickSort = null;

    public static QuickSort getInstance(){
        if (quickSort == null){
            quickSort = new QuickSort();
        }

        return quickSort;
    }

    public int[] quicksort(int vet[], int ini, int fim) {

        int meio;

        time = System.currentTimeMillis();

        if (ini < fim) {

            meio = partition(vet, ini, fim);

            quicksort(vet, ini, meio);

            quicksort(vet, meio + 1, fim);

        }

        time = System.currentTimeMillis() - time;

        switch (vet.length){
            case 5:
                listTime5.add(time);
                break;
            case 10:
                lisTime10.add(time);
                break;
            case 50:
                listTime50.add(time);
                break;
            case 100:
                listTime100.add(time);
                break;
            case 1000:
                listTime1000.add(time);
                break;
            case 10000:
                listTime10000.add(time);
                break;
        }

        return vet;

    }



    public int partition(int vet[], int ini, int fim) {

        int pivo, topo, i;

        pivo = vet[ini];

        topo = ini;



        for (i = ini + 1; i <= fim; i++) {

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

    public ArrayList<Long> getListTime5() {
        return listTime5;
    }

    public void setListTime5(ArrayList<Long> listTime5) {
        this.listTime5 = listTime5;
    }

    public ArrayList<Long> getLisTime10() {
        return lisTime10;
    }

    public void setLisTime10(ArrayList<Long> lisTime10) {
        this.lisTime10 = lisTime10;
    }

    public ArrayList<Long> getListTime50() {
        return listTime50;
    }

    public void setListTime50(ArrayList<Long> listTime50) {
        this.listTime50 = listTime50;
    }

    public ArrayList<Long> getListTime100() {
        return listTime100;
    }

    public void setListTime100(ArrayList<Long> listTime100) {
        this.listTime100 = listTime100;
    }

    public ArrayList<Long> getListTime1000() {
        return listTime1000;
    }

    public void setListTime1000(ArrayList<Long> listTime1000) {
        this.listTime1000 = listTime1000;
    }

    public ArrayList<Long> getListTime10000() {
        return listTime10000;
    }

    public void setListTime10000(ArrayList<Long> listTime10000) {
        this.listTime10000 = listTime10000;
    }
}
