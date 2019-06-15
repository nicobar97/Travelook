package io.travelook.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.travelook.controller.Controller;
import io.travelook.model.Utente;

public class MssqlLoginDAO extends Controller implements LoginDAO {
	
	private Connection dbCredenziali;
	private Connection db;
	private final String JDBC_URL = "jdbc:sqlserver://travelook.database.windows.net:1433;database=tl_cred;"
			+ "user=travelook@travelook;password=travel_2019;encrypt=true;trustServerCertificate=false;"
			+ "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	public static String insert = "INSERT INTO Credenziali(Username, Hash) VALUES(?,?)";
	public static String read_hash = "select Hash from Credenziali where username = '?'";
	public MssqlLoginDAO(Connection c) {
		this.db=c;
		dbCredenziali = getDbConnection();
	}
	@Override
	public boolean create(String username, String hash) {
		
		try {
			PreparedStatement prep_stmt = dbCredenziali.prepareStatement(MssqlLoginDAO.insert);
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
		}finally {
			try {
				dbCredenziali.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public String read(String username) {
		String result = null;
		if ( username.trim().equals("") )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		try {
			PreparedStatement prep_stmt = dbCredenziali.prepareStatement(MssqlLoginDAO.read_hash);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, username);
			/*
			select id,nickname,email,nome,cognome,dataNascita,"
			+ "imgProfilo from Utente where id=? 
			 */
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				result = rs.getString(1);
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("read(): failed to retrieve entry with id = " + username+": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				dbCredenziali.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
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
	public Connection getDbConnection() {
		try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if(dbCredenziali != null && dbCredenziali.isValid(0))
            	return dbCredenziali;
            dbCredenziali = DriverManager.getConnection(JDBC_URL);
            if(dbCredenziali != null) {
                DatabaseMetaData metaObj = (DatabaseMetaData) dbCredenziali.getMetaData();
                System.out.println("Driver Name?= " + metaObj.getDriverName() + ", Driver Version?= " + metaObj.getDriverVersion() + ", Product Name?= " + metaObj.getDatabaseProductName() + ", Product Version?= " + metaObj.getDatabaseProductVersion());
            }
        } 
        catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
		return dbCredenziali;
	}

}