package io.travelook.controller.autenticazione;

import java.util.Date;

import io.travelook.controller.Controller;
import io.travelook.model.Entry;
import io.travelook.model.Utente;
import io.travelook.persistence.MssqlLoginDAO;
import io.travelook.persistence.MssqlUtenteDAO;

public class RegistrazioneController extends Controller implements IRegistrazione{
	
	MssqlLoginDAO db;
	MssqlUtenteDAO ud = new MssqlUtenteDAO(super.getDbConnection());
	public RegistrazioneController() {
		db = new MssqlLoginDAO();
	}
	
	public boolean registraUtente(Utente u, String hash ) {
		// TODO Auto-generated method stub
		boolean esito = false;
		boolean esito2 = false;
		ud.setConn(super.getDbConnection());
		if(u.getUsername().length() > 6 && !u.getEmail().trim().equals("") /*&& altri controlli*/ ) {
			esito=db.create(u.getUsername(), hash);/*controlli da fare client side*/
			if(esito)
				esito2=ud.create(u);
		}
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,u.getId(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return esito2;
	}

}
