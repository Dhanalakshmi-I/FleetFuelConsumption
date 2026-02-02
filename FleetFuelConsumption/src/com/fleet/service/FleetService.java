package com.fleet.service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import com.fleet.bean.FuelExpense;
import com.fleet.bean.Trip;
import com.fleet.bean.Vehicle;
import com.fleet.dao.FuelExpenseDAO;
import com.fleet.dao.TripDAO;
import com.fleet.dao.VehicleDAO;
import com.fleet.util.DBUtil;
import com.fleet.util.FuelDataMismatchException;
import com.fleet.util.ValidationException;
import com.fleet.util.VehicleInUseException;

public class FleetService {

    VehicleDAO vehicleDAO = new VehicleDAO();
    TripDAO tripDAO = new TripDAO();
    FuelExpenseDAO fuelExpenseDAO = new FuelExpenseDAO();
    public Vehicle viewVehicleDetails(String vehicleID) {
        if (vehicleID == null || vehicleID.trim().isEmpty()) {
            return null;
        }
        return vehicleDAO.findVehicle(vehicleID);
    }
    public List<Vehicle> viewAllVehicles() {
        return vehicleDAO.viewAllVehicles();
    }
    public boolean addNewVehicle(Vehicle v) throws ValidationException {
        if (v == null ||
            v.getVehicleID() == null ||
            v.getVehicleID().trim().isEmpty() ||
            v.getTankCapacity() <= 0 ||
            v.getModel() == null ||
            v.getCategory() == null) {

            throw new ValidationException();
        }
        if (vehicleDAO.findVehicle(v.getVehicleID()) != null) {
            return false;
        }

        return vehicleDAO.insertVehicle(v);
    }
    public boolean removeVehicle(String vehicleID)
            throws VehicleInUseException {

        if (vehicleID == null || vehicleID.trim().isEmpty()) {
            return false;
        }
        if (!tripDAO.findTripsByVehicle(vehicleID).isEmpty()
                || !fuelExpenseDAO.findExpensesByVehicle(vehicleID).isEmpty()) {
            throw new VehicleInUseException();
        }

        return vehicleDAO.deleteVehicle(vehicleID);
    }
    public boolean logTrip(String vehicleID, java.util.Date date,double distance, double fuelConsumed)throws ValidationException, FuelDataMismatchException {

        if (vehicleID == null || vehicleID.trim().isEmpty()
                || distance <= 0 || fuelConsumed <= 0) {
            throw new ValidationException();
        }

        Vehicle vehicle = vehicleDAO.findVehicle(vehicleID);
        if (vehicle == null) {
            return false;
        }
        if (fuelConsumed > vehicle.getTankCapacity()) {
            throw new FuelDataMismatchException();
        }

        Connection connection = null;
        try {
            connection = DBUtil.getDBConnection();
            connection.setAutoCommit(false); 
            Trip t = new Trip();
            t.setTripID(tripDAO.generateTripID());
            t.setVehicleID(vehicleID);
            t.setTripDate(date);
            t.setDistanceTraveled(distance);
            t.setFuelConsumed(fuelConsumed);

            boolean tripInserted = tripDAO.recordTrip(t);
            boolean mileageUpdated =
                    vehicleDAO.updateMileage(vehicleID, distance, fuelConsumed);

            if (tripInserted && mileageUpdated) {
                connection.commit(); 
                return true;
            } else {
                connection.rollback(); 
            }

        } catch (Exception e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }
    public boolean recordFuelPurchase(String vehicleID,double fuelVolume, double cost,java.util.Date date,String stationName)
            throws ValidationException {

        if (fuelVolume <= 0 || cost <= 0) {
            throw new ValidationException();
        }

        Vehicle vehicle = vehicleDAO.findVehicle(vehicleID);
        if (vehicle == null) {
            return false;
        }

        if (fuelVolume > vehicle.getTankCapacity()) {
            throw new ValidationException();
        }

        Connection connection = null;
        try {
            connection = DBUtil.getDBConnection();
            connection.setAutoCommit(false); 

            FuelExpense fe = new FuelExpense();
            fe.setExpenseID(fuelExpenseDAO.generateExpenseID());
            fe.setVehicleID(vehicleID);
            fe.setFuelVolume(fuelVolume);
            fe.setCost(cost);
            fe.setPurchaseDate(date);
            fe.setStationName(stationName);

            boolean expenseInserted =
                    fuelExpenseDAO.recordFuelExpense(fe);

            boolean fuelUpdated =
                    vehicleDAO.updateMileage(vehicleID, 0, fuelVolume);

            if (expenseInserted && fuelUpdated) {
                connection.commit();
                return true;
            } else {
                connection.rollback(); 
            }

        } catch (Exception e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }
    public double calculateFuelEfficiency(String vehicleID) {
        Vehicle vehicle = vehicleDAO.findVehicle(vehicleID);
        if (vehicle == null || vehicle.getTotalFuelUsed() == 0) {
            return 0;
        }
        return vehicle.getTotalDistance() / vehicle.getTotalFuelUsed();
    }
}
