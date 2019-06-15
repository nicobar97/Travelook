package io.travelook.controller.autenticazione;

public interface ILogin {
	public boolean verificaCredenziali(String username, String password);
	public void passwordDimenticata(String email);
}
