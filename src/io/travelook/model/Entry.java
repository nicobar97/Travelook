package io.travelook.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {
	public Entry() {
		super();
		// TODO Auto-generated constructor stub
	}
	private int idEntry;
	private int idUtente;
	private Date timestamp;
	private String tipo;
	private String operazione;
	
	public Entry(int idEntry, int idUtente, Date timestamp, String tipo, String operazione) {
		this.idEntry = idEntry;
		this.idUtente = idUtente;
		this.timestamp = timestamp;
		this.tipo = tipo;
		this.operazione = operazione;
	}

	public int getIdEntry() {
		return idEntry;
	}

	public void setIdEntry(int idEntry) {
		this.idEntry = idEntry;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	@Override
	public String toString() {
		return idEntry + ";" + idUtente + ";" + new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss").format(timestamp) + ";" + tipo
				+ ";" + operazione ;
	}
	public String toStringDisplay() {
		return "idEntry " + idEntry + " --> [idUtente=" + idUtente + ", timestamp=" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp) + ", tipo=" + tipo
				+ ", operazione=" + operazione + "]";
	}
	
}
