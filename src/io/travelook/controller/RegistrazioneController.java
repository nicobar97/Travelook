package io.travelook.controller;

import java.util.Date;

public class RegistrazioneController implements IRegistrazione {

	@Override
	public boolean registraUtente(String nome, String cognome, Date dataNascita, String username, String password) {
		// TODO Auto-generated method stub
		boolean esito = true;
		
		if(nome.isEmpty() || cognome.isEmpty() || username.length()<6 || password.isEmpty())
			return false;
		//controllo che i campi siano quantomeno inizializzati, per ora ho fissato la lunghezza minima
		// dello username a 6
		
		//qua occorre fare la query al database, l'esito sarà anche dato dal fatto che ci sia o meno
		//un altro utente registrato con lo stesso username
		
		
		return esito;
	}

}
