package com.fleet.util;

public class FuelDataMismatchException extends Exception {
    public String toString() {
        return "Fuel data mismatch with tank capacity or usage";
    }
}
