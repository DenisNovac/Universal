package QuickSortPck;


public class QuickSort {
	private int[] array;

	public int[] sort(int[] arr) {
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
			// первые два while определяют направление сортировки
			// движемся с двух концов массива и проверяем элементы
			
			while ( i<cur && (array[i] >= array[cur])  )
				i++;

			while ( j>cur && (array[cur] >= array[j])  )
				j--;
			
			// из этих циклов нас выбьет только если найдутся
			// несортированные элементы
			
			int temp = array[i];
			array[i]=array[j];
			array[j]=temp;
			if (i==cur)
				cur=j;
			else if (j==cur)
				cur=i;

		} // end of while(i<j)
		
		sorting(start, cur); 
		sorting(cur+1, end); 
	}// end of sorting
}