package com.fleet.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fleet.bean.FuelExpense;
import com.fleet.util.DBUtil;
public class FuelExpenseDAO {
    public int generateExpenseID() {
        int expenseID=0;
        try {
            Connection connection=DBUtil.getDBConnection();
            PreparedStatement ps=connection.prepareStatement("Select fuel_expense_seq.NEXTVAL FROM DUAL");
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                expenseID = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenseID;
    }
    public boolean recordFuelExpense(FuelExpense fe) {
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO FUEL_EXPENSE_TBL VALUES (?, ?, ?, ?, ?, ?)"
            );

            ps.setInt(1, fe.getExpenseID());
            ps.setString(2, fe.getVehicleID());
            ps.setDouble(3, fe.getFuelVolume());
            ps.setDouble(4, fe.getCost());
            ps.setDate(5, new java.sql.Date(fe.getPurchaseDate().getTime()));    
            ps.setString(6, fe.getStationName());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FuelExpense> findExpensesByVehicle(String vehicleID) {
        List<FuelExpense> expenses = new ArrayList<>();
        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement( "SELECT * FROM FUEL_EXPENSE_TBL WHERE Vehicle_ID = ?");
            ps.setString(1, vehicleID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FuelExpense fe = new FuelExpense();
                ps.setInt(1, fe.getExpenseID());
                ps.setString(2, fe.getVehicleID());
                ps.setDouble(3, fe.getFuelVolume());
                ps.setDouble(4,fe.getCost());
                ps.setDate(5, (Date) fe.getPurchaseDate());
                ps.setString(6, fe.getStationName());
                expenses.add(fe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }
}
