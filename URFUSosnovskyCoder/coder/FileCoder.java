package coder;
import logic.BinaryOperations;

import java.io.*;
import java.util.Map;

public class FileCoder {

    // принимает на вход шифрокнигу буква-код
    public static void encode(String filepath, Map<String,String> codeBook) throws IOException{
        System.out.println("Encoding "+filepath);
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(filepath+".sos") );
        
        String random="";

        String line, s;
        Byte b;
        int counter=0;
        while( (line=br.readLine())!=null ) {
            for (int i=0; i<line.length();i++) {
                s="";
                s+=line.charAt(i);
                b = BinaryOperations.writeBits(codeBook.get(s));
                if (b!=null) bos.write(b);

                counter++;
                if (counter==100) {
                    System.out.print(".");
                    random+=s;
                    counter=0;
                }
            }
        }

        System.out.println();
        b=BinaryOperations.writeBitsFlush();
        if (b!=null) bos.write(b);

        // теперь необходимо дописать в файл несколько случайных символов, чтобы
        // при декодировании не потерять важную информацию
        //System.out.println(random);
        /*for (int i=0; i<random.length(); i++) {
            s="";
            s+=random.charAt(i);
            b = BinaryOperations.writeBits(codeBook.get(s));
            if (b!=null) bos.write(b);
        }*/

        b=BinaryOperations.writeBitsFlush();
        if (b!=null) bos.write(b);



        br.close();
        bos.close();
    }

    // принимает на вход шифрокнигу код-буква
    public static void decode(String filepath, Map<String,String> decodeBook) throws IOException {
        System.out.println("Decoding "+filepath);
        BinaryOperations.setDecodeBook(decodeBook);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filepath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(filepath+"out.txt"));

        int counter=0;
        byte[] buffer = new byte[128];
        while ( bis.read(buffer)!=-1 ) {
            
            for (byte b:buffer) {
                String bits = BinaryOperations.bitsToString(b);
                //System.out.println(bits);
                String ch = BinaryOperations.readBits(bits);
                //System.out.print(ch);
                if(ch!=null) bw.write(ch);

                counter++;
                if (counter==100) {
                    System.out.print(".");
                    counter=0;
                }
            }
        
        }
        System.out.println();
        
        int lastTail=13202338;
        while(BinaryOperations.readingTail.length()>BinaryOperations.minimalByteWord) {
 

            String ch=BinaryOperations.readBits("");
            //System.out.println(BinaryOperations.readingTail.length());
            if (ch!=null) bw.write(ch);
            //System.out.println(BinaryOperations.readingTail.length()+"   "+BinaryOperations.minimalByteWord);

            // если хвост перестал сокращаться - значит, программа застряла, а в хвосте случайная последовательность бит,
            // не подлежащая декодированию (хотя, может и съесть несколько последних символов.)
            if (lastTail==BinaryOperations.readingTail.length()) break;
            lastTail = BinaryOperations.readingTail.length();
        }

        
        bis.close();
        bw.close();
    }


}