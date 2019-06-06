package io.travelook.model;

public class Segnalazione {

  private int idSegnalazione;
  private int idViaggio;
  private int idSegnalato;
  private int idSegnalante;
  private String messaggio;
  
  public Segnalazione(int idSegnalazione, int idViaggio, int idSegnalato, int idSegnalante, String messaggio) {
		super();
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
public String getMessaggio() {
	return messaggio;
}
public void setMessaggio(String messaggio) {
	this.messaggio = messaggio;
}
}
