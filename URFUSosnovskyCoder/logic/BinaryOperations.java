package logic;

import java.util.Map;

public class BinaryOperations {
    public static void _main() {

        /*{011010000=ъ, 011010001=ф, 011010010=щ, 011010011=э, 11101000=ю, 11101001=ц, 0110101=ш, 1110101=ж, 011011=ч, 100010=х, 100011=я, 110110=з, 110111=й, 111011=б, 00010=ы, 00011=ь, 01000=п, 01001=г, 01100=к, 10000=у, 10110=р, 10111=м, 11010=д, 11100=в, 0000=с, 0101=л, 0111=т, 1001=и, 1010=а, 1100=н, 1111=е, 001=о}*/
        // _: 00
        // D: 01
        // A: 10
        // E: 110
        // C: 1110
        // B: 1111
       /* String s = "Dnoooo";
        byte[] bytes = s.getBytes();
       
        String bitsOfByte = bitsToString((byte)bytes[2]);
        System.out.println(bitsOfByte);
        System.out.println((char)stringToBits(bitsOfByte));*/

       /* writeBits("11100010000111101");
        writeBits("1");
        writeBits("11");
        writeBits("0000111");
        writeBitsFlush();
        writeBits("010110111111110");
        writeBits("1011");
        writeBitsFlush();
        writeBits("011010000");*/
        
        /*String a = readBits("1101");
        if (a!=null) System.out.println(a);
        a = readBits("11");
        if (a!=null) System.out.println(a);
        a = readBits("1");
        if (a!=null) System.out.println(a);
        a = readBits("10");
        if (a!=null) System.out.println(a);*/

        /*writeBits("000001000010");
        writeBits("0000100010");
        writeBitsFlush();*/
        
        /*System.out.println(readBits("11011"));
        System.out.println(readBits("1"));*/
    }

    public static void setDecodeBook(Map<String,String> map) {
        minimalByteWord=1000;
        String[] keys = new String[map.size()];
        map.keySet().toArray(keys);
        for (String k:keys) {
            if (minimalByteWord>k.length()) minimalByteWord=k.length();
        }
        // System.out.println("MINIMAL: "+minimalByteWord);
        codeBook=map;
    }

    public static String readingTail="";
    public static int minimalByteWord;
    private static Map<String,String> codeBook;

    /**
     * Method for comparing input binaryString with
     * code book and decoding.
     * @param binaryString - string like 001 or 11101110
     */
    public static String readBits (String binaryString) {
        char[] binary = (readingTail+binaryString).toCharArray();
        String iterable=""; // в начале хвост, затем новые биты
        readingTail="";
        for (int i=0; i<binary.length; i++) {
            iterable+=binary[i];
            //System.out.println("iterable: "+iterable);
            if (codeBook.get(iterable)!=null) {
                for (int j=i+1; j<binary.length; j++) {
                    if (binary.length<=j) break;
                    readingTail+=binary[j];
                }
                //System.out.println("tail:" +readingTail);
                return codeBook.get(iterable);
            }

        }
        readingTail=iterable;
        //System.out.println("tail:"+ readingTail);
        return null;
    }  


    
    // эта переменная должна храниться вне метода
    private static String tail=""; // остаток с прошлой операции
    /**
     * Do not use codeIn with length>=16. It's kinda work here,
     * but im not sure :)
     * variable tail is VERY important to this, so leave it OUTSIDE
     */
    public static Byte writeBits (String codeIn) {
        //System.out.println("input:"+codeIn);
        String forWrite="";

        // если у нас есть остаток с прошлого раза, его нужно написать 
        // в первую очередь, поэтому прибавляем
        // тут программа может сломаться при коде больше 8 длиной, но вроде починил
        if (tail.length()>0) {
            if (tail.length()>7) {
                forWrite+=tail.substring(0,8);
                tail=tail.substring(8);
            } else {
                forWrite+=tail;
                tail="";
            }
            
        }
        char[] input = codeIn.toCharArray();

        for (int i=0; i<input.length; i++) {
            // если закончились места для записи,
            // пишем биты в остаток
            if (forWrite.length()==8) {
                tail+=input[i];
                continue;
            }
            forWrite+=input[i];
        }

        // если длина сообщения равна длине байта
        if (forWrite.length()==8) {
            //System.out.println(forWrite);
            return stringToBits(forWrite);
            /* ТУТ РЕАЛИЗОВАТЬ МЕТОД ОТПРАВКИ В ФАЙЛ */
        } else {
            // если не хватило на отправку - пишем в остаток
            tail+=forWrite;
            forWrite="";
        }

        /*System.out.println("for writing:"+forWrite+" "+forWrite.length());
        System.out.println("tail:"+tail+" "+tail.length());
        System.out.println();*/
        return null;
    } // end of writeFile method


    /**
     * Writes zeros to complete one byte for
     * writing in file in writeBits()
     */
    public static Byte writeBitsFlush() {
        if (tail.length()==0) return null;
        int flushCounter=7-tail.length();
        String flush="";
        while(flushCounter!=-1) {
            flush+="0";
            flushCounter--;
        }
        Byte r= writeBits(flush);
        return r;
    }



    /**
     * Converts bits string in byte 
     * @param codeIn - String line of bits like 01001000
     */
    public static byte stringToBits(String codeIn) {
        if (codeIn.length()!=8) return -1;
        byte b=0b00000000;
        char[] code =codeIn.toCharArray();

        for (int i=0; i<code.length; i++) {
            if (code[i]=='0') continue;
            switch (i) {
                // каждый разряд, который не был 0 (выше проверка)
                // в байте b заполняю битами 1
                case(0): b|=0b10000000; break;
                case(1): b|=0b01000000; break;
                case(2): b|=0b00100000; break;
                case(3): b|=0b00010000; break;
                case(4): b|=0b00001000; break;
                case(5): b|=0b00000100; break;
                case(6): b|=0b00000010; break;
                case(7): b|=0b00000001; break;
            }
        }
        return b;
    }


    // возвращает биты из байта, представленные строкой
    /**
     * This method converts byte to line of bits
     * like 01010001
     * @param bits - byte variable to convert to String of bits
     */
    public static String bitsToString(byte bits) {
        int iter=1;
        String r="";
        while (iter<=9) {
            byte check;
            // на самом деле, метод работает последовательно,
            // и switch/case ему не требуется. Однако, выглядит лучше
            switch(iter) {
                // беру байт с единицой и нулями, умножаю на полученный байт
                // если полученный байт совпал с первым, значит, там
                // тоже быле единица, иначе - там был ноль
                case(1): check=(byte)0b1000_0000;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

                case(2): check=0b0100_0000;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

                case(3): check=0b0010_0000;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

                case(4): check=0b0001_0000;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

                case(5): check=0b0000_1000;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

                case(6): check=0b0000_0100;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

                case(7): check=0b0000_0010;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

                case(8): check=0b0000_0001;
                if ( (check&bits)==check ) r+="1";
                else r+="0";
                break;

            }
            iter++;
        }
        return r;
    }

}