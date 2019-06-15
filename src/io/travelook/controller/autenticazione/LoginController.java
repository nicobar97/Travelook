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
	public boolean verificaCredenziali(String username, String password) {		
		String hashLetto = loginDao.read(username);
		
		return BCrypt.checkpw(password, hashLetto);
		
	}

	@Override
	public void passwordDimenticata(String email) {
		// TODO Auto-generated method stub
		
	}

}
