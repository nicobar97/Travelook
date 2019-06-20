package io.travelook.broker;


public class Risposta {
	

	private String ipDestinatario;
	private Integer portaDest;
	private Object[] valori;
	private int numero_valori;
	
	public Risposta() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Risposta(Object[] valori) {
		this.valori=valori;
	}
	
	
	public Risposta(String ipDestinatario, Integer portaDest, Object[] valori, int numero_valori) {
		super();
		this.ipDestinatario = ipDestinatario;
		this.portaDest = portaDest;
		this.valori = valori;
		this.numero_valori = numero_valori;
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
	public Object[] getValori() {
		return valori;
	}
	public void setValori(Object[] valori) {
		this.valori = valori;
	} 
	
	public int getNumeroValori() {return numero_valori;}
	
	public void setNumeroValori(int n) {this.numero_valori=n;}
	
	
	}
