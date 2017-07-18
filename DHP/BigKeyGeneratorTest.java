package NovacKEP;

import java.math.BigInteger;
import java.util.Random;
import java.util.ArrayList;

public class BigKeyGeneratorTest {
	static private BigInteger P,G;
	static private int KEYSIZE = 256;
	// методы используют тип BigInteger, т.к. при использовании простых типов
	// данных получаются некорректные значения (очевидно, при вычислении сложных
	// функций с модулем)


	public static void main(String[] args) {
		for (int j=0; j<1; j++) {

			int p = PrimitiveRootModulo.generateSimple();
			int g = PrimitiveRootModulo.searchForAny(p);

			P=BigInteger.valueOf(p);
			G=BigInteger.valueOf(g);
			System.out.println(P);
			System.out.println(G);

			BigInteger[] A = generatePrivateKey(), B=generatePrivateKey();
			BigInteger[] a = generatePublicKey(A), b = generatePublicKey(B);
			/*printArray("A",A); printArray("B",B);
			printArray("a",a); printArray("b",b);*/

			BigInteger[] superkeyA = generateSuperkey(b,A);
			BigInteger[] superkeyB = generateSuperkey(a,B);
			/*printArray("sA",superkeyA);
			printArray("sB",superkeyB);*/

			byte[] keyA = convertToBytes(superkeyA);
			byte[] keyB = convertToBytes(superkeyB);

			System.out.println("\nBYTES:\n");
			for (byte bb:keyA) System.out.print(bb+" ");
			System.out.println("\n");
			for (byte bb:keyB) System.out.print(bb+" ");
			System.out.println("\n");
			System.out.println(keyA.length);


			String test = "DHP key is working. My name is Denis. How are you today?";
			test = xorTestString(test,keyA);
			System.out.println(test);
			test = xorTestString(test,keyB);
			System.out.println(test);
		}
	}

	// Этод метод гарантированно обрубит ключ до нужного размера
	// (например, 256 байт), если он превышает этот размер
	static byte[] convertToBytes(BigInteger[] array) {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		int iterator = 0;
		for (int i=0; i<array.length; i++) {

			byte[] cb = array[i].toByteArray();
			for (byte b:cb) {
				if (iterator>=KEYSIZE) break;
				bytes.add(b);
				iterator++;
			}
			if (iterator>=KEYSIZE) break;
		}

		bytes.trimToSize();

		byte[] r = new byte[bytes.size()];
		for (int i=0; i<bytes.size(); i++){
			r[i]=bytes.get(i).byteValue();
		}
		bytes=null;
		return r;
	}

	// генерирует суперключ для пары пользователей
	static BigInteger[] generateSuperkey(BigInteger[] publicKey, 
		BigInteger[] privateKey) {

		BigInteger[] superkey = new BigInteger[privateKey.length];
		for (int i=0; i<privateKey.length; i++) {
			int smallPrivateKey = privateKey[i].intValue();
			superkey[i] = ( publicKey[i].pow(smallPrivateKey) ).mod(P);
		}

		return superkey;
	}

	// генерирует публичный ключ по формуле G^AmodP = a
	static BigInteger[] generatePublicKey(BigInteger[] privateKey) {
		BigInteger[] publicKey = new BigInteger[privateKey.length];

		for (int i=0; i<privateKey.length; i++) {
			int smallPrivateKey = privateKey[i].intValue();
			publicKey[i] = ( G.pow(smallPrivateKey) ).mod(P);
		}
		
		return publicKey;
	}

	// простой генератор приватного ключа, выбирает из положительных
	// чисел до 128
	static BigInteger[] generatePrivateKey() {
		Random randGen = new Random();
		BigInteger[] key = new BigInteger[KEYSIZE];

		for (int i=0; i<key.length; i++){
			int smallKey=randGen.nextInt(100);
			key[i]=BigInteger.valueOf(smallKey);
		}

		return key;
	}

	// метод для отладки, выводит массивы
	static void printArray(String name, BigInteger[] array) {
		System.out.println(name+": ");
		for (BigInteger b:array) System.out.print(b+" ");
		System.out.println("\n");
	}

	// тестовое шифрование блокнотом строки текста
	static String xorTestString(String text, byte[] key) {
		int iterator=0;
		String ret="";
		for (int i=0; i<text.length(); i++) {
			char c = (char) (text.charAt(i)^key[iterator]);
			if ((int)c!=0) ret+=c;
			else ret+=text.charAt(i);
			iterator++;
			if (iterator==key.length) iterator=0;
		}
		return ret;
	}
}