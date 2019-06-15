package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlViaggioDAO;

class ViaggioTest {

	ListaAnnunciController lac = new ListaAnnunciController();
	/*@Test
	void testUpdateViaggio() {
		//Creo un viaggio con id uguale a uno che c'è già nel db e controllo se me lo aggiorna
		Utente u = new Utente(1, "nicobar", "nicobar97@gmail.com", "nicolo", "bartelucci",new Date(System.nanoTime()), "immagine1");
		Viaggio aggiornato = new Viaggio(2, u ,"TITOLO AGGIORNATO", "Montecosaro", "Dialetto Maceratese", 2, "se magna",
				"Macerata", new Date(2019,11,14), new Date(2019,11,17), Stato.INIZIO);
		MssqlViaggioDAO vd = new MssqlViaggioDAO(lac.getDbConnection());
		assertTrue(vd.update(aggiornato));
	}*/
	
	@Test
	void testAbbandonaAnnuncio() {
		Utente u = new Utente(1, "nicobar", "nicobar97@gmail.com", "nicolo", "bartelucci",new Date(System.nanoTime()), "immagine1");
		Viaggio v = new Viaggio(2, u ,"TITOLO AGGIORNATO", "Montecosaro", "Dialetto Maceratese", 2, "se magna",
				"Macerata", new Date(2019,11,14), new Date(2019,11,17), Stato.INIZIO);
		MssqlViaggioDAO db = new MssqlViaggioDAO(lac.getDbConnection());
		assertTrue(db.utenteAbbandonaAnnuncio(u, v));
	}
	
	

}
