package ntpck;

public class QuickSort {
	private long[] timeSorted;
	private String[] namesSorted;
	private boolean wasSorted=false;
	
	public QuickSort(long[] time, String[] names){
		timeSorted=time;
		namesSorted=names;
	}
	
	// публичный метод для старта собственно сортировки
	public void sort() {
		int startIndex=0;
		int endIndex=timeSorted.length-1;
		sorting(startIndex, endIndex);
		
		/*
		for (String s: namesSorted) System.out.println(s);
		System.out.println();
		*/
		
		ABCSort abc = new ABCSort(timeSorted, namesSorted);
		abc.sort();
		namesSorted = abc.getSortedNames();
		
		wasSorted=true;
	}
		
	private void sorting(int start, int end) {
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
			
			
			
			/*if (timeSorted[i]==timeSorted[j]& 
					!namesSorted[i].equals(namesSorted[j]))
				
				System.out.println("collision: \n"+timeSorted[i]+":"+
			namesSorted[i]+" "+timeSorted[j]+":"+namesSorted[j]);*/
			
			// собственно заменяем несортированные части
			// если элементы равны, не меняем их местами.
			if (timeSorted[i]==timeSorted[j] & 
					namesSorted[i].equals(namesSorted[j])) continue;
			/*System.out.println(timeSorted[i]+":"+timeSorted[j]);*/
			
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
	
	
	// различного рода служебные классы для тестов и выводов
	
	public long[] getSortedTime() {
		if (!wasSorted) return null;
		return timeSorted;
	}
	
	public String[] getSortedNames() {
		if (!wasSorted) return null;
		return namesSorted;
	}
	
	public void setClasses(long[] time, String[] names) {
		timeSorted=time;
		namesSorted=names;
		wasSorted=false;
	}
	
}
