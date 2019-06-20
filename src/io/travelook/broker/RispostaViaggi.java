package io.travelook.broker;

import java.util.List;

import io.travelook.model.Viaggio;

public class RispostaViaggi {
	

	private String ipDestinatario;
	private Integer portaDest;
	private List<Viaggio> listaViaggi;
	private int numero_valori;
	
	public RispostaViaggi() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RispostaViaggi(String ipDestinatario, Integer portaDest, List<Viaggio> valori) {
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
	public List<Viaggio> getValori() {
		return listaViaggi;
	}
	public void setValori(List<Viaggio> valori) {
		this.listaViaggi = valori;
	} 
	
	public int getNumeroValori() {return numero_valori;}
	
	public void setNumeroValori(int n) {this.numero_valori=n;}
	
	
	}
