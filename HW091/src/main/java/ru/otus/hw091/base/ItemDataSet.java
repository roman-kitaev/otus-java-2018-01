package ru.otus.hw091.base;

/**
 * Created by rel on 18.04.2018.
 */
public class ItemDataSet extends DataSet {
    private String name;
    private int price;
    private int weight;

    public ItemDataSet() {}

    public long getId() { return id; }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setId(long id) { this.id = id; }

    @Override
    public String toString() {
        return "ItemDataSet{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", weight=" + weight +
                '}';
    }
}
