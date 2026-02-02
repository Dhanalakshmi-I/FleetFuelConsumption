package com.fleet.util;

public class ValidationException extends Exception {
    public String toString() {
        return "Validation failed: Invalid input values";
    }
}
