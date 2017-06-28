import java.math.BigInteger;
import java.util.Random;
import java.util.ArrayList;

public class PrimitiveRootModulo {
static Random randGen = new Random();
	public static void main(String[] args) {

		System.out.println ( searchForFirst(397) );

	}


	// генератор простых чисел
	public static int generateSimple() {
		int simple;
		while (true) {
			boolean isSimple=false;
			simple = randGen.nextInt(9999);
			for (int i=2; i<simple; i++) {
				if (simple%i==0) {
					isSimple=false;
					break;
				}
				isSimple=true;
			}

			if (isSimple) break;
		}
		return simple;
	} // end of generateSimple


	// функция Эйлера (через факторизацию числа - 
	// разложение на простые сомножители)
	// вычисляет количество чисел, нод которых с simple равен 1

	// технически, т.к. на вход я подаю простое число (оно делится лишь 
	// на 1 и себя), то взаимно простых делителей будет это же число-1
	static int phi(int simple) {
		int result = simple;
		for (int i=2; i*i<simple; i++) {
			if (simple%i==0) {
				while (simple%i==0) // пока число не простое - разлагаем
					simple=simple/i;

				result-=result/i; // вычитаем вычисленное простое число
			}
		}
		if (simple>1)
			result-=result/simple;
		return result;
	} // end of phi



	// метод ищет первый первообразный корень по модулю
	// на самом деле их крайне много
	public static int searchForFirst (int simple) {
		int ph = phi(simple);
		// теперь нужно факторизовать это число
		int[] phFct = factorise(ph);

		ArrayList<Integer> currentG = new ArrayList<Integer>();
		boolean isPrimitiveRoot;
		double check=-1;
		int g=2;
		for (; g<=simple; g++) {
			isPrimitiveRoot=true;

			for (int i=0; i<phFct.length; i++) {
				check = Math.pow ( g, (ph/phFct[i]) );

				if (check%simple==1) {
					isPrimitiveRoot=false;
					break;
				}

			}

			if (isPrimitiveRoot) break;
		}
		int ret = (int) g;
		return ret;
	}


	// разложение на простые множители
	static int[] factorise (int input) {
		ArrayList<Integer> answer = new ArrayList<Integer>();
		int iterable = input;
		for (int i=2; i<input; i++) {
			if (iterable%i==0) { // нашли множитель
				while (iterable%i==0) { // делим его, пока не дойдем до простого числа
					answer.add(i); // попутно вписываем делители в массив
					iterable=iterable/i; // теперь нужно делить iterable
				}
			}
		}
		answer.trimToSize();
		int[] retArray = new int[answer.size()];
		for (int i=0; i<retArray.length;i++)
			retArray[i]=answer.get(i);

		return retArray;
	} // end of factorise

}