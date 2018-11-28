import java.util.ArrayList;

public class CountForMetodos {
    private long count;

    private ArrayList<Long> listTime5 = new ArrayList<>();
    private ArrayList<Long> lisTime10 = new ArrayList<>();
    private ArrayList<Long> listTime50 = new ArrayList<>();
    private ArrayList<Long> listTime100 = new ArrayList<>();
    private ArrayList<Long> listTime1000 = new ArrayList<>();
    private ArrayList<Long> listTime10000 = new ArrayList<>();

    public CountForMetodos() {
        count = 0;
    }

    public void coutCiclo(){
        count++;
    }

    public void somarCountsCiclos(int quantidade){
        this.count = this.count + quantidade;
    }

    public void insertCountInList(int[] v, CountForMetodos cout){
        switch (v.length){
            case 5:
                listTime5.add(cout.getCount());
                break;
            case 10:
                lisTime10.add(cout.getCount());
                break;
            case 50:
                listTime50.add(cout.getCount());
                break;
            case 100:
                listTime100.add(cout.getCount());
                break;
            case 1000:
                listTime1000.add(cout.getCount());
                break;
            case 10000:
                listTime10000.add(cout.getCount());
                break;
        }
        setCount(0);
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
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
