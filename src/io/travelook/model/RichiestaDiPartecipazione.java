package io.travelook.model;

public class RichiestaDiPartecipazione {
	private Utente utente;
	private Viaggio viaggio;
	private String messaggioRichiesta;
	private String messaggioRisposta;
	private Stato stato;
	
	public RichiestaDiPartecipazione(Utente utente, Viaggio viaggio, String messaggioRichiesta) throws IllegalArgumentException {
		if(utente == null || viaggio == null || messaggioRichiesta == null || messaggioRichiesta.trim().equals(""))
			throw new IllegalArgumentException();
		this.messaggioRichiesta = messaggioRichiesta;
		this.utente = utente;
		this.viaggio = viaggio;
		this.stato = Stato.NONVISTA;
	}
	public RichiestaDiPartecipazione(Utente utente, Viaggio viaggio, String messaggioRichiesta, String messaggioRisposta,
			Stato stato) throws IllegalArgumentException {
		if(utente == null || viaggio == null || messaggioRichiesta == null || messaggioRichiesta.trim().equals("") ||
				messaggioRisposta == null || messaggioRisposta.trim().equals("") || stato == null)
			throw new IllegalArgumentException();
		this.messaggioRichiesta = messaggioRichiesta;
		this.messaggioRisposta = messaggioRisposta;
		this.utente = utente;
		this.viaggio = viaggio;
		this.stato = stato;
	}
	public Utente getUtente() {
		return utente;
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	public String getMessaggioRichiesta() {
		return messaggioRichiesta;
	}
	public void setMessaggioRichiesta(String messaggioRichiesta) {
		this.messaggioRichiesta = messaggioRichiesta;
	}
	public String getMessaggioRisposta() {
		return messaggioRisposta;
	}
	public void setRisposta(Stato stato, String messaggioRisposta) {
		this.stato = stato;
		this.messaggioRisposta = messaggioRisposta;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	
}
