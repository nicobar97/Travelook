package io.travelook.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import io.travelook.controller.logger.Logger;
import io.travelook.model.Entry;
import io.travelook.utils.ConnectionPool;

public abstract class Controller implements IController {

	//private Connection connessione = null;
	private DataSource dataSource =  null;
	private ConnectionPool jdbcObj = null;
	private Logger log=null;
	private int logcount=0;
	
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
	public Writer openWriterLog(String logPath) {
		this.log=new Logger(logPath);
		FileWriter fw=null;
		try {
			 fw=new FileWriter(log.getFile(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fw;
	}

	@Override
	public void scriviOperazioneLog(Entry entryLog) {
		log.scriviEntry(entryLog);
	}
    
	public int getLogcount() {
		List<Entry> logs=new ArrayList<Entry>();
		logs=this.log.visualizzaLog();
		int lastarg=logs.size();
		if(lastarg>0) {
		Entry last=logs.get(lastarg-1);
	    this.logcount=last.getIdEntry();}
		return logcount;
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