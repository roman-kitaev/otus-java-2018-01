package ru.otus.hw081.testobjects;

import java.util.Objects;

/**
 * Created by rel on 13.05.2018.
 */
public class OneMoreComposed {
    private int i;

    public OneMoreComposed(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "OneMoreComposed{" +
                "i=" + i +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneMoreComposed that = (OneMoreComposed) o;
        return i == that.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }
}
