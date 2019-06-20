package io.travelook.broker;

import java.io.Serializable;
import java.util.List;

public class Richiesta<T> implements Serializable {

	
	private String ipMittente;
	private Integer portaMittente;
	private List<T> argomenti;
	private String servizio;
	
	public Richiesta() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Richiesta(String ipMittente, Integer portaMittente, List<T> argomenti, String servizio) {
		super();
		this.ipMittente = ipMittente;
		this.portaMittente = portaMittente;
		this.argomenti = argomenti;
		this.servizio = servizio;
	}

	public String getIpMittente() {
		return ipMittente;
	}

	public void setIpMittente(String ipMittente) {
		this.ipMittente = ipMittente;
	}

	public Integer getPortaMittente() {
		return portaMittente;
	}

	public void setPortaMittente(Integer portaMittente) {
		this.portaMittente = portaMittente;
	}

	public List<T> getArgomenti() {
		return argomenti;
	}

	public void setArgomenti(List<T> argomenti) {
		this.argomenti = argomenti;
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}
	
	
}
