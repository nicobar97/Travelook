package io.travelook.controller.autenticazione;

import java.util.Optional;

import io.travelook.controller.Controller;
import io.travelook.persistence.MssqlLoginDAO;
import io.travelook.utils.BCrypt;

public class LoginController extends Controller implements ILogin {

	MssqlLoginDAO loginDao;
	
	public LoginController() {
		this.loginDao = new MssqlLoginDAO();
	}
	@Override
	public boolean verificaCredenziali(String username, String hashFromClient) {		
		Optional<String> hashLetto = loginDao.read(username);
		System.out.println("Hash inviato: "+hashFromClient);
		System.out.println("Hash letto: " + hashLetto);
		if(hashLetto.isPresent())
			return hashLetto.get().equals(hashFromClient);
		else
			return false;
		
	}

	@Override
	public void passwordDimenticata(String email) {
		// TODO Auto-generated method stub
		
	}

}
