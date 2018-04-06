package ru.otus.hw061.algorithm;

import ru.otus.hw061.Nominal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by rel on 02.04.2018.
 */
public class DynamicProgramming implements Algorithm {
    @Override
    public Map<Nominal, Integer> getSum(int sumToGet, Map<Nominal, Integer> currencyAmount) {
        Integer[] mass = new Integer[sumToGet + 1];
        Map<Nominal, Integer>[] backMass = new TreeMap[sumToGet + 1];

        mass[0] = 0;
        for(int i = 1; i <= sumToGet; i++) {
            if(Nominal.getNominalByNumeric(i) != null
                    && currencyAmount.get(Nominal.getNominalByNumeric(i)) > 0) {
                mass[i] = 1;
                backMass[i] = new TreeMap();
                backMass[i].put(Nominal.getNominalByNumeric(i), 1);
                continue;
            }
            Integer min = null;
            Map<Nominal, Integer> mapForBackMass = null;
            for(Map.Entry<Nominal, Integer> entry : currencyAmount.entrySet()) {
                if(entry.getValue() > 0 && (i - entry.getKey().getValue()) > 0) { //which coin we can use
                    int coinToCheck = entry.getKey().getValue();
                    int neighborToCheck = i - coinToCheck;
                    if(backMass[neighborToCheck] == null) {
                        continue;
                    }
                    Integer neighborAlreadyHasCoinsToCheck = backMass[neighborToCheck].get(Nominal.getNominalByNumeric(coinToCheck));
                    int weHaveThisCoinInAtm = currencyAmount.get(Nominal.getNominalByNumeric(coinToCheck));
                    if(neighborAlreadyHasCoinsToCheck == null) {
                        if(weHaveThisCoinInAtm > 0) {
                            if(min == null || min > mass[neighborToCheck] + 1) {
                                min = mass[neighborToCheck] + 1;
                                mapForBackMass = new TreeMap<Nominal, Integer>(backMass[neighborToCheck]);
                                mapForBackMass.put(Nominal.getNominalByNumeric(coinToCheck), 1);
                            }
                        }
                        else continue;
                    } else {
                        if(weHaveThisCoinInAtm >= neighborAlreadyHasCoinsToCheck + 1) {
                            min = mass[neighborToCheck] + 1;
                            mapForBackMass = new TreeMap<Nominal, Integer>(backMass[neighborToCheck]);
                            mapForBackMass.put(Nominal.getNominalByNumeric(coinToCheck), neighborAlreadyHasCoinsToCheck + 1);
                        }
                        else continue;
                    }
                }
            }

            mass[i] = min;
            backMass[i] = mapForBackMass;
        }
        System.out.println(Arrays.toString(mass));
        if(mass[sumToGet] != null) {
            Iterator<Map.Entry<Nominal, Integer>> iter = backMass[sumToGet].entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry<Nominal, Integer> entry = iter.next();

                Integer oldVal = currencyAmount.get(entry.getKey());
                Integer newVal = oldVal - entry.getValue();
                currencyAmount.put(entry.getKey(), newVal);
            }
        }
        return backMass[sumToGet];
    }
}
