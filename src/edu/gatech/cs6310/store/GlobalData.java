package edu.gatech.cs6310.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GlobalData {
    private Map<String, Store> allStores = new TreeMap<>();
    private Map<String, Pilot> allPilots = new TreeMap<>();
    private Map<String, Pilot> allLicenseIDs = new TreeMap<>();
    private Map<String, Customer> allCustomers = new TreeMap<>();

    public List<Store> getAllStores() {
        return new ArrayList<>(allStores.values());
    }

    public void putNewStore(Store newStore) {
        if (allStores.containsKey(newStore.getName()))
            throw new StoreOperationException("store_identifier_already_exists");
        allStores.put(newStore.getName(), newStore);
    }

    public Store getStore(String storeName) {
        if (!allStores.containsKey(storeName)) throw new StoreOperationException("store_identifier_does_not_exist");
        return allStores.get(storeName);
    }

    public List<Pilot> getAllPilots() {
        return new ArrayList<>(allPilots.values());
    }

    public void putNewPilot(Pilot newPilot) {
        if (allPilots.containsKey(newPilot.getAccount())) throw new StoreOperationException("pilot_identifier_already_exists");
        if (allLicenseIDs.containsKey(newPilot.getLicenseID())) throw new StoreOperationException("pilot_license_already_exists");
        allPilots.put(newPilot.getAccount(), newPilot);
        allLicenseIDs.put(newPilot.getLicenseID(), newPilot);

    }

    public Pilot getPilot(String pilotAccount) {
        if (!allPilots.containsKey(pilotAccount)) throw new StoreOperationException("pilot_identifier_does_not_exist");
        return allPilots.get(pilotAccount);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(allCustomers.values());
    }

    public Customer getCustomer(String customerAccount) {
        if (!allCustomers.containsKey(customerAccount))
            throw new StoreOperationException("customer_identifier_does_not_exist");
        return allCustomers.get(customerAccount);
    }

    public void putNewCustomer(Customer newCustomer) {
        if (allCustomers.containsKey(newCustomer.getAccount())) throw new StoreOperationException("customer_identifier_already_exists");
        allCustomers.put(newCustomer.getAccount(), newCustomer);
    }
}

