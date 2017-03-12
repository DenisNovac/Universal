import java.util.ArrayList;

/*
Содержит рекурсивный метод для разбиения строки на слова.
Для обработки строки необходимо создать экземпляр ParserSpace, в конструкторе указать количество
необходимых слов. Если нужно разбить строку на все содержащиеся слова - введите количество
слов минус одно.
*/

public class StringToWordsParse{
	public static void main(String[] args){
		//если указать количество слов, равное количеству слов в строке, то будет исключение.
		//если необходимо порезать всю строку на слова - введите число на единицу меньше количества слов.
		String input = "I am Russian. I dont like anybody. But i drink to you, comrade!";
		int amount=7;
		System.out.println(amount+" : ["+input+"]");


		ParserSpace ps = new ParserSpace(amount, input);
		ArrayList<String> recursive = ps.parse();
		for (String s:recursive) 
			System.out.println("["+s+"]");
	}
}

class ParserSpace {
	private ArrayList<String> answer;
	private int iterator;
	private String string;
	private int space=-1; //начальное значение должно быть -1, т.к. при первом проходе будет +1

	//принимает количество нужных отдельных слов и строку, которую необходимо обработать
	ParserSpace(int i, String s){ 
		answer=new ArrayList<>();
		iterator = i;
		string = s;
	}

	ArrayList<String> parse(){ //метод необходим, чтобы отдать нам массив ответа, но рекурсию из него сделать нельзя - нужен void
		recurse();
		return answer;
	}

	private void recurse(){//void рекурсия, ничего не отдаёт и заменяет глобальные переменные
		while (iterator>=0){
			//System.out.println("Recursing "+iterator); //для отладки
			string = string.substring(space+1, string.length());
			string = string.trim();//срезаем пробелы
			//итератор равен 0 когда мы уже достали нужное колиечество слов,
			//но нужно в последний раз обработать строку и срезать последнее слово
			if (iterator==0){
				answer.add(string);
				iterator--;
				return;
			}

			space = string.indexOf(' '); //если заменить этот символ, то можно парсить по любому символу
			String oneWord = string.substring(0,space);
			//System.out.println("One word:["+oneWord+"]"); //для отладки
			answer.add(oneWord);
			iterator--;
			recurse();
		}
	}

}//end of ParserSpace class