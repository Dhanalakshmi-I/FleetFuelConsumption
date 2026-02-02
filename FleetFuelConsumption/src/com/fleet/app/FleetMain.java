package com.fleet.app;

import java.util.Date;
import java.util.Scanner;

import com.fleet.service.FleetService;

public class FleetMain {
	private static FleetService fleetService; 
	 
    public static void main(String[] args) {        
    	fleetService = new FleetService(); 
        Scanner sc = new Scanner(System.in); 
 
        System.out.println("--- Fleet Management Console ---"); 
 
        try { 
            boolean r = fleetService.logTrip("VH1001", new Date(), 150.0, 8.0); 
            System.out.println(r ? "TRIP LOGGED" : "FAILED"); 
        } catch(Exception e) { System.out.println(e); } 
 
        try { 
            boolean r = fleetService.recordFuelPurchase("VH1001", 
30.0, 3000.0, new Date(), "HP Pump - Pune"); 
            System.out.println(r ? "PURCHASE RECORDED" : "FAILED"); 
        } catch(Exception e) { System.out.println(e); } 
 
        double eff = fleetService.calculateFuelEfficiency("VH1001"); 
        System.out.println("Fuel Efficiency: " + eff + " km/l"); 
 
        sc.close(); 
    } 
} 


