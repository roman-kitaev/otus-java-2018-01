package ru.otus.hw061.algorithm;

import java.util.*;

/**
 * Created by rel on 02.04.2018.
 */
public class Greedy implements Algorithm {
    @Override
    public Map<Integer, Integer> getSum(int sumToGet, Map<Integer, Integer> currencyAmount) {
        Map<Integer, Integer> currentSumMap = new TreeMap<Integer, Integer>(currencyAmount);
        List<Integer> list = new ArrayList<Integer>(currentSumMap.keySet());
        ListIterator<Integer> iter = list.listIterator(list.size());

        while(iter.hasPrevious()) {
            Integer currCoinToCheck = iter.previous();
            while(currentSumMap.get(currCoinToCheck) > 0 && sumToGet >= currCoinToCheck) {
                sumToGet -= currCoinToCheck;
                int tmp = currentSumMap.get(currCoinToCheck);
                currentSumMap.put(currCoinToCheck, (tmp - 1));
            }
            if(sumToGet == 0) break;
        }

        Map<Integer, Integer> result = null;
        if(sumToGet == 0) {
            result = new TreeMap<Integer, Integer>();
            iter = list.listIterator(list.size());
            while(iter.hasPrevious()) {
                int currCoin = iter.previous();
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
