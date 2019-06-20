package io.travelook.broker;

import java.io.Serializable;
import java.util.List;

import io.travelook.model.Viaggio;

public class Risposta<T> implements Serializable{
	

	private String ipDestinatario;
	private Integer portaDest;
	private List<T> listaViaggi;
	private int numero_valori;
	
	public Risposta() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Risposta(String ipDestinatario, Integer portaDest, List<T> valori) {
		super();
		this.ipDestinatario = ipDestinatario;
		this.portaDest = portaDest;
		this.listaViaggi = valori;
		this.numero_valori = valori.size();
	}
	
	public String getIpDestinatario() {
		return ipDestinatario;
	}
	public void setIpDestinatario(String ipDestinatario) {
		this.ipDestinatario = ipDestinatario;
	}
	public Integer getPortaDest() {
		return portaDest;
	}
	public void setPortaDest(Integer portaDest) {
		this.portaDest = portaDest;
	}
	public List<T> getValori() {
		return listaViaggi;
	}
	public void setValori(List<T> valori) {
		this.listaViaggi = valori;
	} 
	
	public int getNumeroValori() {return numero_valori;}
	
	public void setNumeroValori(int n) {this.numero_valori=n;}
	
	
	}
