package io.travelook.controller.annuncio;

import io.travelook.model.Recensione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public interface IAnnuncio {
	public Utente[] visuallizzaUtentiPartecipanti(Integer idAnnuncio);
	public boolean modificaAnnuncio(Viaggio modificaato);
	public boolean lasciaRecensione(Recensione newRecensione);
	public boolean abbandonaAnnuncio(Utente utente);
}
