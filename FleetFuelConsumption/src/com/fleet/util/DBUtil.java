package com.fleet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	public static Connection getDBConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver"); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user="dhana";
		String pass="admin";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,user,pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	}


