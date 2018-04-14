package ru.otus.hw091.base;

/**
 * Created by rel on 12.04.2018.
 */
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public UserDataSet(int id, String name, int age) {
        super(id);
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() { return age; }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
                '}';
    }
}
