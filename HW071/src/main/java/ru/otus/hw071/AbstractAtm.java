package ru.otus.hw071;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public abstract class AbstractAtm {
    protected Map<Integer, Integer> currencyAmount = new TreeMap<Integer, Integer>();
    public AbstractAtm() {
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
    public void defaultLoad() {
        Iterator<Map.Entry<Integer, Integer>> iter = currencyAmount.entrySet().iterator();
        while(iter.hasNext()) {
            Integer bankNoteToPut = iter.next().getKey();
            for(int i = 0; i < 5; i++) {
                putSum(bankNoteToPut);
            }
        }
    }
    public void putSum(int bankNote) {
        try {
            int currState = currencyAmount.get(bankNote);
            currState++;
            currencyAmount.put(bankNote, currState);
            System.out.println(bankNote + " was accepted");
        } catch (NullPointerException e) {
            System.out.println("I don`t work with this bank note!");
        }
    }
    public void withDraw(int sumToGet) {
        System.out.println("I`m trying to withdraw : " + sumToGet + " RUB");
        Map<Integer, Integer> result = getSum(sumToGet);
        if(result != null) {
            System.out.println("Please take it : ");
            result.forEach(new BiConsumer<Integer, Integer>() {
                public void accept(Integer integer, Integer integer2) {
                    System.out.println(integer + " x" + integer2);
                }
            });
        } else {
            System.out.println("I don`t have enough coins for this sum.");
        }
    }
    public void getBalance() {
        Iterator<Map.Entry<Integer, Integer>> iter = currencyAmount.entrySet().iterator();
        Integer sum = 0;
        while(iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = iter.next();
            sum += entry.getKey() * entry.getValue();
        }
        System.out.println("Current balance is : " + sum);
    }
    @Override
    public String toString() {
        return currencyAmount.toString();
    }
    protected abstract Map<Integer, Integer> getSum(int sumToGet);
}
