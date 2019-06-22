package io.travelook.model;

public class Segnalazione {

	private int idSegnalazione;
	private Utente segnalato;
	private Utente segnalante;
	private String messaggio;
  
  	public Segnalazione(int idSegnalazione, Utente segnalato, Utente segnalante,
  			String messaggio) throws IllegalArgumentException {
		super();
		if(idSegnalazione < 0 || segnalato== null || segnalante == null || messaggio == null)
			throw new IllegalArgumentException();
		this.idSegnalazione = idSegnalazione;
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
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
}
