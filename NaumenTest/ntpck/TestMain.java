package ntpck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import ntpck.sorter.Picker;
import ntpck.sorter.QuickSort;


public class TestMain {
	// количество входных данных
	private final static int ARRAY_LENGTH=100_000;
	private final static int NAME_LENGTH=12;
	// время работы занимает ~ 2 секунды при
	// 100_000 входов с длиной имени 32
	
	public static void main(String args[]) {
		// среднее время выполнения ~ 1s, для паролей длины 12
		
		long start =  System.nanoTime(); 
		
		// генерируем случайный вход
		String[] unsortedNames = generateStringsArray();
		long[] unsortedLongs = generateArrayLongs();
		
		// сортировка
		QuickSort.sort(unsortedNames, unsortedLongs);
		
		long end = System.nanoTime();
		
		long time = (end-start)/1_000_000;
		System.out.println("Wasted time: "+ time +" msecs");
		
		
		writeLog(unsortedNames, unsortedLongs); // пишу классы в лог	
		
		start =  System.nanoTime(); 
		
		String[] test = Picker.pick(unsortedNames, "asdgsfg");
		
		end = System.nanoTime();
		time = (end-start)/1_000_000;
		for (String s:test) System.out.println(s);
		System.out.println("Wasted time: "+ time +" msecs");
		
		
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
		for (int i=0; i<names.length; i++) {
			System.out.println(names[i]+"   "+times[i]);
		}
	}// end of printArrays
	
	
	// метод генерирует массив случайных "имён" для файлов
	static String[] generateStringsArray() {
		String[] r = new String[ARRAY_LENGTH];
		for (int i=0; i<ARRAY_LENGTH; i++)
			r[i]=PasswordGenerator.generate(NAME_LENGTH);
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
