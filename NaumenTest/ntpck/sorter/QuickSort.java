package ntpck.sorter;

public abstract class QuickSort {
	private static long[] timeSorted;
	private static String[] namesSorted;
	
	// публичный метод для старта собственно сортировки
	public static void sort(String[] names, long[] time) {
		timeSorted=time;
		namesSorted=names;
		
		int startIndex=0;
		int endIndex=timeSorted.length-1;
		sorting(startIndex, endIndex);
		
		// второй прогон массива и сортировка по алфавиту
		// элементов с одинаковым временем изменения
		
		ABCSort.sort(names, time);
	}
	
	// классический алгоритм быстрой сортировки
	private static void sorting(int start, int end) {
		if (start >= end) // если работа метода закончена
			return;

		int i=start, j=end;
		int cur;
		
		cur=(i+j)/2; // разбиение по середине отрезка 
		
		while (i<j){
			// первые два while определяют направление сортировки
			// движемся с двух концов массива и проверяем элементы
			
			// сортировка в порядке убывания (новые файлы выше)
			while ( i<cur && (timeSorted[i] >= timeSorted[cur])  )
				i++;
			

			while ( j>cur && (timeSorted[cur] >= timeSorted[j])  )
				j--;
			
			// собственно заменяем несортированные части
			// если элементы равны, не меняем их местами.
			if (timeSorted[i]==timeSorted[j] & 
					namesSorted[i].equals(namesSorted[j])) continue;
			
			long temp = timeSorted[i];
			timeSorted[i]=timeSorted[j];
			timeSorted[j]=temp;
			
			String tempS = namesSorted[i];
			namesSorted[i]=namesSorted[j];
			namesSorted[j]=tempS;
			
			
			if (i==cur)
				cur=j;
			else if (j==cur)
				cur=i;

		} // end of while(i<j)
		
		sorting(start, cur); 
		sorting(cur+1, end); 
		
	}// end of sorting
	
}
