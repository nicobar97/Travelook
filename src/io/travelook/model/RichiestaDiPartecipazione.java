package io.travelook.model;

public class RichiestaDiPartecipazione {
	private int idUtente;
	private int idViaggio;
	private String messaggioRichiesta;
	private String messaggioRisposta;
	private Stato stato;
	
	public RichiestaDiPartecipazione(int idUtente, int idViaggio, String messaggioRichiesta) throws IllegalArgumentException {
		if(idUtente < 0 || idViaggio < 0 || messaggioRichiesta == null || messaggioRichiesta.trim().equals(""))
			throw new IllegalArgumentException();
		this.messaggioRichiesta = messaggioRichiesta;
		this.idUtente = idUtente;
		this.idViaggio = idViaggio;
		this.stato = Stato.NONVISTA;
	}
	public RichiestaDiPartecipazione(int idUtente, int idViaggio, String messaggioRichiesta, String messaggioRisposta,
			Stato stato) throws IllegalArgumentException {
		if(idUtente < 0 || idViaggio < 0 || messaggioRichiesta == null || messaggioRichiesta.trim().equals("") ||
				messaggioRisposta == null || messaggioRisposta.trim().equals("") || stato == null)
			throw new IllegalArgumentException();
		this.messaggioRichiesta = messaggioRichiesta;
		this.messaggioRisposta = messaggioRisposta;
		this.idUtente = idUtente;
		this.idViaggio = idViaggio;
		this.stato = stato;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public int getIdViaggio() {
		return idViaggio;
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
	public void setMessaggioRisposta(String messaggioRisposta) {
		this.messaggioRisposta = messaggioRisposta;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	
}
