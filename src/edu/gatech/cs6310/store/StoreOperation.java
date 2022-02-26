package edu.gatech.cs6310.store;

import java.util.*;

public class StoreOperation {
    Map<String, Store> allStores = new HashMap<>();
    Map<String, Pilot> allPilots = new HashMap<>();
    Map<String, Pilot> allLicenseIDs = new HashMap<>();
    Map<String, Customer> allCustomers = new HashMap<>();

    public void newStore(String name, double revenue) {
        Store newStore = new Store(name, revenue);
        if (allStores.containsKey(name)) throw new StoreOperationException("store_identifier_already_exists");

        allStores.put(newStore.getName(), newStore);
    }

    public List<Store> getAllStores() {
        List<Store> result = new ArrayList<>();
        allStores.forEach((k, v) -> result.add(v));
        result.sort(Comparator.comparing(Store::getName));
        return result;
    }

    private Store getStore(String storeName) {
        if (!allStores.containsKey(storeName)) throw new StoreOperationException("store_identifier_does_not_exist");
        return allStores.get(storeName);
    }

    public void newItem(String storeName, String itemName, double itemWeight) {
        Store s = getStore(storeName);
        Item newItem = new Item(itemName, itemWeight);
        s.addItem(newItem);
    }

    public List<Item> getAllItems(String storeName) {
        Store s = getStore(storeName);
        return s.getAllItems();
    }

    public Pilot newPilot(String account, String firstName, String lastName, String phone, String taxID, String licenseID, int successfulDeliveries) {
        Pilot newPilot = new Pilot(account, firstName, lastName, phone, taxID, licenseID, successfulDeliveries);
        if (allPilots.containsKey(account)) throw new StoreOperationException("pilot_identifier_already_exists");
        if (allLicenseIDs.containsKey(licenseID)) throw new StoreOperationException("pilot_license_already_exists");
        allPilots.put(newPilot.getAccount(), newPilot);
        allLicenseIDs.put(newPilot.getLicenseID(), newPilot);
        return newPilot;
    }

    public List<Pilot> getAllPilots() {
        List<Pilot> result = new ArrayList<>();
        allPilots.forEach((k, v) -> result.add(v));
        result.sort(Comparator.comparing(Pilot::getAccount));
        return result;
    }

    private Pilot getPilot(String pilotAccount) {
        if (!allPilots.containsKey(pilotAccount)) throw new StoreOperationException("pilot_identifier_does_not_exist");
        return allPilots.get(pilotAccount);
    }

    public void newDrone(String storeName, String droneID, double capacity, int tripsLeft) {
        Store s = getStore(storeName);
        Drone newDrone = new Drone(droneID, capacity, tripsLeft);
        s.addDrone(newDrone);
    }

    public List<Drone> getAllDrones(String storeName) {
        Store s = getStore(storeName);
        return s.getAllDrones();

    }

    public void flyDrone(String storeName, String droneID, String pilotAccount) {
        Store s = getStore(storeName);
        Drone drone = s.getDrone(droneID);
        Pilot pilot = getPilot(pilotAccount);

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

    public Customer newCustomer(String account, String firstName, String lastName, String phone, int rating, double credit) {
        Customer newCustomer = new Customer(account, firstName, lastName, phone, rating, credit);
        if (allCustomers.containsKey(account)) throw new StoreOperationException("customer_identifier_already_exists");
        allCustomers.put(newCustomer.getAccount(), newCustomer);
        return newCustomer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<>();
        allCustomers.forEach((k, v) -> result.add(v));
        result.sort(Comparator.comparing(Customer::getAccount));
        return result;
    }

    private Customer getCustomer(String customerAccount) {
        if (!allCustomers.containsKey(customerAccount))
            throw new StoreOperationException("customer_identifier_does_not_exist");
        return allCustomers.get(customerAccount);
    }

    public void newOrder(String storeName, String orderID, String droneID, String customerAccount) {
        Store s = getStore(storeName);
        Drone d = s.getDrone(droneID);
        Customer c = getCustomer(customerAccount);
        Order newOrder = new Order(orderID, d, c);
        s.addOrder(newOrder);
    }

    public List<Order> getAllOrders(String storeName) {
        Store s = getStore(storeName);
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

    public boolean isEnoughCapacity(OrderLine ol, Order o, Drone d) {
        double currentOrderWeight = o.getCurrentOderWeight();
        double orderLineWeight = ol.getOderLineWeight();
        double droneCapacity = d.getCapacity();

        if (currentOrderWeight + orderLineWeight <= droneCapacity) {
            return true;
        } else {
            return false;
        }
    }

    public void newOrderLine(String storeName, String orderID, String itemName, int quantity, double unitPrice) {
        Store s = getStore(storeName);
        Order o = s.getOrderID(orderID);
        Drone d = o.getDrone();
        Customer c = o.getCustomer();
        Item item = s.getItem(itemName);
        OrderLine ol = new OrderLine(item, quantity, unitPrice);
        if (!isEnoughCredit(c, ol, o)) {
            throw new StoreOperationException("customer_cant_afford_new_item");
        }
        if (!isEnoughCapacity(ol, o, d)) {
            throw new StoreOperationException("drone_cant_carry_new_item");
        } else {
            o.addOrderLine(ol);
        }
    }

    public void newDelivery(String storeName, String orderID) {
        Store s = getStore(storeName);
        Order o = s.getOrderID(orderID);
        Drone d = o.getDrone();
        Customer c = o.getCustomer();
        Pilot p = d.getPilot();
        if (d.getPilot() == null) {
            throw new StoreOperationException("drone_needs_pilot");
        }
        if (d.getTripsLeft() == 0) {
            throw new StoreOperationException("drone_needs_fuel");
        }
        else {
            double newCredit = c.getCredit() - o.getCurrentCost();
            c.setCredit(newCredit);
            double newRevenue = s.getSettledRevenue() + o.getCurrentCost();
            s.setSettledRevenue(newRevenue);
            int newTripsLeft = d.getTripsLeft() - 1;
            d.setTripsLeft(newTripsLeft);
            int newExp = p.getExp()  + 1;
            p.setExp(newExp);
            s.remove(o);
            d.remove(o);
        }

    }

    public void cancelOrder(String storeName, String orderID) {
        Store s = getStore(storeName);
        Order o = s.getOrderID(orderID);
        s.remove(o);
    }
}

