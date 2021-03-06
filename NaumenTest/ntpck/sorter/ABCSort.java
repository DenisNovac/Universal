package ntpck.sorter;

// класс для сортировки массива по алфавиту
abstract class ABCSort {
	private static String[] sortedNames;
	private static long[] sortedTimes;
	
	// принимает отсортированные массивы и сортирует
	// по алфавиту названия с одинаковым временем
	
	protected static void sort(String[] names, long[] time) {
		sortedNames=names;
		sortedTimes=time;
		sorting();
	}
	
	// проходит по массиву
	// если найдены совпадения времени более 2 - сохраняет их в другой массив
	// там сортирует, возвращает в исходный
	
	// метод для сортировки гигантского массива по алфавиту
	private static void sorting() {
		SortingArray sortingArray=new SortingArray();
		int i=0;
		for (; i<sortedTimes.length; i++){
			
			int left = sortedTimes.length-i-1; // оставшиеся индексы
			if (left==0) return;
			
			sortingArray.addElement(sortedNames[i]);
			int j=1;
			
			// в этом цикле проверяем следующие элементы после i
			// если они одинаковые, записываем их в sortedNames
			// если разные - выходим из цикла и начинаем сортировку
			for (; j<sortedTimes.length; j++){
				if ( (i+j)==sortedTimes.length) break;
				if (sortedTimes[i]!=sortedTimes[i+j]) break;
				// если элементы равны - дописываем их в массив
				sortingArray.addElement(sortedNames[i+j]);
			}
			
			// если в массиве были заполнены индексы 0 и 1,
			// значит есть минимум две строки, нуждающиеся
			// в сортировке по алфавиту
			if (sortingArray.getLast()>=1) {
				// сортируем структуру
				sortingArray.sort();
				String[] sorted = sortingArray.getSortedStrings();
				int k=i;
				// k - это индекс, на котором мы стоим в данный момент
				// во внешнем цикле. Начиная с него мы перезаписываем
				// sortingArray.getLast() элементов вперед рассортированными
				// по алфавиту строками
				
				for (String sortString:sorted) {
					sortedNames[k]=sortString;
					k++;
				}
				i+= sortingArray.getLast();
				// перемещаемся к последней заполненой строке
			}
			// после сортировки или если структура имеет меньше
			// двух вписанных строк - очищаем её и идём дальше
			
			sortingArray.clear(); // очистка результатов работы
			
		}// конец внешнего цикла
		
	}// конец метода sorting
	
}
