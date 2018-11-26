import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Main {

    public static void main(String args[]){

        ArrayList<int[]> listVetores = gerarVetores(50);

        System.out.println("Que comece o show...");

        BubbleSort bubbleSort = new BubbleSort();
        BuckSort buckSort = new BuckSort();
        CountingSort countingSort = new CountingSort();
        HeapSort heapSort = new HeapSort();
        InsertSort insertSort = new InsertSort();
        MergeSort mergeSort = new MergeSort();
        QuickSort quickSort = new QuickSort();
        RadixSort radixSort = new RadixSort();
        SelectionSort selectionSort = new SelectionSort();

        for (int [] vetor:listVetores) {
            bubbleSort.bubbleSort(vetor);
            heapSort.heapSort(vetor);
            insertSort.insertionSort(vetor);
            mergeSort.mergeSort(vetor);
//            radixSort.radixSort(vetor, vetor.length);
        }

        Util.getInstance().generateCsvFile("C:\\Users\\Duarte\\Desktop\\projetos\\Dados.csv",bubbleSort,heapSort,insertSort,mergeSort);

        System.out.println("Finish!!!");
    }


    public static ArrayList<int[]> gerarVetores(int quantidade){

        ArrayList<int[]> list = new ArrayList<>();

        for(int i = 0; i < quantidade; i++){
            Random gerar = new Random();
            switch (gerar.nextInt(7)){
                case 1:
                    int vec[] = new int[5];
                        for(int j = 0; j < vec.length; j++){
                            vec[j] = gerar.nextInt();
                        }
                    list.add(vec);
                    break;
                case 2:
                    int vec2[] = new int[10];
                    for(int j = 0; j < vec2.length; j++){
                        vec2[j] = gerar.nextInt();
                    }
                    list.add(vec2);
                    break;
                case 3:
                    int vec3[] = new int[50];
                    for(int j = 0; j < vec3.length; j++){
                        vec3[j] = gerar.nextInt();
                    }
                    list.add(vec3);
                    break;
                case 4:
                    int vec4[] = new int[100];
                    for(int j = 0; j < vec4.length; j++){
                        vec4[j] = gerar.nextInt();
                    }
                    list.add(vec4);
                    break;
                case 5:
                    int vec5[] = new int[1000];
                    for(int j = 0; j < vec5.length; j++){
                        vec5[j] = gerar.nextInt();
                    }
                    list.add(vec5);
                    break;
                 case 6:
                     int vec6[] = new int[10000];
                     for(int j = 0; j < vec6.length; j++){
                         vec6[j] = gerar.nextInt();
                     }
                     list.add(vec6);
                     break;
                  default:
                      list.add(gerarVetores(1).get(0));
                      break;
            }
        }

        return list;
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
