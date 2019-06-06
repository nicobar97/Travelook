package io.travelook.controller;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Segnalazione;

public class SegnalazioniController {
    public SegnalazioniController() {
		super();
		// TODO Auto-generated constructor stub
	}

	private List<Segnalazione> segnalazioni= new ArrayList<Segnalazione>();
    
    public void segnalaUtente(Segnalazione s ) {
    	segnalazioni.add(s);
    }
    
    public List<Segnalazione> getSegnalazioniUtente (int id){
    	List<Segnalazione> res = new ArrayList<Segnalazione>();
    	for(Segnalazione s :segnalazioni) {
    		if(s.getIdSegnalante()==id) {
    			res.add(s);
    		}
    	}
    	return res;
    }
    public Segnalazione leggiSegnalazione(int segnalazioneid ) {
    	Segnalazione res=null;
    	for(Segnalazione s : segnalazioni) {
    		if(s.getIdSegnalazione()==segnalazioneid) {
    			res=s;
    		}
    	}
    	return res;
    }
    
    public void rimuoviSegnalazione(int segnalazioneid) {
    	Segnalazione torem=null;
    	for(Segnalazione s:segnalazioni) {
    		if(s.getIdSegnalazione()==segnalazioneid) {
    			torem=s;
    		}
    	}
    	segnalazioni.remove(torem);
    }

	public List<Segnalazione> getTutteSegnalazioni() {
		return segnalazioni;
	}

	public void setSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}
    
}
