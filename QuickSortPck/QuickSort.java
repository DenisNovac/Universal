package QuickSortPck;

import java.util.Random;


public class QuickSort {
	private int[] array;
	private static Random random = new Random();

	public int[] sort(int [] arr){
		array=arr;
		int startIndex=0;
		int endIndex=array.length-1;
		sorting(startIndex, endIndex);

		return array;
	}

	private void sorting(int start, int end){
		if (start >= end) // если работа метода закончена
			return;

		int i=start, j=end;
		int cur;
		// Может бросить любое число от 0 до end-1
		do {
			cur = random.nextInt(end); 
			//cur=i-(i-j)/2;
		} while (cur<start); // нужно, чтобы выбор был из той же половины*/

		while (i<j){
			// пока значение по i индексу меньше нарандомленного
			// менять ничего не нужно и прогоняем дальше до cur
			while ( (array[i] <= array[cur]) && i<cur )
				i++;

			// пока значение нарандомленного меньше конца
			// прогоняем дальше, пока не достигнем cur
			while ( (array[cur] <= array[j]) && j>cur )
				j--;

			// тепперь если индексы не сошлись, сортируем
			if (i<j){
				//System.out.println(i + ","+j);
				int temp = array[i];
				array[i]=array[j];
				array[j]=temp;
				if (i==cur)
					cur=j;
				else if (j==cur)
					cur=i;
			}

		} // end of while(i<j)

		// теперь сортируем половины 
		sorting(start, cur); 
		sorting(cur+1, end); 
	}// end of sorting

}