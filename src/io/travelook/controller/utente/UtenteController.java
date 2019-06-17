package io.travelook.controller.utente;

import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlUtenteDAO;
import io.travelook.persistence.MssqlUtente_InteressiDAO;

public class UtenteController extends Controller implements IGestioneProfiloUtente {
	private Utente u;
	private MssqlUtenteDAO db;
	private MssqlUtente_InteressiDAO interessi;
	public UtenteController(Utente u) {
		/**
		 * Nel costruttore reperisco l'utente dal database, immmagino debba essere fatto in questo modo,
		 * ogni volta che ho bisogno di lavorare sul profilo di un utente mi creo un controller con l'id dell'utente,
		 * se no non so come passarglielo, visto e considerato che l'utente � il campo principale
		 */
		
		// = new ArrayList<Utente>();
		db = new MssqlUtenteDAO();
		interessi = new MssqlUtente_InteressiDAO();
		//listaUtenti = db.readUtentiFromDB();
		//((if(listaUtenti == null)
		//	listaUtenti = new ArrayList<Utente>();
		this.u=u;
		
	}

	public UtenteController() {
		db = new MssqlUtenteDAO();
		interessi = new MssqlUtente_InteressiDAO();
	}

	@Override
	public boolean aggiungiInteressi(Interessi interesse) {
		// TODO Auto-generated method stub
		//
		/**
		 * prima trovo l'utente
		 */
//		Utente trovatoUtente = null;
//		int idUtente = utente.getId();
//		
//		for(Utente u: listaUtenti) {
//			if(u.getId()==idUtente)
//				trovatoUtente = u;
//		}
//		
//		if(trovatoUtente==null) {
//			System.out.println("non ho trovato l'utente con id " + idUtente);
//			return false;
//			
//		}
			
		/**
		 * cerco se ci sia gi� lo stesso interesse, in caso positivo non lo aggiungo
		 */
		boolean trovatoInteresse=false;
		for(Interessi i: u.getInteressi()) {
			if(i.equals(interesse)) {
				trovatoInteresse=true;
				break;
			}
		}
		if(!trovatoInteresse) {
			u.getInteressi().add(interesse);
			System.out.println("aggiungo interesse " + interesse.toString()+ " all'utente " + u.getUsername());
			interessi.create(u, interesse);
			return true;
		}
		else {
			System.out.println("Non posso aggiungere l'interesse "+interesse.toString()+ " all'utente "+ u.getUsername()+
					" perch� c'� gi�");
			return false;
		}
	}
	public int getIdUtenteFromUsername(String username) {
		db.setConn(super.getDbConnection());
		return db.getIdUtenteByUsername(username);
	}
	@Override
	public Utente visualizzaProfilo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Storico visualizzaStorico() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recensione> visualizzaRecensioni() {
		// TODO Auto-generated method stub
		List<Recensione> lista = new ArrayList<Recensione>();
		return null;
	}

	@Override
	public void modificaProfilo() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Viaggio> getViaggiInPartecipazione() {
		List<Viaggio> listaViaggiPartecipante = new ArrayList<Viaggio>();
		db.setConn(super.getDbConnection());
		listaViaggiPartecipante = db.readViaggiAttiviByUtente(u);
		/* ATTENZIONE QUALE UTENTE????
		 * nel controller c'� una LISTA DI UTENTI
		 */
		
		return listaViaggiPartecipante;
	}

	@Override
	public List<Viaggio> getViaggiInAttesaDiConferma() {
		// TODO Auto-generated method stub
		List<Viaggio> viaggiInAttesaDiConferma = new ArrayList<Viaggio>();
		db.setConn(super.getDbConnection());
		viaggiInAttesaDiConferma = db.readViaggiInAttesaDiConfermaUtente(u);
		/*
		 * STESSO DISCORSO!
		 */
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
