package com.fleet.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fleet.bean.Trip;
import com.fleet.util.DBUtil;
public class TripDAO {
    public int generateTripID() {
        int tripID = 0;
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement( "SELECT trip_seq.NEXTVAL FROM DUAL" );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tripID = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tripID;
    }

    public boolean recordTrip(Trip t) {
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO TRIP_TBL VALUES (?, ?, ?, ?, ?)"
            );

            ps.setInt(1, t.getTripID());
            ps.setString(2, t.getVehicleID());
            ps.setDate(3, new java.sql.Date(t.getTripDate().getTime())); 
            ps.setDouble(4, t.getDistanceTraveled());
            ps.setDouble(5, t.getFuelConsumed());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Trip> findTripsByVehicle(String vehicleID) {
        List<Trip> trips = new ArrayList<>();
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM TRIP_TBL WHERE Vehicle_ID = ?");
            ps.setString(1, vehicleID);
            ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                Trip t = new Trip();
                t.setTripID(rs.getInt(1));
                t.setVehicleID(rs.getString(2));
                t.setTripDate(rs.getDate(3));
                t.setDistanceTraveled(rs.getDouble(4));
                t.setFuelConsumed(rs.getDouble(5));
                trips.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }
}
