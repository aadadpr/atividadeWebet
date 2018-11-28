import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RadixSort {

    private String name = "Radix";
    private long time = 0;

    private CountForMetodos countContador = new CountForMetodos();

    private static RadixSort radixSort = null;

    // A utility function to get maximum value in arr[]
    private int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++){
            countContador.coutCiclo();
            if (arr[i] > mx)
                mx = arr[i];

        }

        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    private void countSort(int arr[], int n, int exp)
    {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count,0);

        // Store count of occurrences in count[]
        n = n -1;
        for (i = 0; i < n; i++){
            countContador.coutCiclo();
            count[ (arr[i]/exp)%10 ]++;
        }


        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++){
            countContador.coutCiclo();
            count[i] += count[i - 1];
        }

        // Build the output array
        for (i = n - 1; i >= 0; i--)
        {
            countContador.coutCiclo();
            output[count[ (arr[i]/exp)%10 ] - 1] = arr[i];
            count[ (arr[i]/exp)%10 ]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to curent digit
        for (i = 0; i < n; i++){
            countContador.coutCiclo();
            arr[i] = output[i];
        }
    }

    // The main function to that sorts arr[] of size n using
    // Radix Sort
    private void radixSort(int arr[], int n)
    {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; m/exp > 0; exp *= 10){
            countContador.coutCiclo();
            countSort(arr, n, exp);
        }

        countContador.insertCountInList(arr, countContador);
    }

    public RadixSort getInstance(){
        countContador.coutCiclo();
        if (radixSort == null){
            radixSort = new RadixSort();
        }
        return radixSort;
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

    public CountForMetodos getCountContador() {
        return countContador;
    }

    public void setCountContador(CountForMetodos countContador) {
        this.countContador = countContador;
    }
}
