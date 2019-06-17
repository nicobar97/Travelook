package io.travelook.broker;

import java.util.HashMap;

public class RichiesteDispatcher implements IBroker {
    
	
	private HashMap<String,EndPointServer> serviziMap;
	private HashMap<EndPointClient,EndPointServer> connessioniMap;
    
	
	public RichiesteDispatcher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private boolean aggiungiConnessione(EndPointClient c, EndPointServer s ) {
		boolean res=false;
		if(c == null || s==null) {
			res=false;
		}
		connessioniMap.put(c,s);
		res=true;
		return res;
	}
	
	@Override
	public boolean registraServizio(String metodo, EndPointServer s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inoltraRichiesta(Richiesta r, EndPointServer s) {
		// TODO Auto-generated method stub
		return false;
	}
    
	public boolean EliminaServizio(EndPointServer s){
		return false; 
	}
	@Override
	public boolean inoltraRisposta(Risposta r, EndPointClient c) {
		// TODO Auto-generated method stub
		return false;
	}

}
