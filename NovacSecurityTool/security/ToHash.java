package security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ToHash{
	static String ALG="SHA-512";
	static int algDigest=0;
	
	public static void setEncoding(String s){
		try {
			algDigest = MessageDigest.getInstance(ALG).getDigestLength();
			ALG=s;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No such algo! Use SHA-512, SHA-256 or MD5: "+e);
			e.printStackTrace();
		}
	}
	
	protected static int getDigest(){
		return algDigest;
	}

	public static String encode(String s){ //alg: md5 SHA-256 SHA-512
		MessageDigest sha=null;
		int RADIX=16;
		try {
			sha = MessageDigest.getInstance(ALG); 
		}catch(Exception e){
			return e.getMessage();
		}
		int DIGLENGTH = sha.getDigestLength();//16 md5 32 sha-256 64 sha-512
		byte[] bytes = s.getBytes();
		bytes = sha.digest(bytes);
		//System.out.println(DIGLENGTH);
		BigInteger bigInt = new BigInteger(1, bytes);
		s=bigInt.toString(RADIX); //основание системы - КОНСТАНТА

		while( s.length() < DIGLENGTH*2 ){//дописывает нули, если хеш меньше стандартного
        	s = "0" + s;
    	}

    	return s;
	}

}
