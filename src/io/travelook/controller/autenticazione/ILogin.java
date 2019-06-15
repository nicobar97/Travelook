package io.travelook.controller.autenticazione;

public interface ILogin {
	public boolean verificaCredenziali(String username, byte[] password);
	public void passwordDimenticata(String email);
}
