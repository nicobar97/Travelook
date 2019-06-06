package io.travelook.controller;

import java.io.Writer;
import java.nio.file.Path;
import java.sql.Connection;
import io.travelook.model.Entry;

public interface IController {
	public Connection startConnection(String conn);
	public Writer openWriterLog(Path logPath);
	public void scriviOperazioneLog(Entry entryLog);
	public Connection getDbConnection();
}
