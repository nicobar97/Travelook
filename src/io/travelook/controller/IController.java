package io.travelook.controller;

import java.io.Writer;
import java.nio.file.Path;
import java.sql.Connection;
import io.travelook.model.Entry;

public interface IController {
	public Writer openWriterLog(String logPath);
	public void scriviOperazioneLog(Entry entryLog);
	public Connection getDbConnection();
}
