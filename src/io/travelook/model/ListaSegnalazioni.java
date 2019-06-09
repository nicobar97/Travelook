package io.travelook.model;
import java.util.ArrayList;
import java.util.List;

import  io.travelook.model.Segnalazione;

public class ListaSegnalazioni { //DA RIMUOVERE
	
	private List<Segnalazione> segnalazioni;
	
    public ListaSegnalazioni() {
    	this.segnalazioni = new ArrayList<Segnalazione>();
	}
	public ListaSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}
    
	public List<Segnalazione> getSegnalazioni() {
		return segnalazioni;
	}
	public void setSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}
    
	public List<Segnalazione> getSegnalazioneByIdViaggio(int idviaggio){
		List<Segnalazione> res=new ArrayList<Segnalazione>();
		for(Segnalazione s : segnalazioni ) {
			if(s.getViaggio().getIdViaggio() == idviaggio) {
				res.add(s);
			}
		}
		return res;		
	}
	public List<Segnalazione> getSegnalazioniByIdSegnalato(int idsegnalato){
		List<Segnalazione> res=new ArrayList<Segnalazione>();
		for(Segnalazione s : segnalazioni ) {
			if(s.getSegnalato().getId() == idsegnalato) {
				res.add(s);
			}
		}
		return res;		
	}
	public List<Segnalazione> getSegnalazioniByIdSegnalante(int idsegnalante){
		List<Segnalazione> res = new ArrayList<Segnalazione>();
		for(Segnalazione s : segnalazioni ) {
			if(s.getSegnalante().getId() == idsegnalante) {
				res.add(s);
			}
		}
		return res;		
	}
}
