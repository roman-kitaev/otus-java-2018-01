package ru.otus.hw071.atm;

import ru.otus.hw071.memento.AtmMemento;
import ru.otus.hw071.observ.Observable;
import ru.otus.hw071.observ.Observer;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class AbstractAtm implements Observer{
    protected Map<Integer, Integer> currencyAmount = new TreeMap<Integer, Integer>();
    protected static Random rand = new Random(47);
    private static int counter = 0;
    private final int id = counter++;
    private AtmMemento memento;
    public AbstractAtm(Observable observable) {
        observable.registerObserver(this);
        int maxAmount = 10;
        currencyAmount.put(1, rand.nextInt(maxAmount));
        currencyAmount.put(2, rand.nextInt(maxAmount));
        currencyAmount.put(5, rand.nextInt(maxAmount));
        currencyAmount.put(10, rand.nextInt(maxAmount));
        currencyAmount.put(50, rand.nextInt(maxAmount));
        currencyAmount.put(100, rand.nextInt(maxAmount));
        currencyAmount.put(200, rand.nextInt(maxAmount));
        currencyAmount.put(500, rand.nextInt(maxAmount));
        currencyAmount.put(1000, rand.nextInt(maxAmount));
        currencyAmount.put(2000, rand.nextInt(maxAmount));
        currencyAmount.put(5000, rand.nextInt(maxAmount));
        memento = new AtmMemento(currencyAmount);
    }
    public void putSum(int bankNote) {
        try {
            int currState = currencyAmount.get(bankNote);
            currState++;
            currencyAmount.put(bankNote, currState);
            System.out.println(bankNote + " was accepted to Atm № " + id);
        } catch (NullPointerException e) {
            System.out.println("I don`t work with this bank note!");
        }
    }
    public void withDraw(int sumToGet) {
        System.out.println("I`m trying to withdraw : " + sumToGet + " RUB from Atm № " + id);
        Map<Integer, Integer> result = getSum(sumToGet);
        if(result != null) {
            System.out.println("Please take it : ");
            result.forEach(new BiConsumer<Integer, Integer>() {
                public void accept(Integer bankNote, Integer amount) {
                    System.out.println(bankNote + " x" + amount);
                }
            });
        } else {
            System.out.println("I don`t have enough coins for this sum.");
        }
    }

    @Override
    public int getBalance() {
        Iterator<Map.Entry<Integer, Integer>> iter = currencyAmount.entrySet().iterator();
        Integer sum = 0;
        while(iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = iter.next();
            sum += entry.getKey() * entry.getValue();
        }
        return sum;
    }

    @Override
    public void setInitialState() {
        System.out.println("Atm № " + id + " is in initial state now!");
        currencyAmount = new TreeMap<>(memento.getSavedData());
    }

    @Override
    public String toString() {
        return "Atm № " + id + " : " + currencyAmount.toString();
    }

    protected abstract Map<Integer, Integer> getSum(int sumToGet);
}
