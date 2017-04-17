package security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


// Класс предназначен для генерации файлов - ключей большой длины для последующего шифрования
public abstract class KeyGenerator {
	
	public static void generate(int byteLength, String name) {
		Random random = new Random(); 
		byte [] bytes = new byte[byteLength]; 
		
		random.nextBytes(bytes); // рандомные байты
		int randomName = random.nextInt(1_000_000);
		
		File key = new File(name+String.valueOf(randomName)+".nstkey");
		
		try {
			FileOutputStream fos = new FileOutputStream(key);
			fos.write(bytes);
			fos.close();
			
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error opening file stream: "+e);
			
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Error opening file stream: "+e);
		}
		
	}
	
	// генерирует множество ключей
	protected static void generate(int byteLength, String name, int n) {
		for (int i=0; i<n; i++)
			generate(byteLength, name);
	}
	
	
	
}
