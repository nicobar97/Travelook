package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;

import org.junit.Test;

import io.travelook.controller.autenticazione.LoginController;
import io.travelook.controller.autenticazione.RegistrazioneController;
import io.travelook.model.Utente;

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
	
	/*
	public static void main(String args[]) {
		RegistrazioneController rc = new RegistrazioneController();
		LoginController lc = new LoginController();
		Utente utente1 = new Utente("nicobar", "nicobar97@gmail.com", "nicolò", "bartelucci", new Date(1997, 03, 02) ,"user1.png");
		
		
		//qua l'utente logga
		System.out.println();
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(salt);
		String psw = "porcodio97";
		byte[] hashedPassword = md.digest(psw.getBytes(StandardCharsets.UTF_8));
		boolean result  = rc.registraUtente(utente1, hashedPassword);
		result = hashedPassword == hashedPassword;
		if(result)
			System.out.println("DIOCAN");
		else
			System.out.println("DIOmerd");
	
		result  = lc.verificaCredenziali("nicobar", hashedPassword );
		if(result)
			System.out.println("DIOCAN");
		else
			System.out.println("DIOmerd");
	}
	*/
	
	@Test 
	public static void main() {
		String password = "ciao";
		String hashed;
		
	}

}
