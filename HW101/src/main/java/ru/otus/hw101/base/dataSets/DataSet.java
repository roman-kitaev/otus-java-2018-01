package ru.otus.hw101.base.dataSets;

import javax.persistence.*;

@MappedSuperclass
class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }
}
