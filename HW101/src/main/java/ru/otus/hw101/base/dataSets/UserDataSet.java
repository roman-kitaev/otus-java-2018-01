package ru.otus.hw101.base.dataSets;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PhoneDataSet> phones = new HashSet<PhoneDataSet>();

    //Important for Hibernate
    public UserDataSet() {
    }

    public UserDataSet(String name) {
        this.name = name;
    }

    public UserDataSet(String name, AddressDataSet address) {
        this.name = name;
        this.address = address;
    }

    public UserDataSet(String name, AddressDataSet address, Set<PhoneDataSet> phones) {
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public void addPhone(PhoneDataSet phoneDataSet) {
        phones.add(phoneDataSet);
        phoneDataSet.setUser(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phones +
                '}';
    }
}

