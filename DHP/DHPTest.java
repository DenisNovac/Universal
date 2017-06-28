import java.util.Random;
import java.math.BigInteger;

public class DHPTest {
	static Random randGen = new Random();
	static int UPBORDER = 999999;
	static int BOTTOMBORDER = UPBORDER/10;
	static int SIMPLE = 409;
	static int GENERATOR = 21; // корень по SIMPLE
	static BigInteger seed = BigInteger.valueOf(Math.abs( randGen.nextInt() ));
	// зерно служит для увеличения порядка ключа, выдаётся сервером

	public static void main (String[] args) {
		// Попытка реализовать алгоритм Диффи-Хеллмана
		// Числа генерируются при каждом новом сеансе связи, что делает невоз-
		// можным получение сообщений без доступа к конкретным машинам клиентов
		
		int A,B; // приватные ключи генерируют клиенты
		BigInteger G,P; // они известны всем и публичны

		// P = BigInteger.valueOf(SIMPLE); // простое число
		// G = BigInteger.valueOf(GENERATOR); // G - первообразный корень по модулю P
		
		/*P = BigInteger.valueOf (
			PrimitiveRootModulo.generateSimple() );*/

		/*G = BigInteger.valueOf (
			PrimitiveRootModulo.searchForFirst(P.intValue()) );*/

		P=BigInteger.valueOf(397);
		G=BigInteger.valueOf(99);
		
		// генерируем приватные ключи
		while (true) {
			A = randGen.nextInt(UPBORDER);
			B = randGen.nextInt(UPBORDER);
			if (B<BOTTOMBORDER || A<BOTTOMBORDER) continue;
			break;
		}
		System.out.println(P+" "+G);
		System.out.println("A="+A);
		System.out.println("B="+B);

		// Тут составляются публичные ключи
		BigInteger a = ( G.pow(A) ).mod(P);
		System.out.println("a="+a);

		BigInteger b = ( G.pow(B) ).mod(P);
		System.out.println("b="+b);

		// Тут составляем общий секретный суперключ
		// на основе своего приватного ключа и публичного ключа собеседника
		BigInteger KA = ( b.pow(A) ).mod(P);
		System.out.println("KA="+KA);
		BigInteger KB = ( a.pow(B) ).mod(P);
		System.out.println("KB="+KB);
		// математическая магия сложных логарифмических преобразований!


		//KA=BigInteger.valueOf(155);
		String result = "Hello hello hello friend!";
		result = textCipherXOR(KA, result);
		result = textCipherXOR(KB, result);
		// этот ключ не передаётся по каналу, а хранится у пользователей.
		// таким образом, нам удалось обменяться ключами, не показав их.
		// в обмене участвуют лишь G,P,a и b, а для получения ключа
		// необходимы A и B. Прослушивающему шпиону необходимо решить
		// уравнение: a^B mod P = b^A mod P. У него будут a,b и P, однако
		// без A и B это невозможно!
		KA=null;
		KB=null;
		System.gc();
	} // end of main



	// тесируем, щифруя текстовую строчку обычным Вернамом
	static String textCipherXOR(BigInteger key, String s) {
		// key=key.multiply(seed);
		byte superkey[] = key.toByteArray();

		/*{
			System.out.print(key+": ");
			for (byte testb:superkey) System.out.print(testb+" ");
			System.out.println();
		}*/

		byte text[] = s.getBytes();
		int j=0;
		for (int i=0; i<text.length; i++){
			byte result = (byte) (text[i]^superkey[j]);
			j++;
			if (j>=superkey.length) j=0;
			//System.out.print(result+" ");
			if (result==0 | result==1) continue;
			text[i] = result;
		}
		s = new String(text);
		System.out.println("\n"+s);

		superkey=null;
		key=null;
		System.gc();
		return s;
	} // end of textCipher


	

}