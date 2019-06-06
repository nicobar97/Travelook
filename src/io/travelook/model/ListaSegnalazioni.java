package io.travelook.model;
import java.util.ArrayList;
import java.util.List;

import  io.travelook.model.Segnalazione;

public class ListaSegnalazioni {
    public ListaSegnalazioni() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListaSegnalazioni(List<Segnalazione> segnalazioni) {
		super();
		this.segnalazioni = segnalazioni;
	}
    
	private List<Segnalazione> segnalazioni = new ArrayList<Segnalazione>();

	public List<Segnalazione> getSegnalazioni() {
		return segnalazioni;
	}

	public void setSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}
    
	public List<Segnalazione>  getSegnalazioniViaggio(int idviaggio){
		List<Segnalazione> res=new ArrayList<Segnalazione>();
		for(Segnalazione s : segnalazioni ) {
			if(s.getIdViaggio()==idviaggio) {
				res.add(s);
			}
		}
		return res;		
	}
	
	public List<Segnalazione>  getSegnalazioniSegnalato(int idsegnalato){
		List<Segnalazione> res=new ArrayList<Segnalazione>();
		for(Segnalazione s : segnalazioni ) {
			if(s.getIdSegnalato()==idsegnalato) {
				res.add(s);
			}
		}
		return res;		
	}
	
	public List<Segnalazione>  getSegnalazioniSegnalante(int idsegnalante){
		List<Segnalazione> res=new ArrayList<Segnalazione>();
		for(Segnalazione s : segnalazioni ) {
			if(s.getIdSegnalante()==idsegnalante) {
				res.add(s);
			}
		}
		return res;		
	}
	
}
