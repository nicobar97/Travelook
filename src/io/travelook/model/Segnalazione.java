package io.travelook.model;

public class Segnalazione {

	private int idSegnalazione;
	private int idViaggio;
	private int idSegnalato;
	private int idSegnalante;
	private Messaggio messaggio;
  
  	public Segnalazione(int idSegnalazione, int idViaggio, int idSegnalato, int idSegnalante,
  			Messaggio messaggio) throws IllegalArgumentException {
		super();
		if(idSegnalazione < 0 || idSegnalato< 0 || idSegnalante < 0 || messaggio == null)
			throw new IllegalArgumentException();
		this.idSegnalazione = idSegnalazione;
		this.idViaggio = idViaggio;
		this.idSegnalato = idSegnalato;
		this.idSegnalante = idSegnalante;
		this.messaggio = messaggio;
	}
  
	public int getIdSegnalazione() {
		return idSegnalazione;
	}
	public void setIdSegnalazione(int idSegnalazione) {
		this.idSegnalazione = idSegnalazione;
	}
	public int getIdViaggio() {
		return idViaggio;
	}
	public void setIdViaggio(int idViaggio) {
		this.idViaggio = idViaggio;
	}
	public int getIdSegnalato() {
		return idSegnalato;
	}
	public void setIdSegnalato(int idSegnalato) {
		this.idSegnalato = idSegnalato;
	}
	public int getIdSegnalante() {
		return idSegnalante;
	}
	public void setIdSegnalante(int idSegnalante) {
		this.idSegnalante = idSegnalante;
	}
	public Messaggio getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(Messaggio messaggio) {
		this.messaggio = messaggio;
	}
}
