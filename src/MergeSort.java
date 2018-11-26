import java.util.ArrayList;

public class MergeSort {

    private String name = "Merge";
    private long time = 0;

    private ArrayList<Long> listTime5 = new ArrayList<>();
    private ArrayList<Long> lisTime10 = new ArrayList<>();
    private ArrayList<Long> listTime50 = new ArrayList<>();
    private ArrayList<Long> listTime100 = new ArrayList<>();
    private ArrayList<Long> listTime1000 = new ArrayList<>();
    private ArrayList<Long> listTime10000 = new ArrayList<>();

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
        int meio = array.length / 2;
        int[] dir = array.length % 2 == 0 ? new int[meio] : new int[meio + 1];
        int[] esq = new int[meio];

        int[] aux = new int[array.length];

        for (int i = 0; i < meio; i++) {
            esq[i] = array[i];
        }

        int auxIndex = 0;
        for (int i = meio; i < array.length; i++) {
            dir[auxIndex] = array[i];
            auxIndex++;
        }

        esq = mergeSort(esq);
        dir = mergeSort(dir);

        aux = sort(esq, dir);

        time = System.currentTimeMillis() - time;

        switch (array.length){
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
