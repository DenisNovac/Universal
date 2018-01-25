package logic;
import java.util.LinkedHashMap;
import java.util.Map;
import sorter.MapValueSorter;

public class HuffmanCode {
    private Map<String,String> encodingTable;
    private Map<String,String> decodingTable;

    public void generateEncodingAndDecodingTables(Map<String,Integer> map) {
        encodingTable=createTree(map);
        createDecodingTable();
    }

    public Map<String,String> getEncodingTable() { return encodingTable; }
    public Map<String,String> getDecodingTable() { return decodingTable; }

    // принимаем на вход карту символов
    private Map<String,String> createTree (Map<String,Integer> map) {
        MapValueSorter<String,Integer> mapSorter = new MapValueSorter<>(String.class, Integer.class);
        map = mapSorter.sortMapByValue(map, true); // сортируем по возрастанию

        // карта, которая держит 0 или 1 для всех существовавших узлов древа
        // узлы-родители всегда дальше дочерних. Т.е. если узел входит в другой узел,
        // этот узел всегда дальше в карте, чем данный
        Map<String,Integer> bitMap = new LinkedHashMap(16, (float)0.75, false);

        String[] keySet;
        int operations = 0;
        int firstSize = map.size();

        while(operations<firstSize) {
            // в конце должно получиться сочетание из всех букв с их суммой
            // т.е. в итоге выведет корень дерева. Нам он не понадобится
            //System.out.println(operations+"\n"+map);
            
            keySet = new String[map.size()];
            map.keySet().toArray(keySet); // ключи в том же порядке, что в сортированном древе
            String key1,key2;
            try {
                key1=keySet[0];
                key2=keySet[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
            
            String key1p2 = key1+key2;
            Integer keysv = map.get(key1)+map.get(key2);
            map.put(key1p2, keysv);
            map.remove(key1);
            map.remove(key2);

            bitMap.put(key1,0);
            bitMap.put(key2,1);

            // необходимо, чтобы зачения карты были 
            // по возрастанию на каждой итерацции
            map=mapSorter.sortMapByValue(map, true);
            operations++;
        }

        //System.out.println(bitMap);   
        return generateHuffmanCodes(bitMap); 
    }

    private Map<String,String> generateHuffmanCodes(Map<String,Integer> map) {
        // получили на вход битмап из getTree, собранный по правилу:
        // в начале листья, в конце узлы, корень не входит в карту
        Map<String,String> codeTable = new LinkedHashMap(16, (float)0.75, false);
        String[] keySet = new String[map.size()];
        map.keySet().toArray(keySet);

        for  (int i=0; i<map.size(); i++) {
            String letter = keySet[i];
            String code="";
            code+=map.get(letter).toString();
            if (letter.length()>1) continue; // попался не лист

            // начинаем идти по карте в поисках содержащих этот лист узлов
            for (int j=i+1; j<map.size(); j++) {
                // если наша буква входит в этот узел
                if (keySet[j].contains(letter)) {
                    code=map.get(keySet[j]).toString()+code;
                }
            }
            
            codeTable.put(letter,code);
        }
        //System.out.println(codeTable);
        return codeTable;
    }

    // так как в результате выполнения кода Хаффмана у нас получаются
    // уникальные двочиные значения для каждой буквы, мы можем составить
    // обратную карту для ускорения последующего поиска
    private void createDecodingTable() {
        if (encodingTable==null) return;
        decodingTable = new LinkedHashMap(16, (float)0.75, false);
        String[] keySet = new String[encodingTable.size()];
        encodingTable.keySet().toArray(keySet);
        for (String key:keySet) {
            decodingTable.put(encodingTable.get(key),key);
        }
    }


    // переводит карту <Character,Integer> в <String,Integer>, нужно для кодирования выше
    public Map<String,Integer> mapCharToString(Map<Character,Integer> map) {
        Map<String,Integer> returnMap = new LinkedHashMap(16, (float)0.75, false);
        Character[] keySet = new Character[map.size()];
        map.keySet().toArray(keySet);

        for (Character key:keySet) {
            String s="";
            s+=key;
            returnMap.put(s, map.get(key));
        }
        return returnMap;
    }

}