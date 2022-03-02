package edu.gatech.cs6310.store;

import java.util.*;

public class StoreOperation {
    GlobalData globalData = new GlobalData();

    public void newStore(String name, double revenue) {
        Store newStore = new Store(name, revenue);
        globalData.putNewStore(newStore);
    }

    public List<Store> getAllStores() {
        return globalData.getAllStores();
    }

    public void newItem(String storeName, String itemName, double itemWeight) {
        Store s = globalData.getStore(storeName);
        Item newItem = new Item(itemName, itemWeight);
        s.addItem(newItem);
    }

    public List<Item> getAllItems(String storeName) {
        Store s = globalData.getStore(storeName);
        return s.getAllItems();
    }

    public void newPilot(String account, String firstName, String lastName, String phone, String taxID, String licenseID, int successfulDeliveries) {
        Pilot newPilot = new Pilot(account, firstName, lastName, phone, taxID, licenseID, successfulDeliveries);
        globalData.putNewPilot(newPilot);
    }

    public List<Pilot> getAllPilots() {
        return globalData.getAllPilots();
    }

    public void newDrone(String storeName, String droneID, double capacity, int tripsLeft) {
        Store s = globalData.getStore(storeName);
        Drone newDrone = new Drone(droneID, capacity, tripsLeft);
        s.addDrone(newDrone);
    }

    public List<Drone> getAllDrones(String storeName) {
        Store s = globalData.getStore(storeName);
        return s.getAllDrones();
    }

    public void flyDrone(String storeName, String droneID, String pilotAccount) {
        Store s = globalData.getStore(storeName);
        Drone drone = s.getDrone(droneID);
        Pilot pilot = globalData.getPilot(pilotAccount);

        Pilot oldPilot = drone.getPilot();
        Drone oldDrone = pilot.getDrone();

        if (oldPilot != null) {
            oldPilot.removeDrone();
        }

        if (oldDrone != null) {
            oldDrone.removePilot();
        }

        pilot.addDrone(drone);
        drone.addPilot(pilot);
    }

    public void newCustomer(String account, String firstName, String lastName, String phone, int rating, double credit) {
        Customer newCustomer = new Customer(account, firstName, lastName, phone, rating, credit);
        globalData.putNewCustomer(newCustomer);
    }

    public List<Customer> getAllCustomers() {
        return globalData.getAllCustomers();
    }

    public void newOrder(String storeName, String orderID, String droneID, String customerAccount) {
        Store s = globalData.getStore(storeName);
        if (s.orderAlreadyExists(orderID)) {
            throw new StoreOperationException("order_identifier_already_exists");
        }

        Drone d = s.getDrone(droneID);
        Customer c = globalData.getCustomer(customerAccount);
        Order newOrder = new Order(orderID, d, c);
        s.addOrder(newOrder);
        d.addOrderToDrone(newOrder);
    }

    public List<Order> getAllOrders(String storeName) {
        Store s = globalData.getStore(storeName);
        return s.getAllOrders();
    }

    public boolean isEnoughCredit(Customer c, OrderLine ol, Order o) {
        //from customer get the current credit
        //from order get the current order cost
        //from orderLine get the orderline cost
        //if the order cost + orderline cost <= current credit, return true
        //else return false
        double currentCredit = c.getCredit();
        double currentOrderCost = o.getCurrentCost();
        double orderLineCost = ol.getOrderLineCost();
        if (currentOrderCost + orderLineCost <= currentCredit) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEnoughCapacity(OrderLine ol, Drone d) {
        double orderLineWeight = ol.getOderLineWeight();
        double droneRemainingCapacity = d.remainingCap();

        if (orderLineWeight <= droneRemainingCapacity) {
            return true;
        } else {
            return false;
        }
    }

    public void newOrderLine(String storeName, String orderID, String itemName, int quantity, double unitPrice) {
        Store s = globalData.getStore(storeName);
        Order o = s.getOrderID(orderID);
        Item item = s.getItem(itemName);
        if (o.itemAlreadyExists(itemName)) {
            throw new StoreOperationException("item_already_ordered");
        }
        
        Drone d = o.getDrone();
        Customer c = o.getCustomer();
        OrderLine ol = new OrderLine(item, quantity, unitPrice);
        if (!isEnoughCredit(c, ol, o)) {
            throw new StoreOperationException("customer_cant_afford_new_item");
        }
        if (!isEnoughCapacity(ol, d)) {
            throw new StoreOperationException("drone_cant_carry_new_item");
        } else {
            o.addOrderLine(ol);
        }
    }

    public void purchaseOrder(String storeName, String orderID) {
        Store s = globalData.getStore(storeName);
        Order o = s.getOrderID(orderID);
        Drone d = o.getDrone();
        Customer c = o.getCustomer();
        Pilot p = d.getPilot();
        if (d.getPilot() == null) {
            throw new StoreOperationException("drone_needs_pilot");
        }
        if (d.getTripsLeft() == 0) {
            throw new StoreOperationException("drone_needs_fuel");
        } else {
            double newCredit = c.getCredit() - o.getCurrentCost();
            c.setCredit(newCredit);
            double newRevenue = s.getSettledRevenue() + o.getCurrentCost();
            s.setSettledRevenue(newRevenue);
            int newTripsLeft = d.getTripsLeft() - 1;
            d.setTripsLeft(newTripsLeft);
            int newExp = p.getExp() + 1;
            p.setExp(newExp);
            s.remove(o);
            d.remove(o);
        }

    }

    public void cancelOrder(String storeName, String orderID) {
        Store s = globalData.getStore(storeName);
        Order o = s.getOrderID(orderID);
        Drone d = o.getDrone();
        s.remove(o);
        d.remove(o);
    }
}

