package security;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;


public abstract class VernamPrimitive {
	// Класс для шифрования файлов шифром-блокнотом.
	// Не используется напрямую, создан для работы класса Autobuilder
	
	private static final String MARKSTRING = "c3981fa8d26e95d911fe8eaeb6570f2f";
	private static final String MARKFILE = "73acf685374bec2f999ca03840b032ef";
	// марки позволяют определить, зашифрован ли файл (и как он зашифрован)
	
	
	// помещает цифровую подпись в начало файла, можно использовать для хеша пароля
	protected static boolean inputDigSign(File f, String sign) {
		sign = ToHash.encode(sign); // находим хеш от строки-подписи
		BufferedOutputStream bosTrash = FileStream.getBOS(new File("temp"));
		BufferedInputStream bisFile = FileStream.getBIS(f); 
		byte [] b = sign.getBytes(); 
		try {
			bosTrash.write(b); // записываем хеш-строку
			while (true){
				int read = bisFile.read();
				if (read==-1) break;
				bosTrash.write(read);
			}
			bosTrash.close();
			
			copyTo(new File("temp"), f);
			new File("temp").delete();

			
			
			return true;
		} catch (IOException e) {
			System.out.println("Exception! "+e);
			return false;
		}
	} // end of inputDigSign method
	
	// проверка хеш-строки
	protected static boolean checkDigSign(File f, File k, String sign) {
		boolean isEncoded = isEncodedWithFile(f); // проверяем, зашифровано ли вообще
		sign=ToHash.encode(sign);
		
		if (isEncoded) xorEncode(f,k); // сначала дешифруем весь файл
		BufferedInputStream bis = FileStream.getBIS(f);
		
		byte [] fromFile = new byte[ToHash.getDigest()*2];
		byte [] fromInput = sign.getBytes();
		try {
			bis.read(fromFile);
			bis.close();
		} catch (IOException e) {
			System.out.println("Exception! "+e);
			return false;
		}
		
		boolean r=true; 
		for (int i=0; i<ToHash.getDigest(); i++){ // сверяем правильность пароля
			//System.out.println(fromFile[i]+" : "+fromInput[i]);
			if (! (fromFile[i]==fromInput[i]) ){
				r=false;
				break;
			}
		}
			
		if (isEncoded) xorEncode(f, k); // шифруем файл обратно
		return r;
	} // end of checkDigSign() method
	
	// ещё одна разновидность метода, уже для строки-подписи
	protected static boolean checkDigSign(File f, String keyword, String sign) {
		boolean isEncoded = isEncodedWithString(f);
		sign=ToHash.encode(sign);
		if (isEncoded) xorEncode(f,keyword); // сначала дешифруем весь файл
		BufferedInputStream bis = FileStream.getBIS(f);
		
		byte [] fromFile = new byte[ToHash.getDigest()*2];
		byte [] fromInput = sign.getBytes();
	
		try {
			bis.read(fromFile);
			bis.close();
		} catch (IOException e) {
			System.out.println("Exception! "+e);
			return false;
		}
		boolean r=true; 
		for (int i=0; i<ToHash.getDigest()*2; i++){ // сверяем правильность пароля
			//System.out.println(fromFile[i]+" : "+fromInput[i]);
			if (! (fromFile[i]==fromInput[i]) ){
				r=false;
				break;
			}
			
		}
			
		if (isEncoded) xorEncode(f, keyword); // шифруем файл обратно
		return r;
	} // end of checkDigSign() method
	
	protected static boolean removeDigSign(File f, File k, String s){
		// метод удаляет из файла цифровую подпись, но только если она верна
		boolean isCorrect = checkDigSign(f,k,s);
		if (!isCorrect) return false;
		boolean isEncoded = isEncodedWithFile(f);
		if (isEncoded) xorEncode(f,k);
		
		if (isCorrect){
			BufferedInputStream inputFile = FileStream.getBIS(f);
			BufferedOutputStream temp = FileStream.getBOS(new File("temp"));
			try {
				inputFile.skip(ToHash.getDigest()*2);
				while (true) {
					int read=inputFile.read();
					if (read==-1) break;
					temp.write(read);
				}
				inputFile.close();
				temp.close();
				
				copyTo(new File("temp"), f);
				new File("temp").delete();
				
				if (isEncoded) xorEncode(f,k);
				return true;
			} catch (IOException e) {
				System.out.println("Exception! "+e);
				return false;
			}
		}
		
		
		if (isEncoded) xorEncode(f,k);
		return isCorrect;
	}
	
	protected static boolean removeDigSign(File f, String keyword, String s){
		// метод удаляет из файла цифровую подпись, но только если она верна
		boolean isCorrect=checkDigSign(f,keyword,s);
		if (!isCorrect) return false;
		
		boolean isEncoded = isEncodedWithString(f);
		if (isEncoded) xorEncode(f,keyword);
		
		if (isCorrect){
			BufferedInputStream inputFile = FileStream.getBIS(f);
			BufferedOutputStream temp = FileStream.getBOS(new File("temp"));
			try {
				inputFile.skip(ToHash.getDigest()*2);
				while (true) {
					int read=inputFile.read();
					if (read==-1) break;
					temp.write(read);
				}
				inputFile.close();
				temp.close();
				
				copyTo(new File("temp"), f);
				new File("temp").delete();
				
				
				if (isEncoded) xorEncode(f,keyword);
				return true;
			} catch (IOException e) {
				System.out.println("Exception! "+e);
				return false;
			}
		}
		
		
		if (isEncoded) xorEncode(f,keyword);
		return isCorrect;
	}
	
