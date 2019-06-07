package io.travelook.controller;

import java.io.Writer;
import java.nio.file.Path;
import java.sql.Connection;

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
		return null;
	}

}
