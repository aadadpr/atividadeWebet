import java.util.LinkedList;

public class BuckSort {

    private String name = "BuckSort";

    private CountForMetodos count = new CountForMetodos();


    private static BuckSort buckSort = null;

    public static BuckSort getInstance(){
        if (buckSort == null){
            buckSort = new BuckSort();
        }
        return buckSort;
    }

    public void bucketSort(int[] vetor)
    {
        int maiorValor = 0;
        //Apenas pegando os valores para achar o maior, nao contando como comparacao do metodo
        for (int number:vetor) {
            if (number > maiorValor){
                maiorValor = number;
            }
        }
        int numBaldes = maiorValor/5;

        LinkedList[] B = new LinkedList[numBaldes];



        for (int i = 0; i < numBaldes; i++){
            B[i] = new LinkedList<Integer>();
            count.coutCiclo();
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
                count.somarCountsCiclos(2);
            }
            count.coutCiclo();
        }

        //Ordena e atualiza o vetor:
        int indice = 0;
        for (int i = 0; i < numBaldes; i++){

            int[] aux = new int[B[i].size()];

            //Coloca cada balde num vetor:
            for (int j = 0; j < aux.length; j++){
                aux[j] = (Integer)B[i].get(j);
                count.coutCiclo();
            }

            InsertSort.getInstance().insertionSort(aux); //algoritmo escolhido para ordenação.

            // Devolve os valores ao vetor de entrada:
            for (int j = 0; j < aux.length; j++, indice++){
                vetor[indice] = aux[j];
                count.coutCiclo();
            }
            count.coutCiclo();
        }


        count.insertCountInList(vetor,count);


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
