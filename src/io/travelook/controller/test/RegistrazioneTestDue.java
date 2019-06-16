package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import io.travelook.controller.autenticazione.LoginController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.persistence.MssqlLoginDAO;
import io.travelook.utils.BCrypt;
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
	
	@Test
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
	

}
