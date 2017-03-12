package application.logic;

import java.io.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Parcer {
	
	public static void encodeFile(String path){
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
	
}
