package io.travelook.controller.rdp;

import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlRichiestaDiPartecipazioneDAO;

public class RichiesteObservableController extends Controller implements IGestioneRichieste {
	private List<RichiestaDiPartecipazione> listaRichieste;
	private List<Observer> observers;
	private MssqlRichiestaDiPartecipazioneDAO db;
	public RichiesteObservableController() {
		super();
		this.observers = new ArrayList<Observer>();
		this.db = new MssqlRichiestaDiPartecipazioneDAO(super.getDbConnection());
		this.listaRichieste = db.readRDPListFromDb();
		if(this.listaRichieste == null)
			this.listaRichieste = new ArrayList<RichiestaDiPartecipazione>();
		initObservers();
	}
	
	@Override
	public boolean nuovaRichiesta(RichiestaDiPartecipazione richiesta) {
		boolean continu = addRichiesta(richiesta);
		if(continu) {
			addObserver((Observer) new NotificheVersoCreatore(richiesta.getUtente(), richiesta.getViaggio()));
			notifyCreatore(richiesta.getViaggio(), richiesta.getMessaggioRichiesta(), richiesta.getUtente());
			addObserver((Observer) new NotificheVersoUtente(richiesta.getViaggio().getCreatore(), richiesta.getViaggio()));
			return true;
		}
		return false;
	}
	@Override
	public boolean rispondiRichiesta(RichiestaDiPartecipazione richiestaRisposta) {
		for(RichiestaDiPartecipazione r : this.listaRichieste) {
			if(r.getUtente().getId() == richiestaRisposta.getUtente().getId() &&
				r.getViaggio().getIdViaggio() == richiestaRisposta.getViaggio().getIdViaggio()) {
				updateRichiesta(richiestaRisposta);
				notifyUtente(richiestaRisposta.getViaggio(), richiestaRisposta.getMessaggioRisposta(), 
						richiestaRisposta.getStato(), richiestaRisposta.getUtente());
			}
		}
		return false;
	}
	
	public void addObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException();
        boolean trovato = false;
        for(Observer o : observers) {
        	NotificheVerso n = (NotificheVerso) o;
        	NotificheVerso nuovo = (NotificheVerso) observer;
        	if(n.getUtente().getId() == nuovo.getUtente().getId())
        		trovato = true;
        }
        if (!trovato) {
        	this.observers.add(observer);
        }
    }
    public void removeObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException();
        boolean trovato = false;
        for(Observer o : observers) {
        	NotificheVerso n = (NotificheVerso) o;
        	NotificheVerso del = (NotificheVerso) observer;
        	if(n.getUtente().getId() == del.getUtente().getId())
        		trovato = true;
        }
        if (trovato) {
        	this.observers.remove(observer);
        }
    }
    public List<Observer> getListObserver() {
    	initObservers();
    	return this.observers;
    }	
	public void notifyCreatore(Viaggio viaggio, String messaggio, Utente sender) {
		boolean trovato = false;
		/*boolean senderIsInViaggio = false;
		System.out.println("LISTA DIMENSIONE: " + this.getListObserver().size());
		for(Utente u : viaggio.getPartecipanti())
			if(u.getId() == sender.getId())
				senderIsInViaggio = true;*/
		for(Observer o : getListObserver()) {
			if(trovato == false) {
				NotificheVerso n = (NotificheVerso) o;		
				if(n.getViaggio().getIdViaggio() == viaggio.getIdViaggio() && 
						n.getViaggio().getCreatore().getId() == viaggio.getCreatore().getId() && sender.getId() == n.getUtente().getId()) {
					if(n instanceof NotificheVersoCreatore) {
						NotificheVersoCreatore nc = (NotificheVersoCreatore) n;
						nc.setMessaggio(messaggio);
						nc.update();
					}
				}
			}
		}
		
	}
	public void notifyUtente(Viaggio viaggio, String messaggio, Stato stato, Utente utente) {
		boolean trovato = false;
		for(Observer o : getListObserver()) {
			if(trovato == false) {
				NotificheVerso n = (NotificheVerso) o;
				if(n.getViaggio().getIdViaggio() == viaggio.getIdViaggio() && viaggio.getPartecipanti().contains(utente)) {
					NotificheVersoUtente nc = (NotificheVersoUtente) n;
					nc.setMessaggio(messaggio);
					nc.setStato(stato);
					nc.update();
				}
			}
		}
	}
	public Observer getObserver(int idUtente) {
		for(Observer o : this.observers) {
			NotificheVerso n = (NotificheVerso) o;
			if(n.getUtente().getId() == idUtente)
				return o;
		}
		return null;
	}
	private boolean addRichiesta(RichiestaDiPartecipazione richiesta) {
		this.listaRichieste.add(richiesta);
		boolean esito = true;
		for(int i=0; i<listaRichieste.size(); i++)
			if(listaRichieste.get(i).getId()== richiesta.getId())
				esito = false;
		if(esito) {
			this.db.setConn(super.getDbConnection());
			this.db.create(richiesta);
		}
		return true;
	}
	private boolean removeRichiesta(RichiestaDiPartecipazione richiesta) {
		boolean esito = false;
		for(int i=0; i<listaRichieste.size(); i++) {
			if(listaRichieste.get(i).getId()== richiesta.getId()) {
				listaRichieste.remove(i);
				esito = true;
			}
		}
		if(esito) {
			this.db.setConn(super.getDbConnection());
			this.db.delete(richiesta.getId());
		}
		return esito;
	}
	private boolean updateRichiesta(RichiestaDiPartecipazione richiesta) {
		boolean esito = false;
		for(int i=0; i<listaRichieste.size(); i++) {
			if(listaRichieste.get(i).getId()== richiesta.getId()) {
				listaRichieste.remove(i);
				listaRichieste.add(richiesta);
				esito = true;
			}
		}
		this.db.setConn(super.getDbConnection());
		this.db.update(richiesta);
		return esito;
	}
	private void initObservers() {
		this.observers = new ArrayList<Observer>();
		for(RichiestaDiPartecipazione r : this.listaRichieste) {
			if(r.getStato().compareTo(Stato.NONVISTA) == 0) {
				addObserver((Observer) new NotificheVersoCreatore(r.getUtente(), r.getViaggio(), r.getMessaggioRichiesta()));
				addObserver((Observer) new NotificheVersoUtente(r.getUtente(), r.getViaggio()));
			}
			else {
				addObserver((Observer) new NotificheVersoCreatore(r.getUtente(), r.getViaggio(), r.getMessaggioRichiesta()));
				addObserver((Observer) new NotificheVersoUtente(r.getUtente(), r.getViaggio(), r.getMessaggioRisposta(), r.getStato()));
			}
		}
	}
}
