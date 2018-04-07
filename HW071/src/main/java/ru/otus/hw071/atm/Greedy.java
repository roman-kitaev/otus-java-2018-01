package ru.otus.hw071.atm;

import java.util.*;

/**
 * Created by rel on 02.04.2018.
 */
public class Greedy implements Algorithm {
    @Override
    public Map<Nominal, Integer> getSum(int sumToGet, Map<Nominal, Integer> currencyAmount) {
        Map<Nominal, Integer> currentSumMap = new TreeMap<Nominal, Integer>(currencyAmount);
        List<Nominal> list = new ArrayList<Nominal>(currentSumMap.keySet());
        ListIterator<Nominal> iter = list.listIterator(list.size());

        while(iter.hasPrevious()) {
            Integer currCoinToCheck = iter.previous().getValue();
            while(currentSumMap.get(Nominal.getNominalByNumeric(currCoinToCheck)) > 0 && sumToGet >= currCoinToCheck) {
                sumToGet -= currCoinToCheck;
                int tmp = currentSumMap.get(Nominal.getNominalByNumeric(currCoinToCheck));
                currentSumMap.put(Nominal.getNominalByNumeric(currCoinToCheck), (tmp - 1));
            }
            if(sumToGet == 0) break;
        }

        Map<Nominal, Integer> result = null;
        if(sumToGet == 0) {
            result = new TreeMap<Nominal, Integer>();
            iter = list.listIterator(list.size());
            while(iter.hasPrevious()) {
                Nominal currCoin = iter.previous();
                int difference = currencyAmount.get(currCoin) - currentSumMap.get(currCoin);
                if(difference > 0) {
                    result.put(currCoin, difference);
                    currencyAmount.put(currCoin, currentSumMap.get(currCoin));
                }
            }
        }
        return result;
    }
}

