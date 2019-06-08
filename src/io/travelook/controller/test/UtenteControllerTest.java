package io.travelook.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.travelook.controller.UtenteController;
import io.travelook.model.Interessi;

class UtenteControllerTest {
	UtenteController utenteController = new UtenteController(12345);
	@Test
	void testAggiungiInteressi() {
		
		utenteController.aggiungiInteressi(Interessi.CINEMA);
		utenteController.aggiungiInteressi(Interessi.ARTE);
		assertFalse(utenteController.aggiungiInteressi(Interessi.CINEMA));
		assertTrue(utenteController.aggiungiInteressi(Interessi.MUSICA));
	}

}
