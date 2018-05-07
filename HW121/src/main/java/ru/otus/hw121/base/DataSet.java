package ru.otus.hw121.base;

/**
 * Created by rel on 12.04.2018.
 */
public abstract class DataSet {
    protected long id = -1;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
