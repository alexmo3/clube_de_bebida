package com.clubedebebida.backend.controller.exception;

public class ControllerInsufficientBalanceException extends RuntimeException {
    public ControllerInsufficientBalanceException(String message) {
        super(message);
    }
}
