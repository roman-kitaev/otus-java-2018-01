package ru.otus.hw071.atm;

import ru.otus.hw071.observ.Observable;

import java.util.*;

public class DPAlgorithmAtm extends AbstractAtm{
    public DPAlgorithmAtm(Observable observable) {
        super(observable);
    }
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
                if(entry.getValue() > 0 && (i - entry.getKey()) > 0) { //which bankNote we can use
                    int bankNoteToCheck = entry.getKey();
                    int neighborToCheck = i - bankNoteToCheck;
                    if(backMass[neighborToCheck] == null) {
                        continue;
                    }
                    Integer neighborAlreadyHasBankNotesToCheck = backMass[neighborToCheck].get(bankNoteToCheck);
                    int weHaveThisBankNoteInAtm = currencyAmount.get(bankNoteToCheck);
                    if(neighborAlreadyHasBankNotesToCheck == null) {
                        if(weHaveThisBankNoteInAtm > 0) {
                            if(min == null || min > mass[neighborToCheck] + 1) {
                                min = mass[neighborToCheck] + 1;
                                mapForBackMass = new TreeMap<Integer, Integer>(backMass[neighborToCheck]);
                                mapForBackMass.put(bankNoteToCheck, 1);
                            }
                        }
                        else continue;
                    } else {
                        if(weHaveThisBankNoteInAtm >= neighborAlreadyHasBankNotesToCheck + 1) {
                            min = mass[neighborToCheck] + 1;
                            mapForBackMass = new TreeMap<Integer, Integer>(backMass[neighborToCheck]);
                            mapForBackMass.put(bankNoteToCheck, neighborAlreadyHasBankNotesToCheck + 1);
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
