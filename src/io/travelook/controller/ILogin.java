package io.travelook.controller;

public interface ILogin {
	public String verificaCredenziali(String username, String password);
	public void passwordDimenticata(String email);
}
