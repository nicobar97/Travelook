package io.travelook.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class UtenteController implements IGestioneProfiloUtente {
	private List<Utente> listaUtenti;
	
	public UtenteController() {
		/**
		 * Nel costruttore reperisco l'utente dal database, immmagino debba essere fatto in questo modo,
		 * ogni volta che ho bisogno di lavorare sul profilo di un utente mi creo un controller con l'id dell'utente,
		 * se no non so come passarglielo, visto e considerato che l'utente è il campo principale
		 */
		
		listaUtenti = new ArrayList<Utente>();
		
		//utent mock per ora, bisogna caricare da db
		Utente utente1 = new Utente(1, "asalvucci", "andrea@gmail.com", "Andrea", "Salvucci", new Date(1997,11,14) ,"C:/");
		Utente utente2 = new Utente(2, "nbartelucci", "nicolo1@gmail.com", "Nicolo", "Bartelucci", new Date(1997,11,14) ,"C:/");
		Utente utente3 = new Utente(3, "nsaccone", "nicolo2@gmail.com", "Nicolo", "Saccone", new Date(1997,11,14) ,"C:/");
		listaUtenti.add(utente1);
		listaUtenti.add(utente2);
		listaUtenti.add(utente3);
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
		 * cerco se ci sia già lo stesso interesse, in caso positivo non lo aggiungo
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
					" perché c'è già");
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
		// TODO Auto-generated method stub
		
		boolean esito= true;
		
		/**
		 * query
		 */
		return esito;
	}

}
