package com.fleet.util;

public class VehicleInUseException extends Exception {
    public String toString() {
        return "Vehicle cannot be removed: trips or fuel records exist";
    }
}