	// проверяет, что файл закодирован файлом
	protected static boolean isEncodedWithFile(File f) { 
		boolean r=true;
		BufferedInputStream file = FileStream.getBIS(f);
		byte [] mark = MARKFILE.getBytes();
		byte [] input = new byte[MARKFILE.length()];
		try {
			file.read(input);
			for (int i=0; i<MARKFILE.length();i++){
				if (mark[i]!=input[i]){
					r=false;
					break;
				}
			}
		}catch (IOException e){
			System.out.println("Exception! "+e);
			return false;
		}
		return r;
	}
	
	// проверяет, что файл закодирован строкой
	protected static boolean isEncodedWithString(File f) { 
		boolean r=true;
		BufferedInputStream file = FileStream.getBIS(f);
		byte [] mark = MARKSTRING.getBytes();
		byte [] input = new byte[MARKSTRING.length()];
		try {
			file.read(input);
			for (int i=0; i<MARKSTRING.length();i++){
				if (mark[i]!=input[i]){
					r=false;
					break;
				}
			}
		}catch (IOException e){
			System.out.println("Exception! "+e);
			return false;
		}
		return r;
	}
	
	protected static boolean xorEncode(File f, File k) { // шифрует файл большим ключом-файлом
		if (isEncodedWithString(f)) return false;
		File tmp = new File("temp"); // мусорный файл
		try {
			BufferedInputStream file, key; 
			BufferedOutputStream temp;
			
			temp = FileStream.getBOS(tmp);
			file = FileStream.getBIS(f);
			key = FileStream.getBIS(k);
			key.mark(1024*1024*1024*1024); //1 TB marklimit, маркируем 0 бит
			// начнём с этого бита после вызова reset
			
			// если файл не был закодирован, вписываем метку в новый файл
			
			if (!isEncodedWithFile(f)) temp.write(MARKFILE.getBytes());
			else file.skip(MARKFILE.length());
			// если файл был закодирван, пропускаем в файле метку и не читаем
			
			while (true){ //начало записи в файл
				
				int readf = file.read();
				if (readf==-1) break; // файл закончился
				
				int readk = key.read();
				if (readk==-1){ // если закончился файл ключа,
					key.reset(); // начинаем читать его с нуля
					readk=key.read();
				}
				
				temp.write(readf^readk); // в temp пишутся лишь шифрованные файлы
			}// end of while 
			temp.close();
			file.close();
			key.close();
			// теперь нужно записать шифрованное сообщение в изначальный файл
			
			copyTo(new File("temp"), f);
			new File("temp").delete();
			
		} catch (IOException e) {
			System.out.println("Exception! "+e);
			return false;
		}
		return true;
	} // end of xor with file method

	// шифрует ксором от строки
	public static boolean xorEncode(File f, String s) {
		if (isEncodedWithFile(f)) return false; // если уже закодировано
		// не даём кодировать повторно - может сломать файл
		s = ToHash.encode(s);
		byte b[] = s.getBytes();
		BufferedInputStream fileIS = FileStream.getBIS(f);
		BufferedOutputStream temp = FileStream.getBOS(new File("temp"));
		try {
			int index=0;
			
			// если раньше не кодировалось, значит - пытаемся кодировать,
			// поэтому помещаем метку в начало файла. Иначе - пропускаем и
			// не читаем эту метку.
			if (!isEncodedWithString(f)) temp.write(MARKSTRING.getBytes());
			else fileIS.skip(MARKSTRING.length());
			
			while (true){
				int read = fileIS.read();
				if (read == -1) break;
				
				if (index==b.length) index=0; // если строка закончилась
				int write = read^b[index]; // начинаем проходить её с нуля
				index ++;
				
				temp.write(write);
			}
			fileIS.close();
			temp.close();
			copyTo(new File("temp"), f);
			new File("temp").delete();
			
		}catch (IOException e){
			System.out.println("Exception! "+e);
			return false;
		}
		return true;
	}// end of xorWithString() method
	
	
	protected static boolean copyTo(File file, File to) { // copy file to 'to'
		// метод не подходит для больших файлов - скорость работы падает
		// в два раза, и это очень заметно
		BufferedInputStream fileIS = FileStream.getBIS(file);
		BufferedOutputStream toOS = FileStream.getBOS(to);
		try {
			while (true){
				int read = fileIS.read();
				if (read==-1) break;
				toOS.write(read);
			}
			fileIS.close();
			toOS.close();
			return true;
		}catch (IOException e) {
			System.out.println("Exception! "+e);
			return false;
		}
		
	}// end of copyTo method
	
}
