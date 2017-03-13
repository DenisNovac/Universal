package application.logic;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Parcer { //мозг приложения - содержит методы для кодирования и декодирования файлов, сохраняет и парсит БД
	
	public static void encodeFile(File inputFile, String password){
		try {
			char[] passwordChar = password.toCharArray();
			RandomAccessFile raf = new RandomAccessFile (inputFile,"rw");
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
	}
	
	public static boolean decodeFile(File inputFile, String password){
		String passhash;
		String maybehash;
		//проверяем хэш
		try(BufferedReader br = new BufferedReader(new FileReader(inputFile))){
			char[] maybehashCh=new char[128];
			
			for (int i=0; i<128;i++){
				maybehashCh[i]=(char) br.read();
			}
			
			char[] passwordCh=password.toCharArray();
			
			for (int i=0; i<maybehashCh.length; i++){
				for (char c:passwordCh)
					maybehashCh[i]^=c;
				//теперь maybehashCh - массив расшифрованного хэша
			}
			maybehash=new String(maybehashCh);	
			passhash = EncodingClass.toHash(password,"SHA-512");
			
			System.out.println(passhash);
			System.out.println(maybehash);
			
		} catch (Exception e){
			System.out.println(e); //NULL POINTER EXC
			return false;
		}
		if (passhash.equals(maybehash)){
			System.out.println("Passord equals written file hash");
			return true;
		}else {
			System.out.println("NOOOO");
			return false;
		}
	}
	
	//метод для сохранения БД
	public static String save(ObservableList<Line> list, String path, String password){
		File bdFile;//сохраняем базу данных в путь
		try { //записываем бд в файл
			bdFile = new File(path);
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(bdFile))){
				bw.write(EncodingClass.toHash(password, "SHA-512")+"\n");
				System.out.println(EncodingClass.toHash(password, "SHA-512"));
				for (Line l:list){
					String name = l.getName();
					if (name.length()<1) name=" ";
					String pass = l.getPass();
					if (pass.length()<1) pass=" ";
					String desc = l.getDesc();
					if (desc.length()<1) desc=" ";
					String LINE = name+"|"+pass+"|"+desc+"\n";
					bw.write(LINE);
				}	
			}
		} catch (IOException e) {
			return e.getMessage();
		}
		encodeFile(bdFile,password);//кодируем бд*/
		return ("Saving success");
	}//end of save method
	
	
	//метод открытия БД
	public static ObservableList<Line> openDB(String path, String password) {
		File bdFile = new File(path);
		
		//проверяем пароль
		if (!decodeFile(bdFile,password)){
			return null;
		}
		encodeFile(bdFile,password);//расшифровываем файл
		
		ObservableList<Line> lines = FXCollections.observableArrayList();
		
		try (BufferedReader br = new BufferedReader(new FileReader(bdFile));){
			StringToWords stringToWords;
			String readed=br.readLine();
			while(true){
				readed = br.readLine();
				System.out.println(readed);
				if (readed==null) break;
				
				stringToWords = new StringToWords(2,readed);
				ArrayList<String> readedWords= stringToWords.parse();
				String name = readedWords.get(0);
				String pass = readedWords.get(1);
				String desc = readedWords.get(2);
				lines.add(new Line(name,pass,desc));
			}

		}catch (Exception e){}	
		
		
		encodeFile(bdFile,password);//шифруем обратно
		return lines;
	}
	
	public static Boolean pathOk(String path){ //проверка расширения файла
		if ( path.lastIndexOf('.')!=-1 & path.lastIndexOf('.')!=0 )	{
			String ext = path.substring(path.lastIndexOf('.')+1);
			if (ext.equals("npdb")) return true;
		}
		return false;
	}
	
	public static boolean noPipe (String s){ //проверка на отсутствие пайпа - |
		for (int i=0; i<s.length();i++){
			if (s.charAt(i)=='|') return false;
		}
		return true;
	}
	
	public static boolean passwordOk(String s){ //проверяем валидность пароля через регулярное выражение
		Pattern p = Pattern.compile("^\\w+$");
		Matcher m = p.matcher(s);
		return(m.matches());
	}
	
}
