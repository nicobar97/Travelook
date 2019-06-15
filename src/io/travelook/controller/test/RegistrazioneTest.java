package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.Test;

import io.travelook.controller.autenticazione.LoginController;
import io.travelook.controller.autenticazione.RegistrazioneController;
import io.travelook.model.Utente;
import io.travelook.utils.BCrypt;

class RegistrazioneTest {
	

	/*@Test
	void registraUtenteTest(){
		String nome ="Andrea";
		String cognome = "Salvucci";
		String username = "asalvucci";
		String password = "aooooo";
		Date dataNascita = new Date(1997,11,14);
		String email ="a@d";
		assertTrue(rc.registraUtente(nome, cognome, dataNascita, username, email, password));
		
	}*/
	
	@Test
	void registraELoggaTest() {
		RegistrazioneController rc = new RegistrazioneController();
		LoginController lc = new LoginController();
		Utente utente1 = new Utente(1, "bsalvucci", "andrea@gmail.com", "Andrea", "Salvucci", new Date(1997,11,14) ,"C:/");
		rc.registraUtente(utente1, BCrypt.hashpw("ciao", BCrypt.gensalt()));
		
		//qua l'utente logga
		
		assertTrue(lc.verificaCredenziali("bsalvucci", BCrypt.hashpw("ciao", BCrypt.gensalt())));
	}
	

}
