package application.logic;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Parcer { //мозг приложения - содержит методы для кодирования и декодирования файлов, сохраняет и парсит БД
	
	public static void encodeFile(String path, String password){
		try {
			RandomAccessFile raf = new RandomAccessFile (path,"rw");
			int read = 0; //сюда пишем ввод
			int car=0;
			while(true){
				raf.seek(car);
				read = raf.read();
				if (read==-1) break;
				read = read^12423;
				raf.seek(car);
				raf.write(read);
				car++;
			}
			raf.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//метод для сохранения БД
	public static String save(ObservableList<Line> list, String path){
		try {
			FileWriter fw = new FileWriter(path);
			try (BufferedWriter bw = new BufferedWriter(fw)){
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
			fw.close();
		} catch (IOException e) {
			return e.getMessage();
		}
		return ("Saving success");
	}//end of save method
	
	
	//метод открытия БД
	public static ObservableList<Line> openDB(String path) {
		ObservableList<Line> lines = FXCollections.observableArrayList();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path));){
			StringToWords stringToWords;
			while(true){
				String readed = br.readLine();
				if (readed==null) break;
				
				stringToWords = new StringToWords(2,readed);
				ArrayList<String> readedWords= stringToWords.parse();
				String name = readedWords.get(0);
				String pass = readedWords.get(1);
				String desc = readedWords.get(2);
				lines.add(new Line(name,pass,desc));
			}

		}catch (Exception e){}	
		
		return lines;
	}
	
	public static Boolean pathOk(String path){ //проверка расширения файла
		if ( path.lastIndexOf('.')!=-1 & path.lastIndexOf('.')!=0 )	{
			String ext = path.substring(path.lastIndexOf('.')+1);
			if (ext.equals("npdb")) return true;
		}
		return false;
	}
	
	public static boolean passwordOk(String s){ //проверяем валидность пароля через регулярное выражение
		Pattern p = Pattern.compile("^\\w+$");
		Matcher m = p.matcher(s);
		return(m.matches());
	}
	
}
