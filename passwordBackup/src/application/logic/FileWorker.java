package application.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FileWorker {
	private static String HASHFUNCTION="SHA-512";//функция для шифрования файла
	
	
	public static String save(ObservableList<Line> list, String path, String password){ //метод для сохранения БД
		File bdFile;
		try { //записываем бд в файл
			bdFile = new File(path); //получаем файл по полученному пути
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(bdFile))){
				bw.write(Hasher.toHash(password, HASHFUNCTION)+"\n"); //вписываем хешированный пароль в начало БД
				for (Line l:list){
					//если пользователь оставил поля пустыми - то пишем туда пробелы
					String name = l.getName();
					if (name.length()<1) name=" ";
					String pass = l.getPass();
					if (pass.length()<1) pass=" ";
					String desc = l.getDesc();
					if (desc.length()<1) desc=" ";
					String LINE = name+"|"+pass+"|"+desc+"\n"; //создаём строку, пригодную для обратного парсинга - с пайпами-разделителями
					bw.write(LINE);
				}	
			}
		} catch (IOException e) {
			return e.getMessage();
		}
		encodeFile(bdFile,password);//кодируем бд, что закодирует и наш хеш-пароль
		return ("Saving success");
	}//end of save method
	
	
	
	
	
	public static ObservableList<Line> openDB(String path, String password) {
		File bdFile = new File(path);
		//проверяем пароль
		if (!decodeFile(bdFile,password)){
			return null;
		}
		encodeFile(bdFile,password);//расшифровываем файл если пароль подошёл
		
		ObservableList<Line> lines = FXCollections.observableArrayList(); //создаём лист - по сути, это наша бд
		
		try (BufferedReader br = new BufferedReader(new FileReader(bdFile));){
			StringToWords stringToWords; //добавляем экземпляр-декодер
			String readed=br.readLine(); //читаем первую линию и пропускаем её - это хешкод пароля
			while(true){
				readed = br.readLine();
				if (readed==null) break;
				
				stringToWords = new StringToWords(2,readed); //создаём конкретный парсер
				ArrayList<String> readedWords= stringToWords.parse(); //парсим строку, получая массив
				String name = readedWords.get(0); //зная порядок значений в массиве, записываем их
				String pass = readedWords.get(1);
				String desc = readedWords.get(2);
				lines.add(new Line(name,pass,desc)); //добавляем в лист новую строчку
			}

		}catch (Exception e){}	
		
		
		encodeFile(bdFile,password);//шифруем обратно, во время работы пользователя с бд, файл на пк остаётся зашифрованным
		return lines;
	}//end of openDb method

	
	
	
	
	public static void encodeFile(File inputFile, String password){ //метод просто шифрует файл исключающим или
		try {
			password=Hasher.toHash(password,HASHFUNCTION); //мы шифруем, используя хэш, чтобы даже самые слабые пароли стали сильнее
			char[] passwordChar = password.toCharArray();
			RandomAccessFile raf = new RandomAccessFile (inputFile,"rw"); //RAF, чтобы писать в тот же бит, который только что прочли
			int read=0; //сюда пишем ввод
			int car=0; //каретка
			while(true){
				raf.seek(car);
				read = raf.read();
				if (read==-1) break;
				for (char c:passwordChar)
					read^=c;
				
				raf.seek(car);
				raf.write(read);
				car++;
			}
			raf.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}//end of encodeFile method
	
	
	
	
	public static boolean decodeFile(File inputFile, String password){ //метод для проверки пароля, вписанного в начало файла
		String passHash;
		String isPassHash;
		//проверяем хэш
		try(BufferedReader br = new BufferedReader(new FileReader(inputFile))){
			char[] isPassHashCh=new char[128]; //Массив для будущего хэша, считанного из файла 
			//128 - длина в char файла, шифрованного алгоритмом SHA-512
			
			for (int i=0; i<128;i++){
				isPassHashCh[i]=(char) br.read(); //читаем первые 128 символов - это, по идее, наш хэш в SHA-512
			}
			
			char[] passwordCh=(Hasher.toHash(password, HASHFUNCTION)).toCharArray(); //выводит пароль в массив чаров
			//мы шифруем, используя хэш, чтобы даже самые слабые пароли стали сильнее
			
			for (int i=0; i<isPassHashCh.length; i++){ //декодируем взятый из файла хэш
				for (char c:passwordCh)
					isPassHashCh[i]^=c;
			}
			isPassHash=new String(isPassHashCh);	//пишем взятый из файла хэш в строку
			passHash = Hasher.toHash(password,HASHFUNCTION); //получаем хэш от пароля
			
		} catch (Exception e){
			return false;
		}
		if (passHash.equals(isPassHash)){ //сравниваем пароль и декодированный хэш
			return true;
		}else {
			return false;
		}
	}//end of decodeFile method
	
	
	
}//end of FileWorker class
