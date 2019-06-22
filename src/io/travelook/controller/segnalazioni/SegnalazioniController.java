package io.travelook.controller.segnalazioni;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Segnalazione;
import io.travelook.persistence.MssqlSegnalazioniDAO;

public class SegnalazioniController extends Controller implements ISegnalazioni {
	private List<Segnalazione> segnalazioni= new ArrayList<Segnalazione>();
	private MssqlSegnalazioniDAO db;
    
    public SegnalazioniController() {
		super();
		// TODO Auto-generated constructor stub
		db = new MssqlSegnalazioniDAO(super.getDbConnection());
	}

	
    public boolean segnalaUtente(Segnalazione s ) {
    	boolean res =false;
        if(s==null) {
        	return res;
        }else {
    	segnalazioni.add(s);
    	db.create(s);
    	res=true;
        }
        return res;
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
    
    public boolean rimuoviSegnalazione(int segnalazioneid) {
    	boolean res=false;
    	if(segnalazioneid<0) {
    		return res;
    	}
    	else {
    	Segnalazione torem=null;
    	for(Segnalazione s:segnalazioni) {
    		if(s.getIdSegnalazione()==segnalazioneid) {
    			torem=s;
    		}
    	}
    	segnalazioni.remove(torem);
    	res=true;
    	}
    	return res;
    	
    }

	public List<Segnalazione> getSegnalazioni() {
		return segnalazioni;
	}

	public void setSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}
    
}
