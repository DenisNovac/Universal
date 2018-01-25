package logic;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;

public class Chance <K,V extends Number> {
    private Class keyClass, valueClass;

    public Chance (Class keyClass, Class valueClass) {
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    // находит вероятность появления символов в тексте
    // по заданию нужны лишь двубуквенные
    // вернёт Map, который можно затем отсортировать
    public Map<K,Double> findChance(Map<K,V> map) {
        // создаём массив значений
        V[] values = (V[])Array.newInstance(valueClass, map.size());
        Map.Entry[] inputs = new Map.Entry[map.size()];
        map.entrySet().toArray(inputs);
        for (int i=0;i<map.size();i++){
            values[i]=(V)inputs[i].getValue();
        }

        int sum=0;
        for (int i=0; i<map.size(); i++)
            sum+=(int)values[i];
        
        K[] keys = (K[])Array.newInstance(keyClass, map.size());
        map.keySet().toArray(keys);

        HashMap<K, Double> returnMap = new HashMap<>();

        for (K k:keys) {
            int i = (int)map.get(k);
            double d = (double)i/(double)sum;
            returnMap.put(k, Double.valueOf(d));
        }

        return returnMap;
    }
}