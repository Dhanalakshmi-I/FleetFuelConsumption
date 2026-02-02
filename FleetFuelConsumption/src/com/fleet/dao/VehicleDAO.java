package com.fleet.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fleet.bean.Vehicle;
import com.fleet.util.DBUtil;

public class VehicleDAO {
      public Vehicle findVehicle(String vehicleID) {
        Vehicle v = null;
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM VEHICLE_TBL WHERE VEHICLE_ID=?");
                ps.setString(1, vehicleID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                v = new Vehicle();
                v.setVehicleID(rs.getString(1));
                v.setModel(rs.getString(2));
                v.setCategory(rs.getString(3));
                v.setTankCapacity(rs.getDouble(4));
                v.setTotalDistance(rs.getDouble(5));
                v.setTotalFuelUsed(rs.getDouble(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
   public List<Vehicle> viewAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM VEHICLE_TBL");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Vehicle v = new Vehicle();
                v.setVehicleID(rs.getString(1));
                v.setModel(rs.getString(2));
                v.setCategory(rs.getString(3));
                v.setTankCapacity(rs.getDouble(4));
                v.setTotalDistance(rs.getDouble(5));
                v.setTotalFuelUsed(rs.getDouble(6));
                vehicles.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    public boolean insertVehicle(Vehicle v) {
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO VEHICLE_TBL VALUES (?, ?, ?, ?, ?, ?)" );
            ps.setString(1, v.getVehicleID());
            ps.setString(2, v.getModel());
            ps.setString(3, v.getCategory());
            ps.setDouble(4, v.getTankCapacity());
            ps.setDouble(5, v.getTotalDistance());
            ps.setDouble(6, v.getTotalFuelUsed());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMileage(String vehicleID, double newDistance, double newFuel) {
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE VEHICLE_TBL SET Total_Distance = Total_Distance + ?, Total_Fuel_Used = Total_Fuel_Used + ? WHERE Vehicle_ID = ?");
            ps.setDouble(1, newDistance);
            ps.setDouble(2, newFuel);
            ps.setString(3, vehicleID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteVehicle(String vehicleID) {
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement psCheckTrips = connection.prepareStatement("SELECT 1 FROM TRIP_TBL WHERE Vehicle_ID = ?");
            psCheckTrips.setString(1, vehicleID);
            ResultSet rsTrips = psCheckTrips.executeQuery();
            if(rsTrips.next()) 
            	return false;
            
            PreparedStatement psCheckExpenses = connection.prepareStatement("SELECT 1 FROM EXPENSE_TBL WHERE Vehicle_ID = ?");
            psCheckExpenses.setString(1, vehicleID);
            ResultSet rsExpenses = psCheckExpenses.executeQuery();
            if(rsExpenses.next()) 
            	return false; 
            
            PreparedStatement psDelete = connection.prepareStatement( "DELETE FROM VEHICLE_TBL WHERE Vehicle_ID = ?" );
            psDelete.setString(1, vehicleID);
            int rows = psDelete.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
