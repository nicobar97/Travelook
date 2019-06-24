package io.travelook.controller.utente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Entry;
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlRecensioniDAO;
import io.travelook.persistence.MssqlUtenteDAO;
import io.travelook.persistence.MssqlUtente_InteressiDAO;

public class UtenteController extends Controller implements IGestioneProfiloUtente {
	private Utente u;
	private MssqlUtenteDAO db;
	private MssqlUtente_InteressiDAO interessi;
	private MssqlRecensioniDAO dbrec;
	public UtenteController(Utente u) {
		db = new MssqlUtenteDAO();
		interessi = new MssqlUtente_InteressiDAO();
		dbrec = new MssqlRecensioniDAO();
		this.u=u;
		
	}

	public UtenteController() {
		db = new MssqlUtenteDAO();
		interessi = new MssqlUtente_InteressiDAO();
		dbrec = new MssqlRecensioniDAO();
	}

	@Override
	public boolean aggiungiInteressi(Interessi interesse) {
//		boolean trovatoInteresse=false;
//		for(Interessi i: u.getInteressi()) {
//			if(i.equals(interesse)) {
//				trovatoInteresse=true;
//				break;
//			}
//		}
//		if(!trovatoInteresse) {
			//u.getInteressi().add(interesse);
			interessi.setConn(super.getDbConnection());
			interessi.create(u, interesse);
			Date d=new Date();
	        super.openWriterLog("hello2.txt");
	        int ide=super.getLogcount()+1;
	        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
	        Entry e=new Entry(ide,1,d,nameofmeth," ");
	        super.scriviOperazioneLog(e);
			return true;
//		}
//		else {
//			return false;
//		}
	}
	public int getIdUtenteFromUsername(String username) {
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," username "+username);
        super.scriviOperazioneLog(e);
        db.setConn(super.getDbConnection());
		return db.getIdUtenteByUsername(username);
	}

	@Override
	public Storico visualizzaStorico() {
		db.setConn(this.getDbConnection());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return db.getStorico(u);
	}

	@Override
	public List<Recensione> visualizzaRecensioni() {
		List<Recensione> lista = null;
		dbrec.setConn(super.getDbConnection());
		lista = dbrec.readRecensioniUtente(u);
		for(Recensione r : lista)
			System.out.println(r.getCorpo());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return lista;
	}

	@Override
	public boolean modificaProfilo(Utente u) {
		boolean res=false;
		if(u!=null) {
			db.setConn(super.getDbConnection());
			db.update(u);
			res=true;
		}
		else{
			res=false;
		}
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,u.getId(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return res;
	}

	@Override
	public List<Viaggio> getViaggiInPartecipazione() {
		List<Viaggio> listaViaggiPartecipante = new ArrayList<Viaggio>();
		db.setConn(super.getDbConnection());
		listaViaggiPartecipante = db.readViaggiAttiviByUtente(u);
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return listaViaggiPartecipante;
	}
	public Utente attachInteressiToUser(Utente user) {
		interessi.setConn(super.getDbConnection());
		user.setInteressi(interessi.readInteressiByUtente(user));
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,user.getId(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return user;
		
	}
	@Override
	public List<Viaggio> getViaggiInAttesaDiConferma() {
		List<Viaggio> viaggiInAttesaDiConferma = new ArrayList<Viaggio>();
		db.setConn(super.getDbConnection());
		viaggiInAttesaDiConferma = db.readViaggiInAttesaDiConfermaUtente(u);
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return viaggiInAttesaDiConferma;
	}

	@Override
	public boolean eliminaUtente() {
		boolean ok = false;
		db.setConn(super.getDbConnection());
		if(db.read(u.getId()) != null) {
			db.setConn(super.getDbConnection());
			db.delete(u.getId());
			u = null;
			ok = true;
		}
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return ok;
	}

	public List<Utente> getListaUtenti() {
		db.setConn(super.getDbConnection());
		List<Utente> listaUtenti = db.readUtentiFromDB();
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return listaUtenti;
	}
	public Utente getUtenteById(int id ) {
		db.setConn(super.getDbConnection());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,id,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return db.read(id);
	}

	public Utente getU() {
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return u;
	}

	public void setU(Utente u) {
		this.u = u;
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,u.getId(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
	}
	public boolean lasciaRecensione(Recensione r) {
		dbrec.setConn(super.getDbConnection());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,r.getIdUtenteRecensitore(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return dbrec.create(r);
	}
	public boolean aggiornaRecensione(Recensione r) {
		dbrec.setConn(super.getDbConnection());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,r.getIdUtenteRecensitore(),d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		return dbrec.update(r);	
	}
}
