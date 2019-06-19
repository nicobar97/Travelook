package io.travelook.model;

public class Recensione {
	
	public Recensione() {
		super();
		// TODO Auto-generated constructor stub
	}
	private int idUtenteRecensito; //che sarebbe idUtenteRisposta
	private int voto;
	private String titolo;
	private String corpo;
	private String risposta;
	private int idUtenteRecensore; //relazione
	private int id;
	
	public Recensione(int idUtenteRecensito, int voto, String titolo, String corpo, int utenteRecensitore)
			throws IllegalArgumentException {
		if(idUtenteRecensito < 0 || voto < 0 || voto > 5 || corpo == null || utenteRecensitore < 0)
			throw new IllegalArgumentException();
		this.idUtenteRecensito = idUtenteRecensito;
		this.voto = voto;
		this.titolo = titolo;
		this.corpo = corpo;
		this.idUtenteRecensore = utenteRecensitore;
	}
	public Recensione(int idUtenteRecensito, int voto, String titolo, String corpo, int utenteRecensitore, String risposta)
			throws IllegalArgumentException {
		if(idUtenteRecensito < 0 || voto < 0 || voto > 5 || corpo == null || utenteRecensitore <0|| 
				risposta == null || risposta.trim().equals(risposta) || titolo == null || titolo.trim().equals(""))
			throw new IllegalArgumentException();
		this.idUtenteRecensito = idUtenteRecensito;
		this.voto = voto;
		this.titolo = titolo;
		this.corpo = corpo;
		this.idUtenteRecensore = utenteRecensitore;
		this.risposta = risposta;
	}

	public int getIdUtenteRecensito() {
		return idUtenteRecensito;
	}
	public void setIdUtenteRecensito(int idUtenteRecensito) {
		this.idUtenteRecensito = idUtenteRecensito;
	}
	public int getIdUtenteRecensitore() {
		return idUtenteRecensore;
	}
	public void setIdUtenteRecensitore(int utenteRecensitore) {
		this.idUtenteRecensore = utenteRecensitore;
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
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
