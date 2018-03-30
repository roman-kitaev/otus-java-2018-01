package ru.otus.hw071.memento;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by rel on 28.03.2018.
 */
public class AtmMemento {
    private Map<Integer, Integer> savedData;
    public AtmMemento(Map<Integer, Integer> dataToSave) {
        savedData = new TreeMap<>(dataToSave);
    }
    public Map<Integer, Integer> getSavedData() {
        return savedData;
    }
}
