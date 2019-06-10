package io.travelook.controller;

import java.io.Writer;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

import io.travelook.model.Entry;

public abstract class Controller implements IController {
//PORCODIO
	private Connection connessione;
	public String JDBC_URL = "jdbc:sqlserver://travelook.database.windows.net:1433;database=travelook;"
			+ "user=travelook@travelook;password=travel_2019;encrypt=true;trustServerCertificate=false;"
			+ "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	@Override
	public Connection startConnection() {
	        try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            connessione = DriverManager.getConnection(JDBC_URL);
	            if(connessione != null) {
	                DatabaseMetaData metaObj = (DatabaseMetaData) connessione.getMetaData();
	                System.out.println("Driver Name?= " + metaObj.getDriverName() + ", Driver Version?= " + metaObj.getDriverVersion() + ", Product Name?= " + metaObj.getDatabaseProductName() + ", Product Version?= " + metaObj.getDatabaseProductVersion());
	            }
	        } 
	        catch(Exception sqlException) {
	            sqlException.printStackTrace();
	    }
			return connessione;
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

	@Override
	public Connection getDbConnection() {
		return connessione;
	}
		

}
