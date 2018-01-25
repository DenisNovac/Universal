 import fileparcer.FileParcer;
 import sorter.MapValueSorter;
 import logic.Shennon;
 import logic.Chance;
 import logic.HuffmanCode;
 import logic.BinaryOperations;
 import java.util.LinkedHashMap;
 import java.util.Map;
 import java.io.*;
 import coder.FileCoder;

 public class App {
     // файл, который будем кодировать
     static String filePath = "../test.txt";
     // читать ли все символы
     static boolean isReadAll = true;

     public static void main(String[] args) {
        if (args.length!=0) filePath = args[0];
        if (args.length>1) {
            isReadAll=false;
            System.out.println("readAll = false");
        } else System.out.println("readAll = true");
        System.out.println("File: "+filePath);

        FileParcer fileParcer = new FileParcer();
        MapValueSorter<Character,Integer> msci = new MapValueSorter<>(Character.class, Integer.class);
        MapValueSorter<String,Integer> mssi = new MapValueSorter<>(String.class, Integer.class);
        
        int size = fileParcer.parceFile(filePath, isReadAll);
        System.out.println("All characters in file readed: "+size);
        Map chars = msci.sortMapByValue(fileParcer.getChars(), false);
        System.out.println("Uniq characters count: "+chars.size());
        Map twoChars = mssi.sortMapByValue(fileParcer.getTwoChars(), false);


        System.out.println(chars);
        System.out.println();
        System.out.println(twoChars);

        HuffmanCode huffmanCode = new HuffmanCode();
        huffmanCode.generateEncodingAndDecodingTables(huffmanCode.mapCharToString(chars));
        Map<String,String> enc = huffmanCode.getEncodingTable();
        Map<String,String> dec = huffmanCode.getDecodingTable();
        System.out.println(enc);
        //System.out.println(dec);

        // считаем вероятности появления двубуквенных сочетаний
        Chance<String, Integer> chance = new Chance(String.class, Integer.class);
        Map chanceTwoMap = chance.findChance(twoChars);
        MapValueSorter<String,Double> mssd = new MapValueSorter<>(String.class, Double.class);
        chanceTwoMap = mssd.sortMapByValue(chanceTwoMap, false);
        System.out.println("Вероятности появления двубуквенных сочетаний: ");
        System.out.println(chanceTwoMap);

        // вероятности однобуквенных сочетаний
        Chance<Character, Integer> chanceone = new Chance<>(Character.class, Integer.class);
        Map chanceOneMap = chanceone.findChance(chars);
        MapValueSorter<Character, Double> mscd = new MapValueSorter<>(Character.class, Double.class);
        chanceOneMap = mscd.sortMapByValue(chanceOneMap, false);
        System.out.println("Вероятности появления символов: ");
        System.out.println(chanceOneMap);

        // вывод двубуквенных и однобуквенных сочетаний в файл по вероятностям
        String[] keys = new String[chanceTwoMap.size()];
        chanceTwoMap.keySet().toArray(keys);
        try(PrintWriter bw = new PrintWriter(new FileWriter(filePath+"twoLettersByChances.txt"));) {
            for (String k:keys) {
                bw.write(k+" "+twoChars.get(k)+" ");
                bw.write(chanceTwoMap.get(k).toString()+"\n");
            }
        } catch (IOException e) { }





        // энтропия по одному и двум символам
        System.out.println("Энтропия для однобуквенных сочетаний: ");
        System.out.println(Shennon.shennonFormula((LinkedHashMap)chars));
        System.out.println("Энтропия для двубуквенных сочетаний: ");
        System.out.println(Shennon.shennonFormula((LinkedHashMap)twoChars));

    
        BinaryOperations.setDecodeBook(dec);
        BinaryOperations._main();

       try {
            FileCoder.encode(filePath, enc);
            System.out.println();
            FileCoder.decode(filePath+".sos", dec);
        } catch (Exception e) { e.printStackTrace(); }
        

     }
 }