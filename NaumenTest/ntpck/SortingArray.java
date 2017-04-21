package ntpck;

import java.util.Arrays;

public class SortingArray {
	private int index;
	private String[] array;
	private int last=-1; // последний заполненный индекс
	
	SortingArray() {
		array = new String[2];
	}
	
	public void setUp(int index){
		this.index=index;
	}
	
	public void clear(){
		array=new String[2];
		last=-1;
	}
	
	public void addElement(String element) {
		if (last==array.length-1) expandArray();
		array[last+1] = element;
		last++;
	}
	
	
	public void sort(){
		trim();
		Arrays.sort(array);
	}
	
	public void printArray() {
		for (String s:array)
			System.out.println(s);
	}
	
	private void trim() {
		String[] trash = array.clone();
		array = new String[last+1];
		for (int i=0; i<last+1; i++)
			array[i]=trash[i];
	}
	
	public String[] getSortedStrings() {
		return array;
	}
	
	public int index() {
		return index;
	}
	
	public int getLast(){
		return last;
	}
	
	private void expandArray() {
		String[] trash = array.clone();
		array = new String[array.length*2];
		for (int i=0; i<trash.length; i++)
			array[i]=trash[i];
	}
	
}
