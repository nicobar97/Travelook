package io.travelook.controller;

import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public interface IGestioneProfiloUtente {
	boolean aggiungiInteressi(Interessi interesse);
	Utente visualizzaProfilo();
	Storico visualizzaStorico();
	List<Recensione> visualizzaRecensioni();
	void modificaProfilo();
	List<Viaggio> getViaggiInPartecipazione();
	List<Viaggio> getViaggiInAttesaDiConferma();
	boolean eliminaUtente(Utente utente);
}
