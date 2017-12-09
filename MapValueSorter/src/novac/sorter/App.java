package novac.sorter;

import novac.sorter.mapvaluesorter.MapValueSorter;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        MapValueSorter<String, Integer> mvs = new MapValueSorter<>(String.class, Integer.class);
        HashMap<String, Integer> map = new HashMap<>();

        map.put("l",1322);
        map.put("k",23);
        map.put("b",324);
        map.put("a",3);
        map.put("h",324);
        map.put("z",34);
        map.put("g", 13);
        map.put("p",7665);

        System.out.println(map+" "+map.size());

        Map<String, Integer> sortedMap = mvs.sortMapByValue(map,true);

        System.out.println(sortedMap+" "+sortedMap.size());
    }
}
