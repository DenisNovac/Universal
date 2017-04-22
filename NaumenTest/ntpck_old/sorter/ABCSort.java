package ntpck.sorter;

// класс для сортировки массива по алфавиту
public class ABCSort {
	private String[] sortedNames;
	private long[] sortedTimes;
	boolean wasSorted = false;
	
	// принимает отсортированные массивы и сортирует
	// по алфавиту названия с одинаковым временем
	
	protected ABCSort (long[] time, String[] names) {
		sortedNames=names;
		sortedTimes=time;
	}
	
	protected void sort() {
		sorting();
		wasSorted=true;
	}
	
	// проходит по массиву
	// если найдены совпадения времени более 2 - сохраняет их в другой массив
	// там сортирует, возвращает в исходный
	
	// тут происходит магия, не трогать
	// метод для сортировки гигантского массива по алфавиту
	private void sorting() {
		SortingArray sortingArray=new SortingArray();
		int i=0;
		for (; i<sortedTimes.length; i++){
			
			//System.out.println("i:"+i);
			
			int left = sortedTimes.length-i-1; // оставшиеся индексы
			if (left==0) return;
			
			sortingArray.addElement(sortedNames[i]);
			int j=1;
			
			for (; j<sortedTimes.length; j++){
				//System.out.println("j:"+j);
				if ( (i+j)==sortedTimes.length) break;
				if (sortedTimes[i]!=sortedTimes[i+j]) break;
				// если элементы равны - дописываем их в массив
				sortingArray.addElement(sortedNames[i+j]);
			}
			
			if (sortingArray.getLast()>=1) {
				/*
				System.out.println("SORTING ARRAY: ");
				String[] test = sortingArray.getSortedStrings();
				for (String s:test) System.out.println(s);
				System.out.println();
				*/
				
				sortingArray.sort();
				String[] sorted = sortingArray.getSortedStrings();
				int k=i;
				for (String sortString:sorted) {
					sortedNames[k]=sortString;
					k++;
				}
				i+= sortingArray.getLast();
				// i=k; - каким-то образом проскакивает на +1 позицию
				// из-за чего пропускает некоторые массивы, стоящие рядом
			}
			// i=j;
			

			sortingArray.clear(); // очистка результатов работы
			
		}// end of for I
		
	}
	
	protected String[] getSortedNames() {
		if (!wasSorted) return null;
		return sortedNames;
	}
	
	protected void setInput(long[] time, String[] names) {
		sortedNames=names;
		sortedTimes=time;
		wasSorted=false;
	}
	
}
