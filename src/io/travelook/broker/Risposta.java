package io.travelook.broker;


public class Risposta {
	

	private String ipDestinatario;
	private Integer portaDest;
	private Object[] valori;
	
	public Risposta() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Risposta(String ipDestinatario, Integer portaDest, Object[] valori) {
		super();
		this.ipDestinatario = ipDestinatario;
		this.portaDest = portaDest;
		this.valori = valori;
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
	
	
	}
