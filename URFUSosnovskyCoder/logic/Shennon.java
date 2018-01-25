package logic;

import java.util.*;

public class Shennon {
    
    // формула Шеннона для подсчёта энтропии
    public static double shennonFormula(LinkedHashMap map) {
        Integer[] values = new Integer[map.size()];
        Map.Entry[] inputs = new Map.Entry[map.size()];
        map.entrySet().toArray(inputs);
        for (int i=0;i<map.size();i++){
            values[i]=(Integer)inputs[i].getValue();
        }

        Integer sum=0;
        for (Integer i:values) sum+=i;
        
        double HX=0;
        for (int i=0; i<map.size();i++) {
            double px = (double) values[i].intValue() / (double) sum.intValue();
            HX+=px*logBy(px,2);
        }

        // округление 
        HX*=-100;
        int hx = (int)Math.round(HX);
        HX=(double)hx/100;

        return HX;
    }


    public static double logBy (double number, double base) {
        return Math.log(number)/Math.log(base);
    }
}