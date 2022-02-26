package edu.gatech.cs6310.store;

import java.util.*;

import static edu.gatech.cs6310.DeliveryService.formatter;

public class Store {

    private String name;
    private double settledRevenue;
    private Map<String, Item> items = new HashMap<>();
    private Map<String, Drone> drones = new HashMap<>();
    private Map<String, Order> orders = new HashMap<>();

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
        List<Item> result = new ArrayList<>();
        items.forEach((k, v) -> result.add(v));
        result.sort(Comparator.comparing(Item::getItemName));
        return result;
    }

    public void addDrone(Drone newDrone) {
        if (drones.containsKey(newDrone.getDroneID())) throw new StoreOperationException("drone_identifier_already_exists");
        drones.put(newDrone.getDroneID(), newDrone);
    }

    public List<Drone> getAllDrones() {
        List<Drone> result = new ArrayList<>();
        drones.forEach((k, v) -> result.add(v));
        result.sort(Comparator.comparing(Drone::getDroneID));
        return result;
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

    public List<Order> getAllOrders() {
        List<Order> result = new ArrayList<>();
        orders.forEach((k, v) -> result.add(v));
        result.sort(Comparator.comparing(Order::getOrderID));
        return result;
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
