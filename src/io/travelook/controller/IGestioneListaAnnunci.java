package io.travelook.controller;

import java.util.List;

import io.travelook.model.Viaggio;

public interface IGestioneListaAnnunci {
	public void visualizzaListaAnnunci();
	public List<Viaggio> visualizzaListaAnnunci(Filtro filtro); 
}
