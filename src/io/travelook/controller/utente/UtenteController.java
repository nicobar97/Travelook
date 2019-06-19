package io.travelook.controller.utente;

import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.Controller;
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
		boolean trovatoInteresse=false;
		for(Interessi i: u.getInteressi()) {
			if(i.equals(interesse)) {
				trovatoInteresse=true;
				break;
			}
		}
		if(!trovatoInteresse) {
			u.getInteressi().add(interesse);
			interessi.create(u, interesse);
			return true;
		}
		else {
			return false;
		}
	}
	public int getIdUtenteFromUsername(String username) {
		db.setConn(super.getDbConnection());
		return db.getIdUtenteByUsername(username);
	}

	@Override
	public Storico visualizzaStorico() {
		//
		return db.getStorico(u);
	}

	@Override
	public List<Recensione> visualizzaRecensioni() {
		List<Recensione> lista = null;
		db.setConn(super.getDbConnection());
		lista = dbrec.readRecensioniUtente(u);
		return lista;
	}

	@Override
	public void modificaProfilo(Utente u) {
		if(u!=null) {
			db.update(u);
		}
	}

	@Override
	public List<Viaggio> getViaggiInPartecipazione() {
		List<Viaggio> listaViaggiPartecipante = new ArrayList<Viaggio>();
		db.setConn(super.getDbConnection());
		listaViaggiPartecipante = db.readViaggiAttiviByUtente(u);
		return listaViaggiPartecipante;
	}

	@Override
	public List<Viaggio> getViaggiInAttesaDiConferma() {
		List<Viaggio> viaggiInAttesaDiConferma = new ArrayList<Viaggio>();
		db.setConn(super.getDbConnection());
		viaggiInAttesaDiConferma = db.readViaggiInAttesaDiConfermaUtente(u);
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
		return ok;
	}

	public List<Utente> getListaUtenti() {
		db.setConn(super.getDbConnection());
		List<Utente> listaUtenti = db.readUtentiFromDB();
		return listaUtenti;
	}
	public Utente getUtenteById(int id ) {
		db.setConn(super.getDbConnection());
		return db.read(id);
	}

	public Utente getU() {
		return u;
	}

	public void setU(Utente u) {
		this.u = u;
	}
	
}
