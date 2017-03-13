package application.logic;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hasher {
	
	static String toHash(String s, String alg){ //alg: md5 SHA-256 SHA-512
		
		MessageDigest sha=null;
		int RADIX=16;
		try {
			sha = MessageDigest.getInstance(alg); 
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
