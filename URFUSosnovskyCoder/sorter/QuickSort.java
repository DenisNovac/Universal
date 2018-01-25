package sorter;

class QuickSort<T extends Comparable> {
    private T[] array;
    private boolean isAscending = false; // sort array from less to biggest

    protected QuickSort() { }

    protected T[] sort(T[] arr, boolean isAscending) {
        this.isAscending = isAscending;
        array=arr;
        int startIndex=0;
        int endIndex=array.length-1;
        sorting(startIndex, endIndex);

        return array;
    }

    private void sorting(int start, int end) {
        if (start >= end) // если работа метода закончена
            return;

        int i=start, j=end;
        int cur;

        //cur=i-(i-j)/2;
        cur=(i+j)/2; // разбиение по середине отрезка


        while (i<j){

            // we have to use .compareTo due to array[] here is T type
            // and we can't just compare T with > or <
            if (isAscending) {
                while ( i<cur && (array[i].compareTo(array[cur]))<=0  )
                    i++;

                while ( j>cur && (array[cur].compareTo(array[j]))<=0  )
                    j--;
            } else {
                while ( i<cur && (array[i].compareTo(array[cur]))>=0  )
                    i++;

                while ( j>cur && (array[cur].compareTo(array[j]))>=0  )
                    j--;
            }

            T temp = array[i];
            array[i]=array[j];

            array[j]=temp;
            array[j] = temp;

            if (i==cur)
                cur=j;
            else if (j==cur)
                cur=i;

        } // end of while(i<j)

        sorting(start, cur);
        sorting(cur+1, end);
    }// end of sorting
}