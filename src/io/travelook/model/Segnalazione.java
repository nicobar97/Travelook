package io.travelook.model;

public class Segnalazione {

	public Segnalazione() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int idSegnalazione;
	private Utente segnalato;
	private Utente segnalante;
	private String messaggio;
    private Stato stato;
  	public Segnalazione(int idSegnalazione, Utente segnalato, Utente segnalante,
  			String messaggio,Stato stato) throws IllegalArgumentException {
		super();
		if(idSegnalazione < 0 || segnalato== null || segnalante == null || messaggio == null)
			throw new IllegalArgumentException();
		this.idSegnalazione = idSegnalazione;
		this.segnalato = segnalato;
		this.segnalante = segnalante;
		this.messaggio = messaggio;
		this.stato=stato;
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

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}
}
