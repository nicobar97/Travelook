package io.travelook.controller.annuncio;

import java.util.List;

import io.travelook.controller.filtro.Filtro;
import io.travelook.model.Viaggio;

public interface IGestioneListaAnnunci {
	public void visualizzaListaAnnunci();
	public List<Viaggio> visualizzaListaAnnunci(Filtro filtro); 
}
