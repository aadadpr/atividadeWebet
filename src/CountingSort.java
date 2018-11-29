import java.util.ArrayList;

public class CountingSort {

    private String name = "Counting";

    private CountForMetodos count = new CountForMetodos();

    private static CountingSort countingSort = null;

    public static CountingSort getInstance() {
        if (countingSort == null) {
            countingSort = new CountingSort();
        }

        return countingSort;
    }


    public void countingSort(int[] v) {
        int maior = v[0];
        for (int i = 1; i < v.length; i++) {
            if (v[i] > maior) {
                maior = v[i];
            }
            count.coutCiclo();
        }
        maior = maior-1;
// frequencia
        int[] c = new int[maior];
        for (int i = 0; i < v.length - 1; i++) {
            c[v[i] - 1] += 1;
            count.coutCiclo();
        }
// cumulativa
        for (int i = 1; i < maior; i++) {
            c[i] += c[i - 1];
            count.coutCiclo();
        }
        Integer[] b = new Integer[v.length];
        for (int i = 0; i < b.length; i++) {
            b[c[v[i] - 1] - 1] = v[i];
            c[v[i] - 1]--;
            count.coutCiclo();
        }
        for (int i = 0; i < b.length -1; i++) {

            v[i] = b[i];
            count.coutCiclo();
        }

        count.insertCountInList(v, count);

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
