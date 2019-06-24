package io.travelook.controller.segnalazioni;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Entry;
import io.travelook.model.Segnalazione;
import io.travelook.persistence.MssqlSegnalazioniDAO;

public class SegnalazioniController extends Controller implements ISegnalazioni {
	private List<Segnalazione> segnalazioni= new ArrayList<Segnalazione>();
	private MssqlSegnalazioniDAO db;
    
    public SegnalazioniController() {
		super();
		// TODO Auto-generated constructor stub
		db = new MssqlSegnalazioniDAO();
	}

	
    public boolean segnalaUtente(Segnalazione s ) {
    	boolean res =false;
        if(s==null) {
        	return res;
        }else {
    	segnalazioni.add(s);
    	db.setConn(super.getDbConnection());
    	db.create(s);
    	res=true;
        }
        Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,s.getSegnalante().getId(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
        return res;
    }
    
    public List<Segnalazione> getSegnalazioniUtente (int id){
    	List<Segnalazione> res = new ArrayList<Segnalazione>();
    	/*for(Segnalazione s :segnalazioni) {
    		if(s.getSegnalante().getId()==id) {
    			res.add(s);
    		}
    	}*/
    	db.setConn(super.getDbConnection());
    	res=db.readSegnalazioniUtente(id);
    	 Date d=new Date();
         super.openWriterLog("hello2.txt");
         int ide=super.getLogcount()+1;
         String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
         Entry e=new Entry(ide,id,d,nameofmeth," ");
         super.scriviOperazioneLog(e);
    	return res;
    }
    public Segnalazione leggiSegnalazione(int segnalazioneid ) {
    	Segnalazione res=null;
    	for(Segnalazione s : segnalazioni) {
    		if(s.getIdSegnalazione()==segnalazioneid) {
    			res=s;
    			db.setConn(super.getDbConnection());
    			db.marcaSegnalazioneComeLetta(s);
    		}
    	}
    	 Date d=new Date();
         super.openWriterLog("hello2.txt");
         int ide=super.getLogcount()+1;
         String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
         Entry e=new Entry(ide,res.getSegnalante().getId(),d,nameofmeth," ");
         super.scriviOperazioneLog(e);
    	return res;
    }
    
    public boolean rimuoviSegnalazione(int segnalazioneid) {
    	boolean res=false;
    	Segnalazione torem=null;
    	if(segnalazioneid<0) {
    		return res;
    	}
    	else {
    	for(Segnalazione s:segnalazioni) {
    		if(s.getIdSegnalazione()==segnalazioneid) {
    			torem=s;
    		}
    	}
    	db.setConn(super.getDbConnection());
    	db.eliminaSegnalazione(segnalazioneid);
    	segnalazioni.remove(torem);
    	res=true;
    	}
    	 Date d=new Date();
         super.openWriterLog("hello2.txt");
         int ide=super.getLogcount()+1;
         String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
         Entry e=new Entry(ide,torem.getSegnalante().getId(),d,nameofmeth," ");
         super.scriviOperazioneLog(e);
    	return res;
    	
    }

	public List<Segnalazione> getSegnalazioni() {
		db.setConn(super.getDbConnection());
		this.segnalazioni=db.readAll();
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return segnalazioni;
	}

	public void setSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
	}
    
}
