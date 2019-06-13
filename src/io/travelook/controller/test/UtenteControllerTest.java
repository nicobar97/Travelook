package io.travelook.controller.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.Date;

import org.junit.jupiter.api.Test;

import io.travelook.controller.RegistrazioneController;
import io.travelook.controller.UtenteController;
import io.travelook.model.Interessi;
import io.travelook.model.Utente;
import io.travelook.persistence.MssqlUtenteDAO;

class UtenteControllerTest {
	UtenteController utenteController = new UtenteController();
	@SuppressWarnings("deprecation")
	@Test
	void testAggiungiInteressi() {
		
		//utent mock per ora, bisogna caricare da db
		Utente utente1 = new Utente(1, "asalvucci", "andrea@gmail.com", "Andrea", "Salvucci", new Date(1997,11,14) ,"C:/");
		Utente utente2 = new Utente(2, "nbartelucci", "nicolo1@gmail.com", "Nicolo", "Bartelucci", new Date(1997,11,14) ,"C:/");
		Utente utente3 = new Utente(3, "nsaccone", "nicolo2@gmail.com", "Nicolo", "Saccone", new Date(1997,11,14) ,"C:/");
		utenteController.aggiungiUtente(utente1);
		utenteController.aggiungiUtente(utente2);
		utenteController.aggiungiUtente(utente3);
		
		utenteController.aggiungiInteressi(Interessi.CINEMA,utente1);
		utenteController.aggiungiInteressi(Interessi.ARTE,utente1);
		utenteController.aggiungiInteressi(Interessi.CUCINA,utente2);
		assertFalse(utenteController.aggiungiInteressi(Interessi.CINEMA,utente1));
		assertTrue(utenteController.aggiungiInteressi(Interessi.MUSICA,utente1));
	}
	/*void testInsertUtente() {
		Utente utente1 = new Utente(1, "orcoddio", "andrea@gmail.com", "Andrea", "Salvucci", new Date(1997,14,11),"C:/");
		RegistrazioneController rc = new RegistrazioneController();
		MssqlUtenteDAO dao = new MssqlUtenteDAO(rc.getDbConnection());
		dao.create(utente1);
		
	}*/
	@Test
	void testDeleteUtente() {
		int idEsistente = 5;
		int idNonEsistente = 90;
		UtenteController uc = new UtenteController();
		Connection conn = uc.getDbConnection();	
		MssqlUtenteDAO dao = new MssqlUtenteDAO(conn);
		assertTrue(dao.delete(idEsistente));
		assertFalse(dao.delete(idNonEsistente));
	}

}
