package security;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;


// Класс использует методы VernamPrimitive для мощной работы с 
// шифрованием и расшифрованием файлов.
public abstract class Autobuilder {
	public static final int FILESEEDLENGTH=256; // количество байт для генерации зерна
	
	
	// защищает файл декодером
	public static String protectWithDecoder(File f, File k) {
		// Если файл уже шифрован - вернёт false
		if (VernamPrimitive.isEncodedWithFile(f) 
				|| VernamPrimitive.isEncodedWithString(f))
			return "File already protected with Novac Security Tool!";
		
		byte[] keySeed = new byte[FILESEEDLENGTH];
		try{
			BufferedInputStream bisKey = FileStream.getBIS(k);
			// вписываем в файл хеш первых 100 бит файла-ключа
			bisKey.read(keySeed);
			bisKey.close();
			VernamPrimitive.inputDigSign(f, keySeed.toString());
			// шифруем с помощью xor-а
			VernamPrimitive.xorEncode(f, k);
		}catch (IOException e) {
			return "Exception writing in file: "+e;
		}
		return null;
	} // end of protectWithKeyfile() method
	
	
	// открывает файл декодером
	public static String openWithDecoder(File f, File k) {
		if (VernamPrimitive.isEncodedWithString(f)) return "File is not protecting with decoder!";
		if (!VernamPrimitive.isEncodedWithFile(f)) return "File is not protecting with decoder!";
		byte[] keySeed = new byte[FILESEEDLENGTH];
		try {
			BufferedInputStream bisKey = FileStream.getBIS(k);
			bisKey.read(keySeed);
			bisKey.close();
			boolean isCorrectFile = 
					VernamPrimitive.removeDigSign(f, k, keySeed.toString());
			if (isCorrectFile) VernamPrimitive.xorEncode(f, k);
			else return "Wrong decoder file!";
			
			
		}catch (IOException e) {
			return "Exception writing in file: "+e;
		}
		return null;
	}
	
	
	// метод для защиты файла паролем
	public static String protectWithString(File f, String s) {
		// Если файл уже шифрован - вернёт false
		if (VernamPrimitive.isEncodedWithFile(f) 
				|| VernamPrimitive.isEncodedWithString(f))
			return "File already protected with Novac Security Tool!";
		VernamPrimitive.inputDigSign(f, s);
		// так как мы помещаем хеш от пароля в файл, необходимо шифровать
		// с помощью хеша от хеша пароля, иначе получим нулевые символы,
		// которые нашурат работу программы. xorEncode сам делает хеш
		VernamPrimitive.xorEncode(f, ToHash.encode(s));
		return null;
		
	}
	
	
	// метод для открытия файла по паролю
	public static String openWithString(File f, String s) {
		
		if (VernamPrimitive.isEncodedWithFile(f)) return "File is not protecting with password!";
		if (!VernamPrimitive.isEncodedWithString(f)) return "File is not protecting with password!";
		// тут так же используем хеш от пароля
		boolean isCorrectPassword = VernamPrimitive.removeDigSign(f, ToHash.encode(s), s);
		if (isCorrectPassword) VernamPrimitive.xorEncode(f, ToHash.encode(s));
		else return "Not correct password!";
		return null;
	}
	
	
}
