package ru.otus.hw071;

import ru.otus.hw071.observ.Observable;
import ru.otus.hw071.observ.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rel on 28.03.2018.
 */
public class ATMDepartment implements Observable {
    List<Observer> observerList = new ArrayList<>();

    @Override
    public void registerObserver(Observer ob) {
        observerList.add(ob);
    }

    @Override
    public void removeObserver(Observer ob) {
        observerList.remove(ob);
    }

    @Override
    public int getBalances() {
        int total = 0;
        for(Observer observer : observerList) {
            total += observer.getBalance();
        }
        return total;
    }

    @Override
    public void setInitialStates() {
        for(Observer observer : observerList) {
            observer.setInitialState();
        }
    }
}
