package io.travelook.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Viaggio implements Serializable{

	public Viaggio() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int idViaggio;
	private Utente creatore;
	private String titolo ;
	private String destinazione;
	private List<Utente> partecipanti;
	private String lingua; 
	private int budget ;
	private String descrizione;
	private String luogoPartenza;
	private Date dataInizio;
	private Date dataFine;
	private Stato stato;
	private String immaginiProfilo;
	   
	public Viaggio(int idViaggio, Utente creatore, String titolo, String destinazione, String lingua, int budget, ArrayList<Utente> partecipanti,
			String descrizione, String luogoPartenza, Date dataInizio, Date dataFine, Stato stato) throws IllegalArgumentException {
		if(idViaggio < 0 || titolo == null || destinazione == null || lingua == null || budget < 0 || 
				budget > 5 || descrizione == null || dataInizio == null || creatore == null)
			throw new IllegalArgumentException();
		this.idViaggio = idViaggio;
		this.creatore = creatore;
		this.titolo = titolo;
		this.destinazione = destinazione;
		this.setPartecipanti(partecipanti);
		this.lingua = lingua;
		this.budget = budget;
		this.descrizione = descrizione;
		this.luogoPartenza = luogoPartenza;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.setStato(stato);
	}
	public Viaggio(int idViaggio, Utente creatore, String titolo, String destinazione, String lingua, int budget,
			String descrizione, String luogoPartenza, Date dataInizio, Date dataFine, Stato stato) throws IllegalArgumentException {
		if(idViaggio < 0 || titolo == null || destinazione == null || lingua == null || budget < 0 || 
				budget > 5 || descrizione == null || dataInizio == null || creatore == null)
			throw new IllegalArgumentException();
		this.idViaggio = idViaggio;
		this.creatore = creatore;
		this.titolo = titolo;
		this.destinazione = destinazione;
		this.lingua = lingua;
		this.budget = budget;
		this.descrizione = descrizione;
		this.luogoPartenza = luogoPartenza;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.setStato(stato);
	}
	public Viaggio(int idViaggio, Utente creatore, String titolo, String destinazione, String lingua, int budget, ArrayList<Utente> partecipanti,
			String descrizione, String luogoPartenza, Date dataInizio, Date dataFine, Stato stato,String imP) throws IllegalArgumentException {
		if(idViaggio < 0 || titolo == null || destinazione == null || lingua == null || budget < 0 || 
				budget > 5 || descrizione == null || dataInizio == null || creatore == null)
			throw new IllegalArgumentException();
		this.idViaggio = idViaggio;
		this.creatore = creatore;
		this.titolo = titolo;
		this.destinazione = destinazione;
		this.setPartecipanti(partecipanti);
		this.lingua = lingua;
		this.budget = budget;
		this.descrizione = descrizione;
		this.luogoPartenza = luogoPartenza;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.setStato(stato);
		this.immaginiProfilo=imP;		
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

	public Utente getCreatore() {
		return creatore;
	}
	public void addPartecipante(Utente u) {
		this.partecipanti.add(u);
	}
	public void setCreatore(Utente creatore) {
		this.creatore = creatore;
	}

	public List<Utente> getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(List<Utente> partecipanti) {
		this.partecipanti = partecipanti;
	}

	public String getImmaginiProfilo() {
		return immaginiProfilo;
	}

	public void setImmaginiProfilo(String immaginiProfilo) {
		this.immaginiProfilo = immaginiProfilo;
	}

}
