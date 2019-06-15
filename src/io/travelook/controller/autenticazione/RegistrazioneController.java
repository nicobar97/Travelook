package io.travelook.controller.autenticazione;

import java.util.Date;

import io.travelook.controller.Controller;
import io.travelook.model.Utente;
import io.travelook.persistence.MssqlLoginDAO;
import io.travelook.persistence.MssqlUtenteDAO;
import io.travelook.utils.BCrypt;

public class RegistrazioneController extends Controller implements IRegistrazione{
	
	MssqlLoginDAO db;
	MssqlUtenteDAO ud = new MssqlUtenteDAO(super.getDbConnection());
	public RegistrazioneController() {
		db = new MssqlLoginDAO(super.getDbConnection());
	}
	
	public boolean registraUtente(Utente u, byte[] hash ) {
		// TODO Auto-generated method stub
		boolean esito = false;
		boolean esito2 = false;
		if(u.getUsername().length() > 6 && !u.getEmail().trim().equals("") /*&& altri controlli*/ ) {
			esito=db.create(u.getUsername(), hash);/*controlli da fare client side*/
			if(esito)
				esito2=ud.create(u);
		}
		return esito2;
	}

}
