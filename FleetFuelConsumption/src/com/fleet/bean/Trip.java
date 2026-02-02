package com.fleet.bean;

import java.util.Date;

public class Trip {
   private int tripID;
   private String vehicleID;
   private Date tripDate;
   private double distanceTraveled;
   private double fuelConsumed;
public int getTripID() {
	return tripID;
}
public void setTripID(int tripID) {
	this.tripID = tripID;
}
public String getVehicleID() {
	return vehicleID;
}
public void setVehicleID(String vehicleID) {
	this.vehicleID = vehicleID;
}
public Date getTripDate() {
	return tripDate;
}
public void setTripDate(Date tripDate) {
	this.tripDate = tripDate;
}
public double getDistanceTraveled() {
	return distanceTraveled;
}
public void setDistanceTraveled(double distanceTraveled) {
	this.distanceTraveled = distanceTraveled;
}
public double getFuelConsumed() {
	return fuelConsumed;
}
public void setFuelConsumed(double fuelConsumed) {
	this.fuelConsumed = fuelConsumed;
}
   
}
