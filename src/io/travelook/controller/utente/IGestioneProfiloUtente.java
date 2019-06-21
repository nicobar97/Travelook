package io.travelook.controller.utente;

import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public interface IGestioneProfiloUtente {
	boolean aggiungiInteressi(Interessi interesse);
	Storico visualizzaStorico();
	List<Recensione> visualizzaRecensioni();
	boolean modificaProfilo(Utente u);
	List<Viaggio> getViaggiInPartecipazione();
	List<Viaggio> getViaggiInAttesaDiConferma();
	boolean eliminaUtente();
}
