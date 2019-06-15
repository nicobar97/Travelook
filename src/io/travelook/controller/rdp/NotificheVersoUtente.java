package io.travelook.controller.rdp;

import io.travelook.controller.notifica.INotifica;
import io.travelook.controller.notifica.NotificheEmail;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class NotificheVersoUtente extends NotificheVerso implements Observer {
	private Utente utente;
	private Viaggio viaggio;
	private String messaggio;
	private Stato stato;
	
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
	public NotificheVersoUtente(Utente utente, Viaggio viaggio, String messaggio, Stato stato) throws NullPointerException {
		if(utente == null || viaggio == null || messaggio.trim().equals("") || messaggio == null || stato == null)
			throw new NullPointerException();
		this.utente = utente;
		this.viaggio = viaggio;
		this.messaggio = messaggio;
		this.stato = stato;
	}

	@Override
	public void update() {
		INotifica notifica = new NotificheEmail();
		String messaggio = "Sei stato " + (stato.compareTo(Stato.ACCETTATA) == 0 ? "accettato" : "rifiutato") + 
				" da "+ this.utente.getUsername() + " per il viaggio di titolo: " + this.viaggio.getTitolo() + 
				"\nMessaggio:\n" + this.messaggio;
		notifica.inviaNotifica(this.utente.getEmail(), messaggio, "Travelook: Esito richiesta di partecipazione");
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
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
}
