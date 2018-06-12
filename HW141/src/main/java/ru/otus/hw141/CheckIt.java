package ru.otus.hw141;

/**
 * Created by rel on 12.06.2018.
 */
public class CheckIt {
    public static boolean checkTheArray(int[] array) {
        for(int i = 0; i < array.length - 1; i++) {
            if(array[i] <= array[i + 1]) { /* everything is fine */ }
            else return false;
        }
        return true;
    }
}
