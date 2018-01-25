package sorter;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapValueSorter<K,V extends Comparable> {

    private Map<K,V> map=null;
    private Class VClass, KClass;

    /**
     * Due to Java Generics construction we need to give classes of
     * keys and values manually. It's not quite "Generic" class now, but
     * it'll give us benefits
     * @param KClass Class of keys from map. Like Integer.class
     * @param VClass Class of values from map. Like Integer.class
     */
    public MapValueSorter(Class KClass, Class VClass) {
        this.VClass=VClass;
        this.KClass=KClass;
    }

    /**
     * @param mapIn - input map for sort by value. It's values must be Comparable
     * @param isAscending - true for sorting from lesser to biggest values
     * @return LinkedHashMap will be returned
     */
    public LinkedHashMap<K,V> sortMapByValue (Map<K,V> mapIn, boolean isAscending) {
        // isAscending - from lesser to biggest values like 1,2,3,4,5
        // false - from biggest: 5,4,3,2,1
        map = mapIn;

        Set<K> keySet = map.keySet();
        // this is why we gave class to MapValueSorter constructor
        // due to Java Generics construction we can't make array
        // like this: K[] keys = new K[];
        K[] keys = (K[]) Array.newInstance(KClass,map.size());
        keySet.toArray(keys);

        // i don't really know how to take array of values in other way
        V[] values = (V[])Array.newInstance(VClass, map.size());
        for (int i=0; i<map.size(); i++)
            values[i]=map.get(keys[i]);

        // quick sort for comparable types
        QuickSort<V> quickSort = new QuickSort<>();
        V[] sorted = quickSort.sort(values,isAscending);
        // for (V v:sorted) System.out.println(v);

        // 16 and 0.75 are default for LinkedHashMap
        // accessOrder - the ordering mode - true for access-order, false for insertion-order
        LinkedHashMap<K,V> sortedMap = new LinkedHashMap(16, (float)0.75, false);
        int size = map.size();

        for (int i=0; i<sorted.length; i++) {
            V value = sorted[i];
            for (int j=0; j<size; j++) {
                K key = keys[j];
                if (key==null) continue;
                if (map.get(key).compareTo(value) == 0) {
                    sortedMap.put(key, value);
                    keys[j]=null;
                    map.remove(key);
                }
            }
        }

        map=null;
        return sortedMap;
    }
}
