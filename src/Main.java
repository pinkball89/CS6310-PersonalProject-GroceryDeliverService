import edu.gatech.cs6310.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Grocery Express Delivery Service!");
        DeliveryService simulator = new DeliveryService();
        simulator.commandLoop();
    }
}
