package io.travelook.controller;

import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Recensione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class AnnuncioController implements IAnnuncio {
    private Viaggio viaggio;
    
    public AnnuncioController() {
    	/* nel costruttore reperisco le info sul viaggio dal db */ 
    	
    }
	@Override
	public Utente[] visuallizzaUtentiPartecipanti(Integer idAnnuncio) {
		Utente[] res;
		if(idAnnuncio == null || idAnnuncio <0) {
			throw new IllegalArgumentException();
		}
		res=(Utente[]) viaggio.getPartecipanti().toArray();
		return res;
	}

	@Override
	public boolean modificaAnnuncio(Viaggio modificaato) {
		/* questo metodo tramite un insert a livello db andrà a modificare i dati del viaggio passato come argomento */
		return false;
	}

	@Override
	public boolean lasciaRecensione(Recensione newRecensione) {
		boolean rec=false; 
		if(newRecensione==null) {
			rec=false;
		}else {
			/*query che inserisce la recensione nell'apposito db 
			 * se non ci sono errori nell'inserimento si aggiorna la variabile boolean a true 
			 * e si inserisce il valore 
			 */
			rec=true;
		}
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
			res=true;
		}
		return res;
	}

}
