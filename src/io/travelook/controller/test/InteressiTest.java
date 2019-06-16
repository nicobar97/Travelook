package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Interessi;
import io.travelook.model.Utente;
import io.travelook.persistence.MssqlUtenteDAO;
import io.travelook.persistence.MssqlUtente_InteressiDAO;

class InteressiTest {
//
	@Test
	void testAggiungi() {
		UtenteController uc = new UtenteController();
		MssqlUtenteDAO ud = new MssqlUtenteDAO(uc.getDbConnection());
		Utente u = ud.read(1);
		MssqlUtente_InteressiDAO intere = new MssqlUtente_InteressiDAO(uc.getDbConnection());
		assertTrue(intere.create(u, Interessi.ARTE));
		
	}

}
