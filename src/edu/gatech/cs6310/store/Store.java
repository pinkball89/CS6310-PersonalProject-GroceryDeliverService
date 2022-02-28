package edu.gatech.cs6310.store;

import java.util.*;

import static edu.gatech.cs6310.DeliveryService.formatter;

public class Store {

    private String name;
    private double settledRevenue;
    private Map<String, Item> items = new TreeMap<>();
    private Map<String, Drone> drones = new TreeMap<>();
    private Map<String, Order> orders = new TreeMap<>();

    public Store(String name, Double revenue) {
        this.name = name;
        this.settledRevenue = revenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSettledRevenue() {
        return settledRevenue;
    }

    public void addItem(Item newItem) {
        if (items.containsKey(newItem.getItemName())) throw new StoreOperationException("item_identifier_already_exists");
        items.put(newItem.getItemName(), newItem);
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }

    public void addDrone(Drone newDrone) {
        if (drones.containsKey(newDrone.getDroneID())) throw new StoreOperationException("drone_identifier_already_exists");
        drones.put(newDrone.getDroneID(), newDrone);
    }

    public List<Drone> getAllDrones() {
        return new ArrayList<>(drones.values());
    }

   public Drone getDrone(String droneID) {
        if (!drones.containsKey(droneID)) throw new StoreOperationException("drone_identifier_does_not_exist");
        return drones.get(droneID);
    }

    public String toString() {
        return "name:" + getName() + ",revenue:" + formatter.format(getSettledRevenue());
    }

    public void addOrder(Order newOrder) {
        if (orders.containsKey(newOrder.getOrderID())) throw new StoreOperationException("order_identifier_already_exists");
        orders.put(newOrder.getOrderID(), newOrder);
    }

    public boolean orderAlreadyExists(String orderId) {
        return orders.containsKey(orderId);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrderID(String orderID) {
        if (!orders.containsKey(orderID)) throw new StoreOperationException("order_identifier_does_not_exist");
        return orders.get(orderID);
    }

    public Item getItem(String itemName) {
        if (!items.containsKey(itemName)) throw new StoreOperationException("item_identifier_does_not_exist");
        return items.get(itemName);

    }

    public void setSettledRevenue(double newRevenue) {
        this.settledRevenue = newRevenue;
    }

    public void remove(Order o) {
        orders.remove(o.getOrderID());
    }
}
