package io.travelook.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.travelook.controller.Controller;
import io.travelook.model.Utente;

public class MssqlRegistrazioneDAO extends Controller implements RegistrazioneDAO {
	
	private Connection dbCredenziali;
	private Connection db;
	
	
	public MssqlRegistrazioneDAO(Connection c) {
		this.db=c;
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
		
		db=c;
	}
	@Override
	public boolean create(String username, String hash) {
		String query1 = "INSERT INTO Credenziali(Username, Hash) VALUES(?,?)";
		
		try {
			PreparedStatement prep_stmt = dbCredenziali.prepareStatement(query1);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, username);
			prep_stmt.setString(2, hash);
			int esito = prep_stmt.executeUpdate();
			prep_stmt.close();
			if(esito>0) return true;
			else return false;
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}
	@Override
	public Utente read(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean update(Utente u) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Utente u) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
