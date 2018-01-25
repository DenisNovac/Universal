package fileparcer;

import java.util.LinkedHashMap;
import java.io.*;
import java.util.ArrayList;
// а-я 1072-1103
// А-Я 1030-1071
public class FileParcer {
    private LinkedHashMap<Character,Integer> chars;
    private LinkedHashMap<String, Integer> twoChars;

    public LinkedHashMap<Character,Integer> getChars() {
        return chars;
    }

    public LinkedHashMap<String,Integer> getTwoChars() {
        return twoChars;
    }

    public int parceFile(String file, boolean readAll) {
        int readedCount=0;
        chars = new LinkedHashMap<>();
        twoChars = new LinkedHashMap<>();

        // начало чтения файла
        String twoCharsContainer=""; // контейнер для двубуквенных сочетаний
        
        try( BufferedReader br = new BufferedReader
            (new FileReader(file)) ) {
            int read;
            while((read=br.read() )!=-1 ) {
                //System.out.println((char)read);
                //if (read==(int)'\n') continue;
                

                if(read<1104 && read>1040) {
                    readedCount++;
                    // перевели в маленькие буквы
                    if (!readAll && read<1072) read+=32;


                    // обработка двубуквенных сочетаний
                    // т.к. переводим в малые буквы раньше, только для малых
                    // только для символов алфавита. Пробел не считается
                    // последовательность п т = пт
                    twoCharsContainer+=(char)read;
                    
                    if (twoCharsContainer.length()==2) {
                        // если элемента в массиве нет
                        if (twoChars.get(twoCharsContainer)==null) {
                            twoChars.put(twoCharsContainer,1);
                            
                        } else {
                            int count=twoChars.get(twoCharsContainer);
                            count++;
                            twoChars.put(twoCharsContainer,count);
                        }

                        twoCharsContainer="";
                        // вставляем последнюю букву, чтобы проследить следующее сочетание
                        twoCharsContainer+=(char)read;
                    }

                    // если элемента в массиве нет
                    if (chars.get((char)read)==null) {
                        chars.put((char)read,1);
                    } else {
                        int count=chars.get((char)read);
                        count++;
                        chars.put((char)read,count);

                    }

                    
                } else if (readAll) { // если читаем любые символы
                    // если элемента в массиве нет
                    readedCount++;
                    if (chars.get((char)read)==null) {
                        chars.put((char)read,1);
                        
                    } else {
                        int count=chars.get((char)read);
                        count++;
                        chars.put((char)read,count);
                    }   
                }

            } // end of file reading while 

        } catch (IOException e) {
            System.out.println (e);
        } // кэч от открытия файла для чтения
        return readedCount;
    } // end of parceFile




} // end of class