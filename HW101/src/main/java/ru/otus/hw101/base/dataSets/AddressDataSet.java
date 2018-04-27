package ru.otus.hw101.base.dataSets;

import javax.persistence.*;

/**
 * Created by rel on 24.04.2018.
 */

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    @Column(name = "name")
    private String address;

    public AddressDataSet() {
    }

    public AddressDataSet(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "address='" + address + '\'' +
                '}';
    }
}
