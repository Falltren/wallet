package com.fallt.wallet.exception;

public class NotHaveEnoughFunds extends RuntimeException {

    public NotHaveEnoughFunds(String message) {
        super(message);
    }

}
