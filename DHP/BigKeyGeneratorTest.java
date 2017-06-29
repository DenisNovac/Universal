package NovacKEP;

import java.math.BigInteger;
import java.util.Random;

public class BigKeyGeneratorTest {
	static private BigInteger P,G;

	public static void main(String[] args) {
		int p = PrimitiveRootModulo.generateSimple();
		int g = PrimitiveRootModulo.searchForAny(p);

		P=BigInteger.valueOf(p);
		G=BigInteger.valueOf(g);

		BigInteger[] A = generatePrivateKey(), B=generatePrivateKey();
		printArray("A",A); printArray("B",B);
		BigInteger[] a = generatePublicKey(A), b = generatePublicKey(B);
		printArray("a",a); printArray("b",b);

		BigInteger[] superkeyA = generateSuperkey(b,A);
		BigInteger[] superkeyB = generateSuperkey(a,B);
		printArray("sA",superkeyA);
		printArray("sB",superkeyB);


	}


	static BigInteger[] generateSuperkey(BigInteger[] publicKey, 
										BigInteger[] privateKey) {

		BigInteger[] superkey = new BigInteger[privateKey.length];
		for (int i=0; i<privateKey.length; i++) {
			int smallPrivateKey = privateKey[i].intValue();
			superkey[i] = ( publicKey[i].pow(smallPrivateKey) ).mod(P);
		}

		return superkey;
	}


	static BigInteger[] generatePublicKey(BigInteger[] privateKey) {
		BigInteger[] publicKey = new BigInteger[privateKey.length];

		for (int i=0; i<privateKey.length; i++) {
			int smallPrivateKey = privateKey[i].intValue();
			publicKey[i] = ( G.pow(smallPrivateKey) ).mod(P);
		}
		
		return publicKey;
	}


	static BigInteger[] generatePrivateKey() {
		Random randGen = new Random();
		BigInteger[] key = new BigInteger[256];

		for (int i=0; i<key.length; i++){
			int smallKey=randGen.nextInt(128);
			key[i]=BigInteger.valueOf(smallKey);
		}

		return key;
	}


	static void printArray(String name, BigInteger[] array) {
		System.out.println(name+": ");
		for (BigInteger b:array) System.out.print(b+" ");
		System.out.println("\n");
	}


}