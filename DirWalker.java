import java.io.*;
import java.util.ArrayList;


public class DirWalker {
	static ArrayList<File> lFile;
	
	
	public static void main(String[] args){
		if (args.length!=1) return;
		String directory = args[0];
		lFile = new ArrayList<File>();
		
		checkDir(directory);
		
		
		lFile.trimToSize();
		for (File f:lFile) {
			System.out.println(f.getPath());
		}
		System.out.println(lFile.size() + " files found");
	}
	
	
	static void checkDir(String inputpath){ //прогоняет всю дирректорию вглубь
		File f = new File(inputpath);
		String[] paths=f.list();
		
		for (String path: paths){
			
			File nf = new File(inputpath+"\\"+path); //создаём таким образом, ибо если использовать AbsolutePath, будет биться внутри дирректории, где установлена программа
			
			if (nf.isDirectory()) checkDir(nf.getPath()); //идём вглубь дирректории, если нашли
			if (nf.isFile()) lFile.add(nf); //если нашли файл - записываем
		}
	}//end of checkDir();

}