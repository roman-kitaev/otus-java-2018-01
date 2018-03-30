package ru.otus.hw071.atm;

import ru.otus.hw071.observ.Observable;

import java.util.*;

public class GreedyAlgorithmAtm extends AbstractAtm {
    public GreedyAlgorithmAtm(Observable observable) {
        super(observable);
    }
    @Override
    public Map<Integer, Integer> getSum(int sumToGet) {
        Map<Integer, Integer> currentSumMap = new TreeMap<Integer, Integer>(currencyAmount);
        List<Integer> list = new ArrayList<Integer>(currentSumMap.keySet());
        ListIterator<Integer> iter = list.listIterator(list.size());

        while(iter.hasPrevious()) {
            Integer currBankNoteToCheck = iter.previous();
            while(currentSumMap.get(currBankNoteToCheck) > 0 && sumToGet >= currBankNoteToCheck) {
                sumToGet -= currBankNoteToCheck;
                int tmp = currentSumMap.get(currBankNoteToCheck);
                currentSumMap.put(currBankNoteToCheck, (tmp - 1));
            }
            if(sumToGet == 0) break;
        }

        Map<Integer, Integer> result = null;
        if(sumToGet == 0) {
            result = new TreeMap<Integer, Integer>();
            iter = list.listIterator(list.size());
            while(iter.hasPrevious()) {
                int currBankNote = iter.previous();
                int difference = currencyAmount.get(currBankNote) - currentSumMap.get(currBankNote);
                if(difference > 0) {
                    result.put(currBankNote, difference);
                }
            }
            currencyAmount = currentSumMap;
        }
        return result;
    }
}
