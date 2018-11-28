import java.util.ArrayList;
import java.util.LinkedList;

public class BuckSort {

    private String name = "BuckSort";

    private CountForMetodos count = new CountForMetodos();

    private long time = 0;

    private static BubbleSort bubbleSort = null;

    public static BubbleSort getInstance(){
        if (bubbleSort == null){
            bubbleSort = new BubbleSort();
        }
        return bubbleSort;
    }

    public void BucketSort(int[] vetor, int maiorValor)
    {
        int numBaldes = maiorValor/5;

        LinkedList[] B = new LinkedList[numBaldes];

        time = System.currentTimeMillis();

        for (int i = 0; i < numBaldes; i++){
            B[i] = new LinkedList<Integer>();
        }

        //Coloca os valores no balde respectivo:
        for (int i = 0; i < vetor.length; i++) {
            int j = numBaldes-1;
            while (true){
                if(j<0){
                    break;
                }
                if(vetor[i] >= (j*5)){
                    B[j].add(vetor[i]);
                    break;
                }
                j--;
                count.coutCiclo();
            }
        }

        //Ordena e atualiza o vetor:
        int indice = 0;
        for (int i = 0; i < numBaldes; i++){

            int[] aux = new int[B[i].size()];

            //Coloca cada balde num vetor:
            for (int j = 0; j < aux.length; j++){
                aux[j] = (Integer)B[i].get(j);
            }

            InsertSort.getInstance().insertionSort(aux); //algoritmo escolhido para ordenação.

            // Devolve os valores ao vetor de entrada:
            for (int j = 0; j < aux.length; j++, indice++){
                vetor[indice] = aux[j];
            }

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
