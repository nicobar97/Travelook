package io.travelook.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import io.travelook.controller.UtenteController;
import io.travelook.model.Interessi;
import io.travelook.model.Utente;

class UtenteControllerTest {
	UtenteController utenteController = new UtenteController();
	@Test
	void testAggiungiInteressi() {
		Utente utente1 = new Utente(1, "asalvucci", "andrea@gmail.com", "Andrea", "Salvucci", new Date(1997,11,14) ,"C:/");
		Utente utente2 = new Utente(2, "nbartelucci", "nicolo1@gmail.com", "Nicolo", "Bartelucci", new Date(1997,11,14) ,"C:/");
		utenteController.aggiungiInteressi(Interessi.CINEMA,utente1);
		utenteController.aggiungiInteressi(Interessi.ARTE,utente1);
		utenteController.aggiungiInteressi(Interessi.CUCINA,utente2);
		assertFalse(utenteController.aggiungiInteressi(Interessi.CINEMA,utente1));
		assertTrue(utenteController.aggiungiInteressi(Interessi.MUSICA,utente1));
	}

}
