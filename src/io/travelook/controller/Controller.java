package io.travelook.controller;

import java.io.Writer;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.travelook.model.Entry;
import io.travelook.utils.ConnectionPool;

public abstract class Controller implements IController {

	//private Connection connessione = null;
	private DataSource dataSource =  null;
	private ConnectionPool jdbcObj = null;
	
	public Controller() {
			jdbcObj = new ConnectionPool();
	        try {
	        	dataSource = jdbcObj.setUpPool();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
  
	@Override
	public Connection getDbConnection() {
		
		jdbcObj.printDbStatus();
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("NON POSSO PRENDEre unA CoNN DAL POOL");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Writer openWriterLog(Path logPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void scriviOperazioneLog(Entry entryLog) {
		// TODO Auto-generated method stub

	}

}


/*try {
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
if(connessione != null && connessione.isValid(0))
	return connessione;
connessione = DriverManager.getConnection(JDBC_URL);
if(connessione != null) {
    DatabaseMetaData metaObj = (DatabaseMetaData) connessione.getMetaData();
    System.out.println("Driver Name?= " + metaObj.getDriverName() + ", Driver Version?= " + metaObj.getDriverVersion() + ", Product Name?= " + metaObj.getDatabaseProductName() + ", Product Version?= " + metaObj.getDatabaseProductVersion());
}
} 
catch(Exception sqlException) {
sqlException.printStackTrace();
}
return connessione;*/