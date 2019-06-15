package io.travelook.controller.autenticazione;

public interface ILogin {
	public String verificaCredenziali(String username, String password);
	public void passwordDimenticata(String email);
}
