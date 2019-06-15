package io.travelook.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MssqlLoginDAO {

	private Connection dbCredenziali;
	
	public MssqlLoginDAO() {
		String JDBC_URL = "jdbc:sqlserver://travelook.database.windows.net:1433;database=tl_cred;"
				+ "user=travelook@travelook;password=travel_2019;encrypt=true;trustServerCertificate=false;"
				+ "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dbCredenziali = DriverManager.getConnection(JDBC_URL);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/***
	 * 
	 * @param username
	 * @return l'hash reperito dal db o null
	 */
	public String read(String username) {
		String query = "SELECT hash FROM Credenziali WHERE Username=?";
		PreparedStatement prep_stmt;
		try {
			prep_stmt = dbCredenziali.prepareStatement(query);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, username);

			ResultSet rs = prep_stmt.executeQuery();
			if(rs.next()) {
				return rs.getString("Hash");
				
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
