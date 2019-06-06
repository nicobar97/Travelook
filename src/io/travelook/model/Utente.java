package io.travelook.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.model.Interessi;
import  io.travelook.model.Recensione;

public class Utente {
   
	private String id;
	private String email; 
	private String nome;
	private String cognome;
	private Date dataNascita;
	private String immagineProfilo;
	private String[] immaginiAggiuntive;
	private Storico storico;
	private List<Interessi> interessi;
	private List<Recensione> recensioni;
	
	//Per creazione utente
	public Utente(String id, String email, String nome, String cognome, Date dataNascita, String immagineProfilo) {
		super();
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.immagineProfilo = immagineProfilo;
		this.storico = new Storico(id);
		this.interessi = new ArrayList<Interessi>();
		this.recensioni = new ArrayList<Recensione>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getImmagineProfilo() {
		return immagineProfilo;
	}
	public void setImmagineProfilo(String immagineProfilo) {
		this.immagineProfilo = immagineProfilo;
	}
	public Storico getStorico() {
		return storico;
	}
	public void setStorico(Storico storico) {
		this.storico = storico;
	}
	public List<Interessi> getInteressi() {
		return interessi;
	}
	public void setInteressi(List<Interessi> interessi) {
		this.interessi = interessi;
	}
	public List<Recensione> getRecensioni() {
		return recensioni;
	}
	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}
	public String[] getImmaginiAggiuntive() {
		return immaginiAggiuntive;
	}
	public void setImmaginiAggiuntive(String[] immaginiAggiuntive) {
		this.immaginiAggiuntive = immaginiAggiuntive;
	}
	
	
	
}
