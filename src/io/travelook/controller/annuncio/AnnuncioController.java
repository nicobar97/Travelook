package io.travelook.controller.annuncio;
import java.util.Date;

import io.travelook.controller.Controller;
import io.travelook.model.Entry;
import io.travelook.model.Recensione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlViaggioDAO;

public class AnnuncioController extends Controller implements IAnnuncio {
    private Viaggio viaggio;
    private MssqlViaggioDAO db;
    
    
    public AnnuncioController(Viaggio v) {
    	/* nel costruttore reperisco le info sul viaggio dal db */ 
    	viaggio = v;
    	this.db = new MssqlViaggioDAO();
    }
	public AnnuncioController() {
		this.db = new MssqlViaggioDAO();
		System.out.println("NEW ANNUNCIO CONTROLLER!");
	}
	@Override
	public Utente[] visuallizzaUtentiPartecipanti(Integer idAnnuncio) {
		Utente[] res;
		if(idAnnuncio == null || idAnnuncio <0) {
			throw new IllegalArgumentException();
		}
		res=(Utente[]) viaggio.getPartecipanti().toArray();
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return res;
	}
	public Viaggio getViaggioById(int id) {
		db.setConn(super.getDbConnection());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return db.read(id);
	}
	@Override
	public boolean modificaAnnuncio(Viaggio modificato) {
		/* questo metodo tramite un insert a livello db andr� a modificare i dati del viaggio passato come argomento */
		boolean res=false;
		db.setConn(super.getDbConnection());
		if(db.update(modificato)) {
			viaggio=modificato;
			res=true;
		}
		else res= false;
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
        return res;
	}

	@Override
	public boolean lasciaRecensione(Recensione newRecensione) {
		boolean rec=false; 
		if(newRecensione==null) {
			rec=false;
		}else {
			rec=true;
		}
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,newRecensione.getIdUtenteRecensitore(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return rec;
	}

	@Override
	public boolean abbandonaAnnuncio(Utente utente) {
		boolean res=false; 
		if(utente==null) {
			res=false;
			throw new IllegalArgumentException();
		}else {
			viaggio.getPartecipanti().remove(utente);
			db.setConn(super.getDbConnection());
			db.utenteAbbandonaAnnuncio(utente, this.viaggio);
			res=true;
		}
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,utente.getId(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return res;
	}
	public Viaggio getViaggio() {
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return viaggio;
	}
	public void setViaggio(Viaggio viaggio) {
		this.viaggio = viaggio;
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
	}
	

}
