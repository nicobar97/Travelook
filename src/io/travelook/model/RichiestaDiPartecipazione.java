package io.travelook.model;

import java.io.Serializable;

public class RichiestaDiPartecipazione implements Serializable {
	private int id;
	private Utente utente;
	private Viaggio viaggio;
	private String messaggioRichiesta;
	private String messaggioRisposta;
	private Stato stato;
	public RichiestaDiPartecipazione() {
		super();
	}
	public RichiestaDiPartecipazione(Utente utente, Viaggio viaggio, String messaggioRichiesta) throws IllegalArgumentException {
		if(utente == null || viaggio == null || messaggioRichiesta == null || messaggioRichiesta.trim().equals(""))
			throw new IllegalArgumentException();
		this.messaggioRichiesta = messaggioRichiesta;
		this.setUtente(utente);
		this.setViaggio(viaggio);
		this.stato = Stato.NONVISTA;
	}
	public RichiestaDiPartecipazione(Utente utente, Viaggio viaggio, String messaggioRichiesta, String messaggioRisposta,
			Stato stato) throws IllegalArgumentException {
		if(utente == null || viaggio == null || messaggioRichiesta == null || messaggioRichiesta.trim().equals("") ||
				messaggioRisposta == null || messaggioRisposta.trim().equals("") || stato == null)
			throw new IllegalArgumentException();
		this.messaggioRichiesta = messaggioRichiesta;
		this.messaggioRisposta = messaggioRisposta;
		this.setUtente(utente);
		this.setViaggio(viaggio);
		this.stato = stato;
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
	public void setRisposta(String messaggioRisposta) {
		this.messaggioRisposta = messaggioRisposta;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	public void setViaggio(Viaggio viaggio) {
		this.viaggio = viaggio;
	}
	
}
