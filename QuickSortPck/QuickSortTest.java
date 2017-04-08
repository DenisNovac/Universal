package QuickSortPck;

import java.util.Random;


public class QuickSortTest {
	private final static int ARRAY_LENGTH=10000;

	public static void main(String args[]){
		int[] unsortedArray=generateArray();
		//printArray(unsortedArray);

		QuickSort quickSort = new QuickSort();
		BubbleSort bubbleSort = new BubbleSort();

		int [] sortedArray = quickSort.sort(unsortedArray);
		//int [] sortedArray = bubbleSort.sort(unsortedArray);

		//printArray(sortedArray);


	}




	static void printArray(int[] array){
		for (int i: array)
			System.out.println(i);
		System.out.println("--------\n");
	}

	static int[] generateArray(){
		Random random = new Random();
		int[] array = new int[ARRAY_LENGTH];
		for (int i=0; i<ARRAY_LENGTH; i++)
			array[i]=random.nextInt(1000000);
		return array;
	}


}