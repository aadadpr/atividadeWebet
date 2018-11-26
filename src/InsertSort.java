import java.util.ArrayList;

public class InsertSort {

    private String name = "Insert";
    private long time = 0;

    private ArrayList<Long> listTime5 = new ArrayList<>();
    private ArrayList<Long> lisTime10 = new ArrayList<>();
    private ArrayList<Long> listTime50 = new ArrayList<>();
    private ArrayList<Long> listTime100 = new ArrayList<>();
    private ArrayList<Long> listTime1000 = new ArrayList<>();
    private ArrayList<Long> listTime10000 = new ArrayList<>();

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
            }
            vetor[j + 1] = atual;;
        }

        time = System.currentTimeMillis() - time;

        switch (vetor.length){
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
