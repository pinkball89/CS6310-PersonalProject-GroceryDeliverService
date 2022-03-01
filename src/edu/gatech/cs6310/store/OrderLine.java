package edu.gatech.cs6310.store;

import static edu.gatech.cs6310.DeliveryService.formatter;

public class OrderLine {
    private Item item;
    private int quantity;
    private double unitPrice;
    private double totalCost;
    private double totalWeight;

    public OrderLine(Item item, int quantity, double unitPrice) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalCost = quantity * unitPrice;
        this.totalWeight = quantity * item.getWeight();
    }

    public double getOrderLineCost() {
        return totalCost ;
    }

    public double getOderLineWeight() {
        return totalWeight;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemName () {
        return item.getItemName();
    }
    public String toString() {
        return "item_name:" + getItemName() + ",total_quantity:" + getQuantity() + ",total_cost:" + formatter.format(getOrderLineCost())+ ",total_weight:" + formatter.format(getOderLineWeight());
    }

}
