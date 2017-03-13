package application.logic;

import java.util.regex.*;

public class Checker {
	
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
