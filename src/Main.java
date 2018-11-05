import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String args[]){
        ArrayList<Integer> list = new ArrayList<>();

        preencherArray(10000, list, 20000);
        System.out.println("Size: " + list.size());

        printList(list);
        //selectionSort(list);
        bubbleSort(list);
        printList(list);


    }



    public static void bubbleSort(ArrayList<Integer> vet){

        long time;
        int aux = 0;
        int i = 0;

        int comp = 0;

        time = System.currentTimeMillis();
        for(i = 0; i<vet.size(); i++){
            for(int j = 0; j<vet.size() -1; j++){
                comp++;
                if(vet.get(j) > vet.get(j + 1)){
                    aux = vet.get(j);

                    vet.add(j, vet.get(j + 1));
                    vet.remove(j+1);
                    vet.add(j + 1, aux);
                    vet.remove(j+2);
                }
            }
        }
        time = System.currentTimeMillis() - time;

        System.out.println("\n" + "Bubble Sort");
        System.out.println("Tempo: " +time);

    }

    public static void selectionSort(ArrayList<Integer> list){
        Integer aux;
        long time = System.currentTimeMillis();

        int comp = 0;

        for(int i = 0; i < list.size(); i ++){
            aux = list.get(i);
            for (int j = i + 1; j < list.size(); j++){
                comp++;
                if(aux > list.get(j)){
                    list.add(j, aux);
                    aux = list.get(j + 1);
                    list.remove(j + 1);
                }

            }
            list.add(i, aux);
            list.remove(i + 1);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("\n" + "SelectionSort");
        System.out.println("Tempo: " +time);
        System.out.println("Comparações: " +comp);

    }

    public static void printList(ArrayList<Integer> list){
            System.out.println(list);
    }

    public static void preencherArray(int size, ArrayList<Integer> array, int numeroBase){
        Random gerar = new Random();
        for (int i = 0; i  < size; i++) {
            array.add(gerar.nextInt( numeroBase));
        }
    }

}
