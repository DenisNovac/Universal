package ntpck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import ntpck.sorter.QuickSort;


public class TestMain {
	// количество входных данных
	private final static int ARRAY_LENGTH=100;
	
	public static void main(String args[]) {
		// среднее время выполнения ~ 1s, зависит от длины
		// строки и количества разрядов во времени изменения
		
		long start =  System.nanoTime(); 
		
		// генерируем случайный вход
		String[] unsortedNames = generateStringsArray();
		long[] unsortedLongs = generateArrayLongs();
		
		// сортировка
		QuickSort quickSort = new QuickSort(unsortedLongs, unsortedNames);
		quickSort.sort();
		
		long end = System.nanoTime();
		String [] sortedNames = quickSort.getSortedNames();
		long[] sortedTimes = quickSort.getSortedTime();
		
		long time = (end-start)/1_000_000;
		System.out.println("Wasted time: "+ time +" msecs");
		
		writeLog(sortedNames, sortedTimes); // пишу классы в лог	
		
	} // end of main
	
	
	// запись большого количества выводов в файл
	static void writeLog(String[] names, long[] times) {
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter("log.txt"));
			for (int i=0; i<names.length;i++){
				bw.write(names[i]+"   "+times[i]+"\n");
			}
			bw.close();
		} catch(IOException e){}	
	}
	
	// красиво выводит массивы данных
	static void printArrays(String[] names, long[] times) {
		for (int i=0; i<names.length; i++){
			System.out.println(names[i]+"   "+times[i]);
		}
	}// end of printArrays
	
	
	// метод генерирует массив случайных "имён" для файлов
	static String[] generateStringsArray() {
		String[] r = new String[ARRAY_LENGTH];
		for (int i=0; i<ARRAY_LENGTH; i++)
			r[i]=PasswordGenerator.generate(10);
		return r;
	}
	
	// генерирует массив лонгов
	static long[] generateArrayLongs() {
		Random random = new Random();
		long[] array = new long[ARRAY_LENGTH];
		for (int i=0; i<ARRAY_LENGTH; i++)
			array[i]=random.nextInt(ARRAY_LENGTH);
		return array;
	}
	
} // end of TestMain
