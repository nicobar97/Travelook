package io.travelook.controller.logger;

import java.util.List;

import io.travelook.controller.filtro.Filtro;
import io.travelook.model.Entry;

public interface ILogger {
	public void scriviEntry(Entry e);
	public List<Entry> visualizzaLog(Filtro filtro);
	public List<Entry> visualizzaLog();
}
