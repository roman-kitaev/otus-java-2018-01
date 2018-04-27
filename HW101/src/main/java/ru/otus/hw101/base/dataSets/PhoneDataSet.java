package ru.otus.hw101.base.dataSets;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet {

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
