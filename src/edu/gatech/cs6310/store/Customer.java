package edu.gatech.cs6310.store;

import static edu.gatech.cs6310.DeliveryService.formatter;

public class Customer extends User {
    private int rating;
    private double credit;
    private String address;

    public Customer(String account, String firstName, String lastName, String phone, int rating, double credit){
        super(account, firstName, lastName, phone);
        this.rating = rating;
        this.credit = credit;
    }

    public int getRating() {
        return rating;
    }

    public double getCredit() {
        return credit;
    }

    public String toString() {
        return "name:" + getFirstName() + "_" + getLastName() + ",phone:" +
                getPhone() + ",rating:" + getRating() + ",credit:" + formatter.format(getCredit());
    }

    public void setCredit(double newCredit) {
        this.credit = newCredit;
    }
}
