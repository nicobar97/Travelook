package io.travelook.controller.autenticazione;

import java.util.Date;
import java.util.Optional;

import io.travelook.controller.Controller;
import io.travelook.model.Entry;
import io.travelook.persistence.MssqlLoginDAO;

public class LoginController extends Controller implements ILogin {

	MssqlLoginDAO loginDao;
	
	public LoginController() {
		this.loginDao = new MssqlLoginDAO();
	}
	@Override
	public boolean verificaCredenziali(String username, String hashFromClient) {		
		Optional<String> hashLetto = null;
		if(hashFromClient != null)
			hashLetto = loginDao.read(username);
		System.out.println("Hash inviato: " + hashFromClient);
		System.out.println("Hash letto: " + hashLetto);
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," "+username);
        super.scriviOperazioneLog(e);
		if(hashLetto != null && hashLetto.isPresent()) {
			if(hashFromClient != null)
				return hashLetto.get().equals(hashFromClient);
			else
				return false;
		}
		else
			return false;
		
	}

	@Override
	public void passwordDimenticata(String email) {
		// TODO Auto-generated method stub
		
	}

}
