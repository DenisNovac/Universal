import java.util.regex.*;
import java.util.*;

public class PasswordGenerator {
	public static void main(String[] args){
		int length = Integer.parseInt(args[0]);
		for (int i=0; i<20; i++)
			generate(length);
		
		/*Pattern p = Pattern.compile("^\\w+$");
		Matcher m = p.matcher(password);
		System.out.println(m.matches());*/

	}

	public static void generate (int n){
		String password="";
		int underlines=2;
		int chars = n;
		while (chars>=0){
			int choice = (int)(Math.random()*5);//0,1,2,3,4
			switch(choice){
				case 0:
					if (password.length()<1) break;
					if (password.length()==n) break;
					int chance = (int)(Math.random()*underlines);
					if (chance!=1) break;
					password+="_";
					underlines*=2;
					chars--;
					break;

				case 2:
					int latin=(int) (Math.random()*91);
					if (latin<65) break;
					password+=(char)latin;
					chars--;
					break;

				case 4:
					int sLatin= (int) (Math.random()*123);
					if (sLatin<97) break;
					password+=(char)sLatin;
					chars--;
					break;

				case 1:
				case 3:
					int number = (int)(Math.random()*58);
					if (number<48) break;
					password+=(char)number;
					chars--;
					break;

			}//end of switch
		}//end of while

		System.out.println(password);
	}
}