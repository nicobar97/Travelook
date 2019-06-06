package io.travelook.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Messaggio {
	private int idUtente;
	private String messaggio;
	private Date timestamp;
	
	public Messaggio(int idUtente, String messaggio) throws IllegalArgumentException {
		if(idUtente < 0 || messaggio == null || messaggio.trim().equals(""))
			throw new IllegalArgumentException();
		this.setIdUtente(idUtente);
		this.setMessaggio(messaggio);
		this.setTimestamp(new Date());
	}

	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
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
}
