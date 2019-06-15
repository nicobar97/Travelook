package io.travelook.controller.segnalazioni;

import io.travelook.model.Segnalazione;

public interface ISegnalazioni {
	void segnalaUtente(Segnalazione s);
	Segnalazione[] getSegnalazioni();
	Segnalazione[] getSegnalazioniUtente(int idUtente);
}
