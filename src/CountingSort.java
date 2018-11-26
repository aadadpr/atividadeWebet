import java.util.ArrayList;

public class CountingSort {

   private String name = "Counting";
   private long time = 0;

    private ArrayList<Long> listTime5 = new ArrayList<>();
    private ArrayList<Long> lisTime10 = new ArrayList<>();
    private ArrayList<Long> listTime50 = new ArrayList<>();
    private ArrayList<Long> listTime100 = new ArrayList<>();
    private ArrayList<Long> listTime1000 = new ArrayList<>();
    private ArrayList<Long> listTime10000 = new ArrayList<>();

   private static CountingSort countingSort = null;

   public static CountingSort getInstance(){
       if (countingSort == null){
           countingSort = new CountingSort();
       }

       return countingSort;
   }

    public void CountingSort(Integer[] array, int leftIndex, int rightIndex) {

        time = System.currentTimeMillis();

        //Encontrar o maior valor
        int k = 0;
        for(int m = leftIndex; m < rightIndex; m++){
            if(array[m] > k){
                k = array[m];
            }
        }

        //Cria vetor com o tamanho do maior elemento
        int[] vetorTemporario = new int[k];

        //Inicializar com zero o vetor temporario
        for(int i = 0; i < vetorTemporario.length; i++){
            vetorTemporario[i] = 0;
        }

        //Contagem das ocorrencias no vetor desordenado
        for(int j = leftIndex; j < rightIndex; j++){
            vetorTemporario[array[j]] += 1;
        }

        //Fazendo o  complemento do numero anterior
        for(int i = leftIndex; i < k; i++){
            vetorTemporario[i] = vetorTemporario[i] + vetorTemporario[i - 1];
        }

        //Ordenando o array da direita para a esquerda
        int[] vetorAuxiliar = new int[array.length];
        for(int j = rightIndex; j > leftIndex; j--) {
            vetorAuxiliar[vetorTemporario[array[j]]] = array[j];
            vetorTemporario[array[j]] -= 1;
        }

        //Retornando os valores ordenados para o vetor de entrada
        for (int i = leftIndex; i < rightIndex; i++){
            array[i] = vetorAuxiliar[i];
        }

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
