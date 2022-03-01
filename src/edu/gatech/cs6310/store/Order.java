package edu.gatech.cs6310.store;

import java.time.OffsetDateTime;
import java.util.*;

import static edu.gatech.cs6310.DeliveryService.formatter;

public class Order {
    private Store store;
    private String orderID;
    private Customer customer;
    private Drone drone;
    private Map<String, OrderLine> orderLines = new TreeMap<>();

    public Order(String orderID, Drone d, Customer c) {
        this.orderID = orderID;
        this.drone = d;
        this.customer = c;

    }

    public String getOrderID() {
        return orderID;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        for (OrderLine ol: orderLines.values()) {
            joiner.add(ol.toString());
        }
        String orderlineString = joiner.toString();
        return "orderID:" + getOrderID() + (!orderlineString.isEmpty() ? "\n" + joiner : "");
    }

    public double getCurrentCost() {
        //create a totalCost variable
        //loop through all element inside the set of order lines
        //add the cost of each order line to the totalCost
        //return totalCost
        double totalOrderCost = 0;
        for (OrderLine ol : orderLines.values()) {
            totalOrderCost += ol.getOrderLineCost();
        }
        return totalOrderCost;
    }

    public double getCurrentOderWeight() {
        double totalOrderWeight = 0;
        for (OrderLine ol : orderLines.values()) {
            totalOrderWeight += ol.getOderLineWeight();
        }
        return totalOrderWeight;

    }

    public boolean itemAlreadyExists(String itemName) {
        return orderLines.containsKey(itemName);
    }

    public Drone getDrone() {
        return drone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addOrderLine(OrderLine ol) {
        if (orderLines.containsKey(ol.getItemName())) throw new StoreOperationException("item_already_ordered");
        orderLines.put(ol.getItemName(), ol);
    }

}
