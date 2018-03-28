package ru.otus.hw071;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DPAlgorithmAtm extends AbstractAtm{
    @Override
    public Map<Integer, Integer> getSum(int sumToGet) {
        Integer[] mass = new Integer[sumToGet + 1];
        Map<Integer, Integer>[] backMass = new TreeMap[sumToGet + 1];

        mass[0] = 0;
        for(int i = 1; i <= sumToGet; i++) {
            if(currencyAmount.get(i) != null) /*&&*/ { if(currencyAmount.get(i) > 0) {
                mass[i] = 1;
                backMass[i] = new TreeMap();
                backMass[i].put(i, 1);
                continue;
            }}
            Integer min = null;
            Map<Integer, Integer> mapForBackMass = null;
            for(Map.Entry<Integer, Integer> entry : currencyAmount.entrySet()) {
                if(entry.getValue() > 0 && (i - entry.getKey()) > 0) { //which coin we can use
                    int coinToCheck = entry.getKey();
                    int neighborToCheck = i - coinToCheck;
                    if(backMass[neighborToCheck] == null) {
                        continue;
                    }
                    Integer neighborAlreadyHasCoinsToCheck = backMass[neighborToCheck].get(coinToCheck);
                    int weHaveThisCoinInAtm = currencyAmount.get(coinToCheck);
                    if(neighborAlreadyHasCoinsToCheck == null) {
                        if(weHaveThisCoinInAtm > 0) {
                            if(min == null || min > mass[neighborToCheck] + 1) {
                                min = mass[neighborToCheck] + 1;
                                mapForBackMass = new TreeMap<Integer, Integer>(backMass[neighborToCheck]);
                                mapForBackMass.put(coinToCheck, 1);
                            }
                        }
                        else continue;
                    } else {
                        if(weHaveThisCoinInAtm >= neighborAlreadyHasCoinsToCheck + 1) {
                            min = mass[neighborToCheck] + 1;
                            mapForBackMass = new TreeMap<Integer, Integer>(backMass[neighborToCheck]);
                            mapForBackMass.put(coinToCheck, neighborAlreadyHasCoinsToCheck + 1);
                        }
                        else continue;
                    }
                }
            }
            mass[i] = min;
            backMass[i] = mapForBackMass;
        }

        if(mass[sumToGet] != null) {
            Iterator<Map.Entry<Integer, Integer>> iter = backMass[sumToGet].entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry<Integer, Integer> entry = iter.next();

                Integer oldVal = currencyAmount.get(entry.getKey());
                Integer newVal = oldVal - entry.getValue();
                currencyAmount.put(entry.getKey(), newVal);
            }
        }
        return backMass[sumToGet];
    }
}
