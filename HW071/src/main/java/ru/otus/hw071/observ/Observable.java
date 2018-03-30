package ru.otus.hw071.observ;

/**
 * Created by rel on 28.03.2018.
 */
public interface Observable {
    void registerObserver(Observer ob);
    void removeObserver(Observer ob);

    int getBalances();
    void setInitialStates();
}
