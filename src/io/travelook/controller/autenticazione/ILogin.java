package io.travelook.controller.autenticazione;

public interface ILogin {
	public boolean verificaCredenziali(String username, String hashFromClient);
	public void passwordDimenticata(String email);
}
