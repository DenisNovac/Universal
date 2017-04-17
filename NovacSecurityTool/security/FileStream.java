package security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

abstract class FileStream {
	
	protected static BufferedOutputStream getBOS(File file) {
		BufferedOutputStream bos=null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			
		} catch (FileNotFoundException e) {
			System.out.println("Error opening buffered stream: "+e);
			e.printStackTrace();
		}
		
		return bos;
	}// end of get BOS
	
	
	protected static BufferedInputStream getBIS(File file) {
		BufferedInputStream bis=null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			
		} catch (FileNotFoundException e) {
			System.out.println("Error opening buffered stream: "+e);
			e.printStackTrace();
		}
		
		return bis;
	}// end of getBIS
	
	
	
}
