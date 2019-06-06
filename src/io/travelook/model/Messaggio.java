package io.travelook.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Messaggio {
	private String idUtente;
	private String messaggio;
	private Date timestamp;
	
	public Messaggio(String idUtente, String messaggio) {
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
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
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
