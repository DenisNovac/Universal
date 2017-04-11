package mainpck;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class NioTutorial {
	
	/* Java 4 - NIO
	 * 
	 * Ключевые особенности:
	 * Каналы - абстракия низкого уровня файловой системы.
	 * Позволяет передавать файлы с более высокой скоростью.
	 * Каналы не блокируются, поэтому есть селектор.
	 * Селектор выбирает готовый канал для передачи данных.
	 * Сокет является инструментом для блокировки.
	 * 
	 * Буферы
	 * java.nio.charset
	 * Кодировки - кодеры и декодеры для байт и символов юникод.
	 * 
	 * Новая абстракция для пути - интерфейс Path
	 * Содержит путь до файла/каталога
	 * 
	 * Класс Files (! S) в nio - служебный. Содержит приватный конструктор
	 * и онли статические методы
	 * 
	 * */
	
	public static void main(String[] args) {
		//test1();
		//test2();
		//test3();
		//test4();
		Path source = Paths.get("D:\\dev\\source\\java");
		try {
			Files.walkFileTree(source, new Vis());
				
		} catch (IOException e){
			System.out.println(e);
		}
	}
	
	static void test1() {
		// создание объекта Path через вызов get()
		// однако сам файл и каталоги этот метод не создаёт
		Path testFilePath = Paths.get("C:\\test\\parent\\testfile.txt");
		// вывод информации о файле
		System.out.println("File name: "+testFilePath.getFileName()); // имя файла
		System.out.println("File root: " +testFilePath.getRoot()); // диск файла
		System.out.println("File parent: "+testFilePath.getParent()); // каталог файла
		// вывод элементов пути
		System.out.println("Elements of path: ");
		for (Path element:testFilePath)
			System.out.println(element); // вывод по кускам без root-а
	}
	
	static void test2() {
		// Абсолютный путь, относительный и нормализация пути
		Path test = Paths.get(".\\test.txt");
		System.out.println("File URI: "+test.toUri()+"\n"); // возвращает юри файл
		
		System.out.println("Absolute path: "+test.toAbsolutePath()); // абсолютный путь
		Path testNormalized = Paths.get(test.normalize().toString());
		System.out.println("Normalized absolute path: "+testNormalized.toAbsolutePath()+"\n");
		
		System.out.println("Normalize path: "+test.normalize()); // удаляет символы типа . и вообще всё ненужное
		
		// Получение другого объекта строки по нормализованному относительному пути
		
		/*try { // для этого метода необходимо существование файла в системе
			System.out.println("Normalized real path: "+test.toRealPath(LinkOption.NOFOLLOW_LINKS));
			// возвращает абсолютный путь и нормализует его
			// It's normalized real path is: /home/heorhi/workspace/OCPJP/Test  - вот такой вывод
		} catch (IOException e) {
			e.printStackTrace();
		}*/	
	}
	
	static void test3() {
		// Сравнивает, что пути указывают на один и тот же файл
		// Файлы должны существовать в файловой системе!
		/*try {
			boolean r = Files.isSameFile(Paths.get("D:\\dev\\source\\java2\\test2.txt"), 
					Paths.get("D:\\dev\\source\\java2\\test2.txt"));
			System.out.println(r);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		// Можно проверить на директорию или существование:
		boolean r = Files.isDirectory(Paths.get("C:\\Program Files\\"), LinkOption.NOFOLLOW_LINKS);
		System.out.println(r); // true
		
		r = Files.exists(Paths.get("C:\\Program Not Files"), LinkOption.NOFOLLOW_LINKS);
		System.out.println(r); // false
		
		//path.getFileName() для root-каталога вернёт null
		System.out.println(Paths.get("C:\\").getFileName());
		
		// Класс Files содержит методы isReadable(), isWriteable() и isExecutable()
		//для проверки возможности чтения, записи и выполнения файлов:
	}
	
	static void test4(){ // можно получать различные атрибуты
		Path path = Paths.get("C:\\test.txt"); 
        try { 
            Object object = Files.getAttribute(path, "creationTime");  // время создания
            System.out.println("Creation time: " + object); 
             
            //Здесь указан третий параметр 
            Object objectLastModified = Files.getAttribute(path, "lastModifiedTime", // время модифицкаии
                    LinkOption.NOFOLLOW_LINKS); 
            System.out.println("Last modified time: " + objectLastModified); 
            
            
 
            object = Files.getAttribute(path, "size"); // размер в байтах
            System.out.println("Size: " + object); 
            
            object = Files.getAttribute(path, "isDirectory"); // false
            System.out.println("isDirectory: " + object); 
            
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
		
	}
	
}

class Vis implements FileVisitor<Path> {
	// Обход дерева файлов
	// Генерик, без <Path> в оверрайдах будут Object-ы
	/*
	 * Path walkFileTree(Path start, FileVisitor <!--?super Path--> visitor)
	 * Path walkFileTree(Path start, Set<filevisitoption> options, 
	 * 			int maxDepth, FileVisitor<!--? super Path--> visitor)
	 * 
	 * FileVisitor определяет поведение при обходе дерева
	 * maxDepth: 0 - только текущий файл, MAX_VALUE - все подкаталоги
	 * 
	 * 
	 * 
	 * FileVisitor - интерфейс. Методы:
	 * 
	 * FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs)
	 * - перед доступам к элементам каталога
	 * 
	 * FileVisitResult visitFile (T file, BasicFileAttributes attrs)
	 * - при доступе к файлу
	 * 
	 * FileVisitResult postVisitDirectory (T dir, IOException exc)
	 * - когда все элементы директории пройдены
	 * 
	 * FileVisitResult visitFileFailed (T file, IOException exc)
	 * - когда к файлу нет доступа.
	 * 
	 * 
	 * Необходимо реализовать интерфейс FileVisitor, чтобы передать объект
	 * в метод walkFileTree(). Если все методы не нужны, то можно расширить
	 * класс SimpleFileVisitor, переопределив лишь необходимые методы.
	 */
	PathMatcher pathMatcherClasses;
	boolean show = false;
	
	
	Vis(){ // конструктор с паттерном поиска
		String pattern="glob:*.class";
		// String pattern="regex:\\S+\\.java";
		pathMatcherClasses = FileSystems.getDefault().getPathMatcher(pattern);
		
		/*GLOB - синтаксис
		 * * - любая строка
		 * ** - как *, но выходит за границы каталогов
		 * ? любое одиночный синтаксис
		 * [XYZ] - X или Y или Z
		 * [a-z] - любой строчный символ латиницы
		 * {XYZ, ABC} -либо XYZ, либо ABC
		 * */
	}
	
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		//System.out.println("Directory name: "+dir);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		//System.out.println("file name: "+file.getFileName());
		//return FileVisitResult.TERMINATE; // например, заканчивает обход
		// после встречи с первым файлом
		if (pathMatcherClasses.matches(file.getFileName())){
			System.out.print(file.getFileName()+"    "); // показываем имя файла
			FileTime fileTime = Files.getLastModifiedTime(file, LinkOption.NOFOLLOW_LINKS);
			System.out.println(fileTime+"    "+fileTime.toMillis()); //дата и время в милисекундах
			show=true; // указываем, что хоти и дирректорию отобразить
		}
		
		return FileVisitResult.CONTINUE;
	}

	
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
		// return null; // редкий случай, вообще ни разу не выпал.
	}

	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (show) { // отображаем директорию, если в ней есть искомое
			System.out.println("from dir: "+dir);
			System.out.println("--------------------\n");
			show=false;
		}
		return FileVisitResult.CONTINUE;
		//return null; // дает эксепшн нулевого указателя, когда
		// вглубь дирректория заканчивается и нужно вернуться к самому
		// началу и пойти в другую ветку дирректорий
	}
}








