package ru.otus.hw061.algorithm;

/**
 * Created by rel on 06.04.2018.
 */
public enum Nominal {
    FIVE(5), TEN(10), FIFTY(50), HUNDRED(100), SUPER(293);

    private int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
    public static Nominal getNominalByNumeric(int value) {
        for(Nominal nominal : Nominal.values()) {
            if(nominal.getValue() == value) {
                return nominal;
            }
        }
        return null;
    }
}