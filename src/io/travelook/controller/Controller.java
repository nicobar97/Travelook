package io.travelook.controller;

import java.io.Writer;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

import io.travelook.model.Entry;

public abstract class Controller implements IController {

	@Override
	public Connection startConnection(String conn) {
		// TODO Auto-generated method stub
		
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

	@Override
	public Connection getDbConnection() {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://travelook.cdxoyr24drny.eu-central-1.rds.amazonaws.com:3306/travelookdb"+
			"?user=travelookdb&password=travelook2019");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			return conn;
		}
	}

}
