package ntpck.sorter;

public abstract class Picker {
// класс необходим для поиска по именам в массиве, на вход
// принимает сортированный по времени и алфавиту массив

	public static String[] pick(String[] names, String start) {
		String[] trash = new String[12];
		int counter=0;
		for (int i=0; i<names.length; i++){
			if (counter==12) break;
			if (!names[i].startsWith(start)) continue;
			
			trash[counter]=names[i];
			counter++;
		}
		String[] answer = new String[counter];

		for (int i=0; i<answer.length; i++)
			answer[i]=trash[i];
		return answer;
	}
	
	
	
	
	
}
