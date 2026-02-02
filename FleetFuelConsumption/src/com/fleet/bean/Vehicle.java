package com.fleet.bean;

public class Vehicle {
	private String vehicleID;
	private String model;
	private String category;
	private double tankCapacity;
	private double totalDistance;
	private double totalFuelUsed;
	public String getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getTankCapacity() {
		return tankCapacity;
	}
	public void setTankCapacity(double tankCapacity) {
		this.tankCapacity = tankCapacity;
	}
	public double getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
	public double getTotalFuelUsed() {
		return totalFuelUsed;
	}
	public void setTotalFuelUsed(double totalFuelUsed) {
		this.totalFuelUsed = totalFuelUsed;
	}
	
	

}
