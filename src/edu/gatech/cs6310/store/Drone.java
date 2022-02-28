package edu.gatech.cs6310.store;

import java.util.*;

import static edu.gatech.cs6310.DeliveryService.formatter;

public class Drone {

    private String droneID;
    private double cap;
    private int tripsLeft;
    private Map<String, Order> currentOrders = new TreeMap<>();
    private Pilot currentPilot;

    public Drone(String droneID, double capacity, int tripsLeft) {
        this.droneID = droneID;
        this.cap = capacity;
        this.tripsLeft = tripsLeft;
    }

    public String getDroneID() {
        return droneID;
    }

    public double getCapacity() {
        return cap;
    }

    public int getNumOrders() {
        return currentOrders.size();
    }

    public int getTripsLeft() {
        return tripsLeft;
    }


    public double getCurrentWeight() {
        double sum = 0;
        for (Order o : currentOrders.values()) {
            sum += o.getCurrentOderWeight();
        }
        return sum;
    }

    public double remainingCap() {
        return (cap - getCurrentWeight());
    }

    public String toString() {
        return "droneID:" + getDroneID() + ",total_cap:" +
                formatter.format(getCapacity()) + ",num_orders:" + getNumOrders() + ",remaining_cap:" +
                formatter.format(remainingCap()) + ",trips_left:" + getTripsLeft() +
                (currentPilot != null ? ",flown_by:" + currentPilot.getFirstName() + "_" + currentPilot.getLastName() : "");
    }

    public void removePilot() {
        currentPilot = null;
    }

    public void addPilot(Pilot pilot) {
        currentPilot = pilot;
    }

    public Pilot getPilot() {
        return currentPilot;
    }

    public void setTripsLeft(int newTripsLeft) {
        this.tripsLeft = newTripsLeft;
    }

    public void addOrderToDrone(Order newOrder) {
        currentOrders.put(newOrder.getOrderID(), newOrder);
    }

    public void remove(Order o) {
        currentOrders.remove(o.getOrderID());
    }
}
