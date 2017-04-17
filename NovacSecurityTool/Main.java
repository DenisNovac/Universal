import java.io.File;

import security.Autobuilder;
import security.KeyGenerator;
import security.ToHash;
import security.VernamPrimitive;


public class Main {
	private static final String algo="SHA-512";
	

	public static void main(String[] args) {
		//KeyGenerator.generate(1024*1024*100, "key"); //1024*1024 - 1mb keyfile
		ToHash.setEncoding(algo);
		
		File file = new File ("test.txt");
		File key = new File("key1.nstkey");
		//VernamPrimitive.inputDigSign(file, "heybabe");
		//VernamPrimitive.xorEncode(file, key);
		//VernamPrimitive.xorEncode(file, "heybabe");
		//VernamPrimitive.inputDigSign(file, "odin");
		//VernamPrimitive.xorEncode(file, "heybabe");
		//VernamPrimitive.xorEncode(file, "heybabe");
		VernamPrimitive.xorEncode(file, "fraerok");
		boolean r=false;
		String ret = "";
		
		//r=VernamPrimitive.checkDigSign(file, "heybabe", "heybabe");
		//r=VernamPrimitive.removeDigSign(file, "heybabe", "odin");
		//r = VernamPrimitive.checkDigSign(file, "heybabe", "heybabe");
		//r=VernamPrimitive.removeDigSign(file,"heybabe", "heybabe");
		//ret = Autobuilder.protectWithDecoder(file, key);
		//ret = Autobuilder.openWithDecoder(file, key);
		//ret = Autobuilder.protectWithString(file, "heybabe");
		//ret = Autobuilder.openWithString(file, "heybabe");
		
		//r = VernamPrimitive.checkDigSign(file, key, "denis ahahha nakantetsta");
		//r = VernamPrimitive.checkDigSign(file, "heybabe", "hey");
		//r = VernamPrimitive.removeDigSign(file, key, "enis ahahha nakantetsta");
		//r = VernamPrimitive.removeDigSign(file, "heybabe", "heybabe");
		//r = VernamPrimitive.isEncodedWithString(file);
		//r = VernamPrimitive.isEncodedWithFile(file);
		System.out.println(ret);
		System.out.println(r);
		
	}// end of main
	
	
	
	
	
	
}
