package edu.gatech.cs6310.store;

import static edu.gatech.cs6310.DeliveryService.formatter;

public class Item {
    private String name;
    private double weight;

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getItemName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return getItemName() + "," + formatter.format(getWeight());
    }
}
