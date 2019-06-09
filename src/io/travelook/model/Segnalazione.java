package io.travelook.model;

public class Segnalazione {

	private int idSegnalazione;
	private Viaggio viaggio;
	private Utente segnalato;
	private Utente segnalante;
	private Messaggio messaggio;
  
  	public Segnalazione(int idSegnalazione, Viaggio viaggio, Utente segnalato, Utente segnalante,
  			Messaggio messaggio) throws IllegalArgumentException {
		super();
		if(idSegnalazione < 0 || segnalato== null || segnalante == null || messaggio == null)
			throw new IllegalArgumentException();
		this.idSegnalazione = idSegnalazione;
		this.viaggio = viaggio;
		this.segnalato = segnalato;
		this.segnalante = segnalante;
		this.messaggio = messaggio;
	}
  
	public int getIdSegnalazione() {
		return idSegnalazione;
	}
	public void setIdSegnalazione(int idSegnalazione) {
		this.idSegnalazione = idSegnalazione;
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	public void setViaggio(Viaggio viaggio) {
		this.viaggio = viaggio;
	}
	public Utente getSegnalato() {
		return segnalato;
	}
	public void setSegnalato(Utente segnalato) {
		this.segnalato = segnalato;
	}
	public Utente getSegnalante() {
		return segnalante;
	}
	public void setSegnalante(Utente segnalante) {
		this.segnalante = segnalante;
	}
	public Messaggio getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(Messaggio messaggio) {
		this.messaggio = messaggio;
	}
}
