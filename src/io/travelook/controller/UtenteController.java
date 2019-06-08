package io.travelook.controller;

import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class UtenteController implements IGestioneProfiloUtente {
	private Utente utente;
	
	public UtenteController(int idUtente) {
		/**
		 * Nel costruttore reperisco l'utente dal database, immmagino debba essere fatto in questo modo,
		 * ogni volta che ho bisogno di lavorare sul profilo di un utente mi creo un controller con l'id dell'utente,
		 * se no non so come passarglielo, visto e considerato che l'utente è il campo principale
		 */
	}

	@Override
	public boolean aggiungiInteressi(Interessi interesse) {
		// TODO Auto-generated method stub
		/**
		 * cerco se ci sia già lo stesso interesse, in caso positivo non lo aggiungo
		 */
		boolean trovato=false;
		for(Interessi i: utente.getInteressi()) {
			if(i.equals(interesse)) {
				trovato=true;
				break;
			}
		}
		if(!trovato) {
			utente.getInteressi().add(interesse);
			return true;
		}
		else return false;
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
		// TODO Auto-generated method stub
		
		boolean esito= true;
		
		/**
		 * query
		 */
		return esito;
	}

}
