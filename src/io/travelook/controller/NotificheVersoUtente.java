package io.travelook.controller;

import java.util.Observable;
import java.util.Observer;

import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class NotificheVersoUtente implements Observer{
	private Utente utente;
	private Viaggio viaggio;
	private String messaggio;

	
	public NotificheVersoUtente(Utente utente, Viaggio viaggio) throws NullPointerException {
		if(utente == null || viaggio == null)
			throw new NullPointerException();
		this.utente = utente;
		this.viaggio = viaggio;
	}
	public NotificheVersoUtente(Utente utente, Viaggio viaggio, String messaggio) throws NullPointerException {
		if(utente == null || viaggio == null || messaggio.trim().equals("") || messaggio == null)
			throw new NullPointerException();
		this.utente = utente;
		this.viaggio = viaggio;
		this.messaggio = messaggio;
	}

	@Override
	public void update(Observable o, Object arg) {
		INotifica notifica = new NotificheEmail();
		String messaggio = "Nuova richiesta di partecipazione per " + this.viaggio.getTitolo() + "e da "
				+ this.utente.getUsername() + "\nMessaggio:\n" + this.messaggio;
		notifica.inviaNotifica(this.utente.getEmail(), messaggio);
	}

	public Utente getUtente() {
		return utente;
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
}
