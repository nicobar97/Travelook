package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import io.travelook.controller.autenticazione.LoginController;
import io.travelook.controller.autenticazione.RegistrazioneController;
import io.travelook.model.Utente;
import io.travelook.utils.SHA256;

class RegistrazioneTestDue {

	/*@Test
	void testRegistrazione() {
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(in);
		String username="";
		String pwd="";
		String salt="travelook";
		
		try {
			System.out.println("Inserisci Username: ");
			username = br.readLine();
			System.out.println("Inserisci password: ");
			pwd = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			UtenteController uc = new UtenteController();
			MssqlLoginDAO login = new MssqlLoginDAO(uc.getDbConnection());
			assertTrue(login.create(username, SHA256.encrypt(pwd)));
		}
		
	}*/
	
	/*@Test
	void testLogin() {
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(in);
		String username="";
		String pwd="";
		String salt = "travelook";
		
		try {
			System.out.println("Inserisci Username: ");
			username = br.readLine();
			System.out.println("Inserisci password: ");
			pwd = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			UtenteController uc = new UtenteController();
			MssqlLoginDAO login = new MssqlLoginDAO(uc.getDbConnection());
			LoginController lc = new LoginController();
			String hashFromClient = SHA256.encrypt(pwd);
			assertTrue(lc.verificaCredenziali(username, hashFromClient));
		}
		
	}
	*/
	
	@Test
	void registraEAutentica() {
		Utente utente1 = new Utente("nicobar", "nicobar97@gmail.com", "nicolò", "bartelucci", new Date(1997, 03, 02) ,"user1.png");
		LoginController lc = new LoginController();
		RegistrazioneController rc = new RegistrazioneController();
		assertTrue(rc.registraUtente(utente1, SHA256.encrypt("porcodio97")));
		assertTrue(lc.verificaCredenziali(utente1.getUsername(), SHA256.encrypt("porcodio97")));
	}

}
