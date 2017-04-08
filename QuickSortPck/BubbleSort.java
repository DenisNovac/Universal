package QuickSortPck;

public class BubbleSort {
    public int[] sort(int[] array){

        //пузырьковая сортировка по возрастанию
        for (int j=0;j<array.length;j++) {

            for (int i=1;i<array.length;i++) {
                if (array[i]<array[i-1]) {
                    int trash=array[i];
                    array[i]=array[i-1];
                    array[i-1]=trash;
                } 
            }

        }

        return array;
    }
}