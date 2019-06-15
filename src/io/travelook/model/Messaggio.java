package io.travelook.model;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class Messaggio {
	private int id;
	private Utente utente;
	private String messaggio;
	private Date timestamp;
	
	public Messaggio(Utente utente, String messaggio) throws IllegalArgumentException {
		if(utente == null || messaggio == null || messaggio.trim().equals(""))
			throw new IllegalArgumentException();
		this.setUtente(utente);
		this.setMessaggio(messaggio);
		this.setTimestamp(null);
	}

	public Messaggio() {
		// TODO Auto-generated constructor stub
	}

	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getFullTimeStr() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.timestamp);
	}
	public String getDateStr() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.timestamp);
	}
	public String getTimeStr() {
		return new SimpleDateFormat("HH:mm:ss").format(this.timestamp);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
