import java.util.ArrayList;

public class SelectionSort {

    private String name = "Selection";
    private long time = 0;

    private ArrayList<Long> listTime5 = new ArrayList<>();
    private ArrayList<Long> lisTime10 = new ArrayList<>();
    private ArrayList<Long> listTime50 = new ArrayList<>();
    private ArrayList<Long> listTime100 = new ArrayList<>();
    private ArrayList<Long> listTime1000 = new ArrayList<>();
    private ArrayList<Long> listTime10000 = new ArrayList<>();

    public static SelectionSort selectionSort = null;

    public static SelectionSort getInstance(){
        if(selectionSort == null){
            selectionSort = new SelectionSort();
        }
        return selectionSort;
    }

    void selectionSort(int numbers[], int array_size)
    {
        time = System.currentTimeMillis();
        int i, j;
        int min, temp;
        for (i = 0; i < array_size-1; i++)
        {
            min = i;
            for (j = i+1; j < array_size; j++)
            {
                if (numbers[j] < numbers[min])
                    min = j;
            }
            temp = numbers[i];
            numbers[i] = numbers[min];
            numbers[min] = temp;
        }

        time = System.currentTimeMillis() - time;



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
}
