package ntpck.sorter;

import java.util.Arrays;

// структура для создания подмножества строк
// с одинаковым временем изменения

class SortingArray {
	private String[] array;
	private int last=-1; // последний заполненный индекс
	
	protected SortingArray() {
		array = new String[2];
	}
	
	// метод для подготовки структуры к следующему заполнению
	
	// метод очищает результаты работы структуры
	protected void clear() {
		array=new String[2];
		last=-1;
	}
	
	// добавление элемента
	protected void addElement(String element) {
		if (last==array.length-1) expandArray();
		array[last+1] = element;
		last++;
	}
	
	// сортировка структуры
	protected void sort() {
		trim();
		
		// сортирую массив по алфавиту библиотечным методом
		/*Метод сортирует по правилам:
		 * Цифры (меньшие выше)
		 * Заглавные буквы
		 * Подчеркивания
		 * Строчные буквы
		 * */
		
		Arrays.sort(array);
	}

	// возвращает внутренний массив
	protected String[] getSortedStrings() {
		return array;
	}
	
	// возвращает последний заполненный индекс
	protected int getLast() {
		return last;
	}
	
	// убирает лишние места в массиве
	private void trim() {
		String[] trash = array.clone();
		array = new String[last+1];
		for (int i=0; i<last+1; i++)
			array[i]=trash[i];
	}
	
	// увеличивает размер внутреннего массива при необходимости
	private void expandArray() {
		String[] trash = array.clone();
		array = new String[array.length*2];
		for (int i=0; i<trash.length; i++)
			array[i]=trash[i];
	}
	
}
