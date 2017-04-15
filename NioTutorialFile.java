package mainpck;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class NioTutorialFile {
	// класс с реализованным шифром Вернама, основанном
	// на каналах и пакете NIO
	
	final static int BUFFER_MAX_SIZE=1024*1024; // максимальный размер буфера - 1 мб
	
	public static void main(String[] args){
		cipherBuffer(new File("file.avi"), new File("key.nstkey"));
	}
	
	// метод шифрует файл с помощью ключа
	static void cipherBuffer(File file, File key) {
		Path filePath = Paths.get(file.getAbsolutePath()); // путь к файлу
		
		try {
			
			FileChannel fileChannel = (FileChannel) // получаем канал для пути
					Files.newByteChannel(filePath, StandardOpenOption.WRITE,
							StandardOpenOption.READ);
			
			
			long fileSize = fileChannel.size(); // берём размер файла
			
			// буфферы байт
			MappedByteBuffer fromFile;
			ByteBuffer ciphered;

			int count=0; // количество прочитанных байт
			int car=0; // каретка
			// поток для чтения и работы с ключом
			BufferedInputStream fromKey = new BufferedInputStream(new FileInputStream(key));
			
			while (true){ //цикл шифрования файла
				int size=BUFFER_MAX_SIZE;
				if (size>fileSize) size=(int) fileSize; // не выделяем лишнего
				// остаток байт в файле
				int remains = (int) (fileSize-fileChannel.position());
				// если осталось немного байт - уменьшаем буфер
				if (remains<size) size = remains;
				
				// связываем буфер с файлом
				fromFile = fileChannel.map(MapMode.READ_WRITE, car, size);
				// равный ему буфер шифрованных байт
				ciphered=ByteBuffer.allocate(size);
				
				count = fileChannel.read(fromFile); // читаем из файла count байт
				
				if (count<=0) break; // если достигнут конец файла - выходим
				byte keyByte=0; 
				
				for (int i=0; i<count; i++){
					
					int keyInt = fromKey.read();
					if (keyInt==-1){
						// переоткрываем поток, если ключ закончился
						fromKey.close(); 
						fromKey = new BufferedInputStream(new FileInputStream(key));
						keyInt = fromKey.read();
					}
					keyByte = (byte)keyInt;
					
					byte readed = fromFile.get(i); // получили байт из файла
					byte cipherByte = (byte) ( readed^keyByte ); // зашифровали
					ciphered.put(cipherByte); // кладём его в буфер
					
				}
				
				ciphered.rewind(); // подготовка буфера к записи в файл
				fileChannel.position(car); // переходим к каретке
				// переход к каретке необходим, чтобы вписать шифрованные байты
				// на место исходных байтов
				fileChannel.write(ciphered); // вписываем буфер начиная с car
				
				car+= count; // передвигаем каретку на количество прочитанного
				
				fromFile.clear(); // очищаем буферы
				ciphered.clear();
				
				if (car==fileSize) break; // если каретка дошла до конца файла - конец цикла
			}
			
			fileChannel.close(); // закрыть канал
			fromKey.close();
			
		} catch (IOException e){
			System.out.println("Error in xoring file: "+e);
			e.printStackTrace();
		}
		
	} // end of cipherBuffer(String p) method
	
	
	
	
	
	
	/* остальные методы, в принципе, не предсавляют интереса
	static void reopener(File f){ // тест на переоткрытие потока с файлом
		try {
			FileInputStream fis = new FileInputStream(f);
			int i=0;
			while(true){
				if (i==2) break;
				while(true){
					int read = fis.read();
					if (read==-1) break;
					System.out.print((char)read);
				}
				fis.close();
				fis = new FileInputStream(f);
				i++;
			}
			fis.close();
		} catch (IOException e){
			System.out.println(e);
		}
	}
	
	
	
	
	public static void readFromChannel() {
		int count; // количество прочитанных байтов
		Path filepath = null;
		// получили путь к файлу
		try {
			filepath=Paths.get("test.txt");
		} catch (InvalidPathException e){}
		
		// получаем канал к этому файлу
		try (SeekableByteChannel fChan = Files.newByteChannel(filepath ))
		{
			// выделяем память под буфер
			ByteBuffer mBuf = ByteBuffer.allocate(12);
			do {
				count=fChan.read(mBuf); // метод возвращает количество считанных байтов
				
				if (count!=-1){
					// подготовка буфера к чтению данных
					// метод rewind необходим перед методом get
					mBuf.rewind();
					
					// читать байты данных из буфера и выводить
					for (int i=0; i<count; i++)
						System.out.print((char)mBuf.get());
				}
				
			} while (count!=-1);
			
			System.out.println();
			
		} catch (IOException e){}
	}// end of read from channel
	
	static void writeInChannel() throws IOException{
		FileChannel fChan = (FileChannel)
				Files.newByteChannel(Paths.get("test.txt"), StandardOpenOption.WRITE);
		ByteBuffer mBuf = ByteBuffer.allocate(26);
		for (int i=0; i<26; i++) // пишем некоторые байты в буфер
			mBuf.put((byte)('A'+i));
		mBuf.rewind(); // готовим буфер к записи данных из него
		fChan.write(mBuf);
		fChan.close();
	}
	*/
	
} // end of class












