package io.travelook.controller.rdp;

import java.util.Date;

import io.travelook.controller.Controller;
import io.travelook.controller.autenticazione.IRegistrazione;
import io.travelook.model.Utente;
import io.travelook.persistence.MssqlRegistrazioneDAO;
import io.travelook.persistence.MssqlUtenteDAO;
import io.travelook.utils.BCrypt;

public class RegistrazioneController extends Controller implements IRegistrazione{
	
	MssqlRegistrazioneDAO db;
	MssqlUtenteDAO ud = new MssqlUtenteDAO(super.getDbConnection());
	public RegistrazioneController() {
		db = new MssqlRegistrazioneDAO(super.getDbConnection());
	}


	
	public boolean registraUtente(Utente u, String hash ) {
		// TODO Auto-generated method stub
		boolean esito = true;
		
		//controllo che i campi siano quantomeno inizializzati, per ora ho fissato la lunghezza minima
		// dello username a 6
		
		//qua occorre fare la query al database, l'esito sar� anche dato dal fatto che ci sia o meno
		//un altro utente registrato con lo stesso username
		//commento per commit
		
		esito=db.create(u.getUsername(), hash);
		boolean esito2=ud.create(u);
		
		return esito & esito2;
	}


}
