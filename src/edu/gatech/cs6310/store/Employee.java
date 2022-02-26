package edu.gatech.cs6310.store;

public class Employee extends User {
    protected String taxID;
    protected int monthEmployed;
    protected int annualSalary;

    public Employee(String account, String firstName, String lastName, String phone, String taxID, int annualSalary) {
        super(account, firstName, lastName, phone);
        this.taxID = taxID;
        this.annualSalary = annualSalary;
    }
}
