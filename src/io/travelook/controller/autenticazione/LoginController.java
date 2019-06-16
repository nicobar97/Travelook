package io.travelook.controller.autenticazione;

import io.travelook.controller.Controller;
import io.travelook.persistence.MssqlLoginDAO;
import io.travelook.utils.BCrypt;

public class LoginController extends Controller implements ILogin {

	MssqlLoginDAO loginDao;
	
	public LoginController() {
		this.loginDao = new MssqlLoginDAO(super.getDbConnection());
	}
	@Override
	public boolean verificaCredenziali(String username, String hashFromClient) {		
		String hashLetto = loginDao.read(username);
		System.out.println("Hash inviato: "+hashFromClient);
		System.out.println("Hash letto: " + hashLetto);
		return hashLetto.equals(hashFromClient);
		
	}

	@Override
	public void passwordDimenticata(String email) {
		// TODO Auto-generated method stub
		
	}

}
