package ru.otus.hw061;

import ru.otus.hw061.algorithm.Algorithm;
import ru.otus.hw061.algorithm.Nominal;

import java.util.Map;
import java.util.TreeMap;

public class ATM {
    private Algorithm algorithm;
    private Map<Nominal, Integer> currencyAmount = new TreeMap<Nominal, Integer>();
    public ATM(Algorithm algorithm) {
        this.algorithm = algorithm;
        for(Nominal nominal : Nominal.values()) {
            currencyAmount.put(nominal, 0);
        }
    }
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public void defaultLoad(int initialAmount) {
        for(Nominal nominal : Nominal.values()) {
            for(int i = 0; i < initialAmount; i++) {
                putSum(nominal.getValue());
            }
        }
    }
    public boolean putSum(int bankNote) {
        Nominal currentNoteToCheck = Nominal.getNominalByNumeric(bankNote);
        if(currentNoteToCheck != null) {
            int currState = currencyAmount.get(currentNoteToCheck);
            currState++;
            currencyAmount.put(currentNoteToCheck, currState);
            return true;
        } return false;
    }
    public Map<Nominal, Integer> withDraw(int sumToGet) {
        return algorithm.getSum(sumToGet, currencyAmount);
    }
    public int getBalance() {
        return currencyAmount.entrySet().stream().
                map(e -> e.getValue() * e.getKey().getValue()).
                reduce((s1, s2) -> s1 + s2).orElse(0);
    }
    @Override
    public String toString() {
        return currencyAmount.toString();
    }
}
