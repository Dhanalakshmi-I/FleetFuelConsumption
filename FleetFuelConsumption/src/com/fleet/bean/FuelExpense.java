package com.fleet.bean;

import java.util.Date;

public class FuelExpense {
	private int expenseID ;
	private String vehicleID ;
	private double fuelVolume ;
	private double cost ;
	private Date purchaseDate ;
	private String stationName;
	public int getExpenseID() {
		return expenseID;
	}
	public void setExpenseID(int expenseID) {
		this.expenseID = expenseID;
	}
	public String getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	public double getFuelVolume() {
		return fuelVolume;
	}
	public void setFuelVolume(double fuelVolume) {
		this.fuelVolume = fuelVolume;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	} 
	


}
