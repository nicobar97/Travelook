package io.travelook.controller.segnalazioni;

import java.util.List;

import io.travelook.model.Segnalazione;

public interface ISegnalazioni {
	boolean segnalaUtente(Segnalazione s);
	List<Segnalazione> getSegnalazioni();
	List<Segnalazione> getSegnalazioniUtente(int idUtente); 
}
