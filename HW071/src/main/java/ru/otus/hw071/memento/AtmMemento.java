package ru.otus.hw071.memento;

import ru.otus.hw071.atm.Nominal;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by rel on 28.03.2018.
 */
public class AtmMemento {
    private Map<Nominal, Integer> savedData;
    public AtmMemento(Map<Nominal, Integer> dataToSave) {
        savedData = new TreeMap<>(dataToSave);
    }
    public Map<Nominal, Integer> getSavedData() {
        return savedData;
    }
}
