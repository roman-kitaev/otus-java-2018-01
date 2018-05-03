package ru.otus.hw111.base;

/**
 * Created by rel on 12.04.2018.
 */
public class UserDataSet extends DataSet {
    private String name;
    private int age;
    private String address;
    private int numberOfChildren;

    public UserDataSet() {}

    public String getAddress() { return address; }

    public String getName() {
        return name;
    }

    public int getAge() { return age; }

    public int getNumberOfChildren() { return numberOfChildren; }

    public long getId() { return id; }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(long id) { this.id = id; }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", address=" + address +
                ", number of children=" + numberOfChildren +
                '}';
    }
}
