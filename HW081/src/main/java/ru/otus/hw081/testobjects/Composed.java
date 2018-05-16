package ru.otus.hw081.testobjects;

import java.util.Objects;

/**
 * Created by rel on 13.05.2018.
 */
public class Composed {
    private int i;
    private String str;
    private OneMoreComposed oneMoreComposed;

    public Composed(int i, String str) {
        this.i = i;
        this.str = str;
        oneMoreComposed = new OneMoreComposed(10000);
    }

    @Override
    public String toString() {
        return "Composed{" +
                "i=" + i +
                ", str='" + str + '\'' +
                ", oneMoreComposed=" + oneMoreComposed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composed composed = (Composed) o;
        return i == composed.i &&
                Objects.equals(str, composed.str) &&
                Objects.equals(oneMoreComposed, composed.oneMoreComposed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, str, oneMoreComposed);
    }
}
