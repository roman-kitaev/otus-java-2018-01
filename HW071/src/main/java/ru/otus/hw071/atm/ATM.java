package ru.otus.hw071.atm;

import ru.otus.hw071.memento.AtmMemento;
import ru.otus.hw071.observ.Observer;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class ATM implements Observer{
    private Algorithm algorithm;
    private AtmMemento memento;
    private static Random rand = new Random(47);
    private static int counter = 0;
    private final int id = counter++;
    private static final int maxValueForFillingByRandom = 15;
    private Map<Nominal, Integer> currencyAmount = new TreeMap<Nominal, Integer>();
    public ATM(Algorithm algorithm) {
        this.algorithm = algorithm;
        for(Nominal nominal : Nominal.values()) {
            currencyAmount.put(nominal, rand.nextInt(maxValueForFillingByRandom));
        }
        memento = new AtmMemento(currencyAmount);
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
    public void setInitialState() {
        System.out.println("Atm № " + id + " is in initial state now!");
        currencyAmount = new TreeMap<>(memento.getSavedData());
    }

    @Override
    public String toString() {
        return currencyAmount.toString();
    }
}
