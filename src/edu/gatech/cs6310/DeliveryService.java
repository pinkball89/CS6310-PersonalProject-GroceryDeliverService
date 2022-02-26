package edu.gatech.cs6310;

import edu.gatech.cs6310.store.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

public class DeliveryService {
    public static NumberFormat formatter = new DecimalFormat("#0");

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        StoreOperation storeOperation = new StoreOperation();

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);
                if (wholeInputLine.startsWith("//")) continue;

                if (tokens[0].equals("make_store")) {
                    String storeName = tokens[1];
                    String revenue = tokens[2];
                    //Call store operation service to create a store
                    storeOperation.newStore(storeName, Double.parseDouble(revenue));

                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("display_stores")) {
                    List<Store> allStores = storeOperation.getAllStores();
                    allStores.forEach(s -> System.out.println(s.toString()));
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("sell_item")) {
                    String storeName = tokens[1];
                    String itemName = tokens[2];
                    String itemWeight = tokens[3];
                    storeOperation.newItem(storeName, itemName, Double.parseDouble(itemWeight));
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("display_items")) {
                    String storeName = tokens[1];
                    List<Item> allItems = storeOperation.getAllItems(storeName);
                    allItems.forEach(item -> System.out.println(item.toString()));
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("make_pilot")) {
                    String account = tokens[1];
                    String firstName = tokens[2];
                    String lastName = tokens[3];
                    String phone = tokens[4];
                    String taxID = tokens[5];
                    String licenseID = tokens[6];
                    int successfulDeliveries = Integer.parseInt(tokens[7]);
                    storeOperation.newPilot(account, firstName, lastName, phone, taxID, licenseID, successfulDeliveries);
                    System.out.println("OK:change_completed");


                } else if (tokens[0].equals("display_pilots")) {
                    List<Pilot> allPilots = storeOperation.getAllPilots();
                    allPilots.forEach(pilot -> System.out.println(pilot.toString()));
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("make_drone")) {
                    String storeName = tokens[1];
                    String droneID = tokens[2];
                    double capacity = Double.parseDouble(tokens[3]);
                    int tripsLeft = Integer.parseInt(tokens[4]);
                    storeOperation.newDrone(storeName, droneID, capacity, tripsLeft);
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("display_drones")) {
                    String storeName = tokens[1];
                    List<Drone> allDrones = storeOperation.getAllDrones(storeName);
                    allDrones.forEach(drone -> System.out.println(drone.toString()));
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("fly_drone")) {
                    String storeName = tokens[1];
                    String droneID = tokens[2];
                    String account = tokens[3];
                    storeOperation.flyDrone(storeName, droneID, account);
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("make_customer")) {
                    String account = tokens[1];
                    String firstName = tokens[2];
                    String lastName = tokens[3];
                    String phone = tokens[4];
                    int rating = Integer.parseInt(tokens[5]);
                    double credit = Double.parseDouble(tokens[6]);
                    storeOperation.newCustomer(account, firstName, lastName, phone, rating, credit);
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("display_customers")) {
                    List<Customer> allCustomers = storeOperation.getAllCustomers();
                    allCustomers.forEach(customer -> System.out.println(customer.toString()));
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("start_order")) {
                    String storeName = tokens[1];
                    String orderStub = tokens[2];
                    String droneID = tokens[3];
                    String customerAccount = tokens[4];
                    storeOperation.newOrder(storeName, orderStub, droneID, customerAccount);
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("display_orders")) {
                    String storeName = tokens[1];
                    List<Order> allOrders = storeOperation.getAllOrders(storeName);
                    allOrders.forEach(order -> System.out.println(order.toString()));
                    System.out.println("OK:display_completed");

                } else if (tokens[0].equals("request_item")) {
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    String itemName = tokens[3];
                    int quantity = Integer.parseInt(tokens[4]);
                    double unitPrice = Double.parseDouble(tokens[5]);
                    storeOperation.newOrderLine(storeName, orderID, itemName, quantity, unitPrice);
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("purchase_order")) {
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    storeOperation.newDelivery(storeName, orderID);
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("cancel_order")) {
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    storeOperation.cancelOrder(storeName, orderID);
                    System.out.println("OK:change_completed");

                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (StoreOperationException e) {
                System.out.println("ERROR:" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
