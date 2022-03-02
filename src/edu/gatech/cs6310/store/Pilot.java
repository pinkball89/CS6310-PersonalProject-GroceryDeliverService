package edu.gatech.cs6310.store;

public class Pilot extends Employee {
    private String licenseID;
    private String account;
    private int successfulDeliveries;
    private Drone currentDrone;

    public Pilot(String account, String firstName, String lastName, String phone, String taxID, String licenseID, int successfulDeliveries) {
        super(account, firstName, lastName, phone, taxID, 0, 0);
        this.licenseID = licenseID;
        this.account = account;
        this.successfulDeliveries = successfulDeliveries;
    }

    public String getPhone() {
        return phone;
    }

    public String getTaxID() {
        return taxID;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public int getExp() {
        return successfulDeliveries;
    }

    public String toString() {
        return "name:" + getFirstName() + "_" + getLastName() + ",phone:" +
                getPhone() + ",taxID:" + getTaxID() + ",licenseID:" + getLicenseID() + ",experience:" + getExp();
    }

    public void addDrone(Drone drone) {
        currentDrone = drone;
    }

    public void removeDrone() {
        currentDrone = null;
    }

    public Drone getDrone() {
        return currentDrone;
    }

    public void setExp(int newExp) {
        this.successfulDeliveries = newExp;
    }
}
