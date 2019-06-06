package io.travelook.model;

import java.util.List;

public class Logs { //DA RIMUOVERE
	private List<Entry> logs;

	public Logs(List<Entry> logs) {
		this.logs = logs;
	}

	public List<Entry> getLogs() {
		return logs;
	}

	public void setLogs(List<Entry> logs) {
		this.logs = logs;
	}

}
