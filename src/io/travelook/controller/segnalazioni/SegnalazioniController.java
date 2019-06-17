package io.travelook.controller.segnalazioni;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Segnalazione;
import io.travelook.persistence.MssqlSegnalazioniDAO;

public class SegnalazioniController extends Controller {
	private List<Segnalazione> segnalazioni= new ArrayList<Segnalazione>();
	private MssqlSegnalazioniDAO db;
    
    public SegnalazioniController() {
		super();
		// TODO Auto-generated constructor stub
		db = new MssqlSegnalazioniDAO(super.getDbConnection());
	}

	
    public void segnalaUtente(Segnalazione s ) {
    	segnalazioni.add(s);
    	db.create(s);
    }
    
    public List<Segnalazione> getSegnalazioniUtente (int id){
    	List<Segnalazione> res = new ArrayList<Segnalazione>();
    	for(Segnalazione s :segnalazioni) {
    		if(s.getSegnalante().getId()==id) {
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
    			db.marcaSegnalazioneComeLetta(s);
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
