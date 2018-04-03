package ru.otus.hw061;

import ru.otus.hw061.algorithm.Algorithm;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ATM {
    private Algorithm algorithm;
    private Map<Integer, Integer> currencyAmount = new TreeMap<Integer, Integer>();
    public ATM(Algorithm algorithm) {
        this.algorithm = algorithm;
        currencyAmount.put(1, 0);
        currencyAmount.put(2, 0);
        currencyAmount.put(5, 0);
        currencyAmount.put(10, 0);
        currencyAmount.put(50, 0);
        currencyAmount.put(100, 0);
        currencyAmount.put(200, 0);
        currencyAmount.put(500, 0);
        currencyAmount.put(1000, 0);
        currencyAmount.put(2000, 0);
        currencyAmount.put(5000, 0);
    }
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public void defaultLoad(int initialAmount) {
        Iterator<Map.Entry<Integer, Integer>> iter = currencyAmount.entrySet().iterator();
        while(iter.hasNext()) {
            Integer bankNoteToPut = iter.next().getKey();
            for(int i = 0; i < initialAmount; i++) {
                putSum(bankNoteToPut);
            }
        }
    }
    public boolean putSum(int bankNote) {
        int currState = 0;
        try {
            currState = currencyAmount.get(bankNote);
        } catch (NullPointerException e) {
            return false;
        }
        currState++;
        currencyAmount.put(bankNote, currState);
        return true;
    }
    public Map<Integer, Integer> withDraw(int sumToGet) {
        return algorithm.getSum(sumToGet, currencyAmount);
    }
    public int getBalance() {
        return currencyAmount.entrySet().stream().
                map(e -> e.getValue() * e.getKey()).
                reduce((s1, s2) -> s1 + s2).orElse(0);
    }
    @Override
    public String toString() {
        return currencyAmount.toString();
    }
}
