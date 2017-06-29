import java.math.BigInteger;
import java.util.Random;
import java.util.ArrayList;

public abstract class PrimitiveRootModulo {

private static Random randGen = new Random();
private static int BORDER=9999; // должен быть больше 4 знаков!

	// генератор простых чисел
	public static int generateSimple() {
		int simple;
		while (true) {
			boolean isSimple=false;
			simple = randGen.nextInt(BORDER);
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

	// берет первый встреченный первообразный корень числа
	public static int searchForFirst (int simple) {
		return searchForRoot(simple, false);
	}

	// берёт первый встреченный первообразный корень, начиная со случайного g
	public static int searchForAny (int simple) {
		return searchForRoot(simple, true);
	}

	// метод ищет первообразный корень по модулю simple
	// на самом деле их крайне много, возможно, целесообразно
	// брать случайный из них
	private static int searchForRoot (int simple, boolean isRandomPlace) {
		int ph = phi(simple);
		// теперь нужно факторизовать это число
		int[] phFct = factorise(ph);

		boolean isPrimitiveRoot; // триггер
		double check=-1;

		// если увеличивать g, можно брать корни из начала или конца списка
		int g;
		if (isRandomPlace) g=randGen.nextInt(BORDER/2);
		else g=2;
		
		for (; g<=simple; g++) {
			isPrimitiveRoot=true;

			for (int i=0; i<phFct.length; i++) {

				// проверяем формулу (g^(ph/phFct[i]) mod n )!=1
				check = Math.pow ( g, (ph/phFct[i]) );
				if (check%simple==1) {
					isPrimitiveRoot=false;
					break;
				}

			} // конец проверки простых множителей pi
			if (isPrimitiveRoot) break;
		} // end of g search

		int ret = (int) g;
		return ret;
	} // end of searchForFirst


	// функция Эйлера (через факторизацию числа - разложение на простые
	// сомножители) вычисляет количество чисел, нод которых с simple равен 1
	// (количество взаимно-простых чисел с simple)

	// технически, т.к. на вход я подаю простое число (оно делится лишь 
	// на 1 и себя), то взаимно простых делителей будет simple-1
	private static int phi(int simple) {
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

	
	// разложение на простые множители, возвращает массив простых множителей
	private static int[] factorise (int input) {
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