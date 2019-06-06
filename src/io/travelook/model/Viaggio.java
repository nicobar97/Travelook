package io.travelook.model;

import java.util.Date;

public class Viaggio {

	private int idViaggio;
	private int idCreatore;
	private String titolo ;
	private String destinazione;
	private int numeropartecipanti;
	private String lingua; 
	private int budget ;
	private String descrizione;
	private String luogoPartenza;
	private Date dataInizio;
	private Date dataFine;
	private Stato stato;
	   
	public Viaggio(int idViaggio, String titolo, String destinazione, String lingua, int budget, 
			String descrizione, String luogoPartenza, Date dataInizio, Date dataFine) throws IllegalArgumentException {
		if(idViaggio < 0 || titolo == null || destinazione == null || lingua == null || budget < 0 || 
				budget < 5 || descrizione == null || dataInizio == null)
			throw new IllegalArgumentException();
		this.idViaggio = idViaggio;
		this.titolo = titolo;
		this.destinazione = destinazione;
		this.numeropartecipanti = 1;
		this.lingua = lingua;
		this.budget = budget;
		this.descrizione = descrizione;
		this.luogoPartenza = luogoPartenza;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.setStato(Stato.INIZIO);
	}
	
	public int getIdViaggio() {
		return idViaggio;
	}
	public void setIdViaggio(int idViaggio) {
		this.idViaggio = idViaggio;
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
		return luogoPartenza;
	}
	public void setLuogopartenza(String luogoPartenza) {
		this.luogoPartenza = luogoPartenza;
	}
	public Date getDatainizio() {
		return dataInizio;
	}
	public void setDatainizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDatafine() {
		return dataFine;
	}
	public void setDatafine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public int getIdCreatore() {
		return idCreatore;
	}

	public void setIdCreatore(int idCreatore) {
		this.idCreatore = idCreatore;
	}
}
