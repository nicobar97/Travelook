package io.travelook.model;

import java.util.Date;

public class Viaggio {

	   private int id;
	   private String titolo ;
	   private String destinazione;
	   private int numeropartecipanti;
	   private String lingua ; 
	   private int budget ;
	   private String descrizione;
	   private String luogopartenza ;
	   private Date datainizio;
	   private Date datafine;
	   
	   public Viaggio(int id, String titolo, String destinazione, int numeropartecipanti, String lingua, int budget,
				String descrizione, String luogopartenza, Date datainizio, Date datafine) {
			super();
			this.id = id;
			this.titolo = titolo;
			this.destinazione = destinazione;
			this.numeropartecipanti = numeropartecipanti;
			this.lingua = lingua;
			this.budget = budget;
			this.descrizione = descrizione;
			this.luogopartenza = luogopartenza;
			this.datainizio = datainizio;
			this.datafine = datafine;
		}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDestinazione() {
		return destinazione;
	}
	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}
	public int getNumeropartecipanti() {
		return numeropartecipanti;
	}
	public void setNumeropartecipanti(int numeropartecipanti) {
		this.numeropartecipanti = numeropartecipanti;
	}
	public String getLingua() {
		return lingua;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getLuogopartenza() {
		return luogopartenza;
	}
	public void setLuogopartenza(String luogopartenza) {
		this.luogopartenza = luogopartenza;
	}
	public Date getDatainizio() {
		return datainizio;
	}
	public void setDatainizio(Date datainizio) {
		this.datainizio = datainizio;
	}
	public Date getDatafine() {
		return datafine;
	}
	public void setDatafine(Date datafine) {
		this.datafine = datafine;
	}
}
