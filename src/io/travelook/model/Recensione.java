package io.travelook.model;

public class Recensione {
	
	private int idUtenteRecensito; //che sarebbe idUtenteRisposta
	private int voto;
	private String titolo;
	private String corpo;
	private String foto;
	private String risposta;
	private Utente utenteRecensitore; //relazione
	
	public Recensione(int idUtenteRecensito, int voto, String titolo, String corpo, Utente utenteRecensitore)
			throws IllegalArgumentException {
		if(idUtenteRecensito < 0 || voto < 0 || voto > 5 || corpo == null || utenteRecensitore == null)
			throw new IllegalArgumentException();
		this.idUtenteRecensito = idUtenteRecensito;
		this.voto = voto;
		this.titolo = titolo;
		this.corpo = corpo;
		this.utenteRecensitore = utenteRecensitore;
	}
	public Recensione(int idUtenteRecensito, int voto, String titolo, String corpo, Utente utenteRecensitore, String risposta)
			throws IllegalArgumentException {
		if(idUtenteRecensito < 0 || voto < 0 || voto > 5 || corpo == null || utenteRecensitore == null || 
				risposta == null || risposta.trim().equals(risposta) || titolo == null || titolo.trim().equals(""))
			throw new IllegalArgumentException();
		this.idUtenteRecensito = idUtenteRecensito;
		this.voto = voto;
		this.titolo = titolo;
		this.corpo = corpo;
		this.utenteRecensitore = utenteRecensitore;
		this.risposta = risposta;
	}

	public int getIdUtenteRecensito() {
		return idUtenteRecensito;
	}
	public void setIdUtenteRecensito(int idUtenteRecensito) {
		this.idUtenteRecensito = idUtenteRecensito;
	}
	public Utente getIdUtenteRecensitore() {
		return utenteRecensitore;
	}
	public void setIdUtenteRecensitore(Utente utenteRecensitore) {
		this.utenteRecensitore = utenteRecensitore;
	}
	public int getVoto() {
		return voto;
	}
	public void setVoto(int voto) {
		this.voto = voto;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
}
