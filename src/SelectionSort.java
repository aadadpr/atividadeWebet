import java.util.ArrayList;

public class SelectionSort {

    private String name = "Selection";

    private CountForMetodos count = new CountForMetodos();


    public static SelectionSort selectionSort = null;

    public static SelectionSort getInstance(){
        if(selectionSort == null){
            selectionSort = new SelectionSort();
        }
        return selectionSort;
    }

    public void selectionSort(int arr[])
    {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            count.coutCiclo();
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++){
                count.coutCiclo();
                if (arr[j] < arr[min_idx])
                    min_idx = j;
            }

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }

        count.insertCountInList(arr, count);

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountForMetodos getCount() {
        return count;
    }

    public void setCount(CountForMetodos count) {
        this.count = count;
    }
}
