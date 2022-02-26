package edu.gatech.cs6310.store;

public class StoreOperationException extends RuntimeException {

    public StoreOperationException(String errorMessage) {
        super(errorMessage);
    }
}
