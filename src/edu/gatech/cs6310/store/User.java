package edu.gatech.cs6310.store;

public class User {
    protected String account;
    protected String firstName;
    protected String lastName;
    protected String phone;

    public User(String account, String firstName, String lastName, String phone) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccount() {
        return account;
    }

    public String getPhone() { return phone; }

}
