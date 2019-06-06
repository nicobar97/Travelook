package io.travelook.model;

public class Recensione {
	
	private int idUtenteRecensito; //che sarebbe idUtenteRisposta
	private int voto;
	private String titolo;
	private String corpo;
	private String foto;
	private String risposta;
	private int idUtenteRecensitore; //relazione
	
	public Recensione(int idUtenteRecensito, int voto, String titolo, String corpo, int idUtenteRecensitore)
			throws IllegalArgumentException {
		if(idUtenteRecensito < 0 || voto < 0 || voto > 5 || corpo == null || idUtenteRecensitore < 0)
			throw new IllegalArgumentException();
		this.idUtenteRecensito = idUtenteRecensito;
		this.voto = voto;
		this.titolo = titolo;
		this.corpo = corpo;
		this.idUtenteRecensitore = idUtenteRecensitore;
	}

	public int getIdUtenteRecensito() {
		return idUtenteRecensito;
	}
	public void setIdUtenteRecensito(int idUtenteRecensito) {
		this.idUtenteRecensito = idUtenteRecensito;
	}
	public int getIdUtenteRecensitore() {
		return idUtenteRecensitore;
	}
	public void setIdUtenteRecensitore(int idUtenteRecensitore) {
		this.idUtenteRecensitore = idUtenteRecensitore;
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
