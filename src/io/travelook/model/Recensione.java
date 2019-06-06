package io.travelook.model;

public class Recensione {

	private int voto ; 
	private String titolo;
	private String corpo;
	private String foto;
	private String risposta;
	private int idutenterisposta;
   
	public Recensione(int voto, String titolo, String corpo, String foto, String risposta, int idutenterisposta) {
		super();
		this.voto = voto;
		this.titolo = titolo;
		this.corpo = corpo;
		this.foto = foto;
		this.risposta = risposta;
		this.idutenterisposta = idutenterisposta;
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
	public int getIdutenterisposta() {
		return idutenterisposta;
	}
	public void setIdutenterisposta(int idutenterisposta) {
		this.idutenterisposta = idutenterisposta;
	}
}
