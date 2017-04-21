package ntpck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class NaumenTestMain {
	private final static int ARRAY_LENGTH=100_000;
	
	
	
	
	/*
		123_hello
		92_h
		AH
		HELLO
		_hellO
		ah
		ashd
		hello
		
	*/
	
	public static void main(String args[]) {
		
		long start =  System.nanoTime();
		
		/*String[] unsortedNames = {"zallo1", "hbh2",
				"sosat3", "sdf4", 
				"chlen5", "234zhahah6", 
				"LLLLol7", "__snet8", 
				"tom9", "aidor10"};*/
		
		String[] unsortedNames = generateStringsArray();
		//for (String s: unsortedNames) System.out.println(s);
		long[] unsortedLongs = generateArrayLongs();
		//for (long l: unsortedLongs) System.out.println(l);
		//printArrays(unsortedNames, unsortedLongs, false);
		System.out.println();

		//long [] unsortedLongs = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		QuickSort quickSort = new QuickSort(unsortedLongs, unsortedNames);
		quickSort.sort();
		long end = System.nanoTime();
		String [] sortedNames = quickSort.getSortedNames();
		long[] sortedTimes = quickSort.getSortedTime();
		
		/*ABCSort abc = new ABCSort(sortedTimes, sortedNames);
		abc.sort();
		String [] sorted = abc.getSortedNames();*/
		printArrays(sortedNames, sortedTimes, true);
		
		
		long time = (end-start)/1_000_000;
		
		System.out.println("Wasted time: "+ time +" msecs");
		
		
		/*String[] test = {"hello", "HELLO","AH","ah", "123_hello", "_hellO", "ashd", "92_h"};
		long [] unsortedLongs = {1, 1, 1, 1, 1, 1, 1, 1};
		ABCSort abc = new ABCSort(unsortedLongs, test);
		abc.sort();
		test=abc.getSortedNames();
		for (String s:test) System.out.println(s);*/
		
		/*QuickSort quickSort = new QuickSort(unsortedLongs, unsortedNames);
		printArrays(unsortedNames, unsortedLongs);
		quickSort.sort();
		
		System.out.println("\n\nAfter sorting:");
		long[] sortedLongs = quickSort.getSortedTime();
		String[] sortedStrings = quickSort.getSortedNames();
		long end = System.nanoTime();
		
		printArrays(sortedStrings, sortedLongs);
		long time = (end-start)/1_000_000;
		
		System.out.println("Wasted time: "+ time +" msecs");*/
		
		/*
		
		long[] unsortedArray = generateArrayLongs();
		String[] unsortedStrings = generateStringsArray();
		//printArrays(unsortedStrings, unsortedArray, false);
		
		
		start = System.nanoTime();
		QuickSort quickSort = new QuickSort(unsortedArray, unsortedStrings);
		quickSort.sort();
		
		
		long end = System.nanoTime();
		System.out.println("\n\nAfter sorting:");
		long[] sortedArray = quickSort.getSortedTime();
		String[] sortedStrings = quickSort.getSortedNames();
		printArrays(sortedStrings, sortedArray, true);
		
		long time = (end-start)/1_000_000;
		System.out.println("Wasted time: "+ time +" msecs");
		
		*/
		
	}

	
	static void printArrays(String[] names, long[] times, boolean write){
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter("log.txt"));
		} catch(IOException e){}
		for (int i=0; i<names.length; i++){
			if (!write)System.out.println(names[i]+"   "+times[i]);
			try {
				if (write) bw.write(names[i]+"   "+times[i]+"\n");
			} catch (IOException e) {e.printStackTrace();}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}// end of printArrays
	
	static void printArray(long[] array) {
		for (long i: array)
			System.out.println(i);
		System.out.println("--------\n");
	}
	
	static String[] generateStringsArray(){
		String[] r = new String[ARRAY_LENGTH];
		for (int i=0; i<ARRAY_LENGTH; i++)
			r[i]=PasswordGenerator.generate(12);
		return r;
	}
	
	static long[] generateArrayLongs() {
		Random random = new Random();
		long[] array = new long[ARRAY_LENGTH];
		for (int i=0; i<ARRAY_LENGTH; i++)
			array[i]=random.nextInt(ARRAY_LENGTH);
		return array;
	}
}
