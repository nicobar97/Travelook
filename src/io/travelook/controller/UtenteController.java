package io.travelook.controller;

import java.util.ArrayList;
import java.util.List;
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlUtenteDAO;

public class UtenteController extends Controller implements IGestioneProfiloUtente {
	private List<Utente> listaUtenti;
	private MssqlUtenteDAO db;
	public UtenteController() {
		/**
		 * Nel costruttore reperisco l'utente dal database, immmagino debba essere fatto in questo modo,
		 * ogni volta che ho bisogno di lavorare sul profilo di un utente mi creo un controller con l'id dell'utente,
		 * se no non so come passarglielo, visto e considerato che l'utente � il campo principale
		 */
		
		// = new ArrayList<Utente>();
		db = new MssqlUtenteDAO(super.getDbConnection());
		listaUtenti = db.readUtentiFromDB();
		if(listaUtenti == null)
			listaUtenti = new ArrayList<Utente>();
		
	}

	@Override
	public boolean aggiungiInteressi(Interessi interesse, Utente utente) {
		// TODO Auto-generated method stub
		
		/**
		 * prima trovo l'utente
		 */
		Utente trovatoUtente = null;
		int idUtente = utente.getId();
		
		for(Utente u: listaUtenti) {
			if(u.getId()==idUtente)
				trovatoUtente = u;
		}
		
		if(trovatoUtente==null) {
			System.out.println("non ho trovato l'utente con id " + idUtente);
			return false;
			
		}
			
		/**
		 * cerco se ci sia gi� lo stesso interesse, in caso positivo non lo aggiungo
		 */
		boolean trovatoInteresse=false;
		for(Interessi i: trovatoUtente.getInteressi()) {
			if(i.equals(interesse)) {
				trovatoInteresse=true;
				break;
			}
		}
		if(!trovatoInteresse) {
			trovatoUtente.getInteressi().add(interesse);
			System.out.println("aggiungo interesse " + interesse.toString()+ " all'utente " + trovatoUtente.getUsername());
			return true;
		}
		else {
			System.out.println("Non posso aggiungere l'interesse "+interesse.toString()+ " all'utente "+ trovatoUtente.getUsername()+
					" perch� c'� gi�");
			return false;
		}
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
		return null;
	}

	@Override
	public void modificaProfilo() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Viaggio> getViaggiInPartecipazione() {
		List<Viaggio> listaViaggiPartecipante = new ArrayList<Viaggio>();
		
		/**
		 * anche qui va fatta la query al database
		 */
		
		return listaViaggiPartecipante;
	}

	@Override
	public List<Viaggio> getViaggiInAttesaDiConferma() {
		// TODO Auto-generated method stub
		List<Viaggio> viaggiInAttesaDiConferma = new ArrayList<Viaggio>();
		
		/**
		 * query
		 */
		
		return viaggiInAttesaDiConferma;
	}

	@Override
	public boolean eliminaUtente(Utente utente) {
		boolean ok = true;
		if(this.listaUtenti.contains(utente))
			this.listaUtenti.remove(utente);
		else
			return ok = false;
		return ok;
	}
	public boolean aggiungiUtente(Utente u) {
		boolean ok = true;
		if(this.listaUtenti.contains(u))
			ok = false;//se lo contiene aggiornalo
		else
			this.listaUtenti.add(u);
		return ok;
	}
	public List<Utente> getListaUtenti() {
		db = new MssqlUtenteDAO(super.getDbConnection());
		listaUtenti = db.readUtentiFromDB();
		return this.listaUtenti;
	}
}
