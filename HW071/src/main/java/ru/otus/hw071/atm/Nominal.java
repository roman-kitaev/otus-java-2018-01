package ru.otus.hw071.atm;

/**
 * Created by rel on 06.04.2018.
 */
public enum Nominal {
    FIVE(5), TEN(10), FIFTY(50), HUNDRED(100), SUPER(294);

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