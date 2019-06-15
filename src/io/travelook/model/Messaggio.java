package io.travelook.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.Timestamp;

public class Messaggio {
	private int id;
	private Utente utente;
	private String messaggio;
	private Timestamp timestamp;
	
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
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getFullTimeStr() {
		return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(this.timestamp);
	}
	public void setFullTimeStr(String time) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss");
			this.timestamp = (Timestamp) dtf.parse(time);
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
