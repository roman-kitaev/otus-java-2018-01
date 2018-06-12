package ru.otus.hw141;

import java.util.Arrays;

/**
 * Created by rel on 10.06.2018.
 */
public class SortIt implements Runnable {
    int[] array;
    Section section;

    public SortIt(int[] array, Section section) {
        this.array = array;
        this.section = section;
    }

    @Override
    public void run() {
        Arrays.sort(array, section.getBegin(), section.getEnd());
    }
}
