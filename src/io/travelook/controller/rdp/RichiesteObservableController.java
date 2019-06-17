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
		this.db = new MssqlRichiestaDiPartecipazioneDAO();
		this.db.setConn(super.getDbConnection());
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
			addObserver((Observer) new NotificheVersoUtente(richiesta.getViaggio().getCreatore(), richiesta.getViaggio(), richiesta.getStato()));
			return true;
		}
		return false;
	}
	@Override
	public boolean rispondiRichiesta(RichiestaDiPartecipazione richiestaRisposta) {
		for(RichiestaDiPartecipazione r : this.listaRichieste) {
			if(r.getId() == richiestaRisposta.getId()) {
				boolean esito = updateRichiesta(richiestaRisposta);
				if(esito)
					notifyUtente(richiestaRisposta);
				else
					System.out.println("PORCODDIO");
			}
		}
		return true;
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
        	System.out.println("Aggiunto obs:" + observer.toString());
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
    	//initObservers();
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
	public void notifyUtente(RichiestaDiPartecipazione rdp) {
		boolean trovato = false;
		for(Observer o : getListObserver()) {
			System.out.println(o.toString());
			if(trovato == false) {
				NotificheVerso n = (NotificheVerso) o;
				System.out.println(n.getUtente().getUsername() + " " + n.getViaggio().getTitolo());
				if(n.getViaggio().getIdViaggio() == rdp.getViaggio().getIdViaggio() && 
						n.getUtente().getId() == rdp.getUtente().getId() ) {
					NotificheVersoUtente nc = null;
					if(n instanceof NotificheVersoUtente) {
						nc = (NotificheVersoUtente) n;
						
						if(nc.getStato().compareTo(Stato.NONVISTA) == 0) {
							nc.setMessaggio(rdp.getMessaggioRisposta());
							nc.setStato(rdp.getStato());
							nc.update();
						}
					}
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
		
		boolean esito = true;
		for(int i=0; i<listaRichieste.size(); i++)
			if(listaRichieste.get(i).getId()== richiesta.getId())
				esito = false;
		if(esito) {
			this.db.setConn(super.getDbConnection());
			this.db.create(richiesta);
			this.listaRichieste.add(richiesta);
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
				//addObserver((Observer) new NotificheVersoCreatore(r.getViaggio().getCreatore(), r.getViaggio(), r.getMessaggioRichiesta()));
				addObserver((Observer) new NotificheVersoUtente(r.getUtente(), r.getViaggio(), r.getStato()));
				
			}
			else {
				//addObserver((Observer) new NotificheVersoCreatore(r.getUtente(), r.getViaggio(), r.getMessaggioRichiesta()));
				//addObserver((Observer) new NotificheVersoUtente(r.getUtente(), r.getViaggio(), r.getMessaggioRisposta(), r.getStato()));
			}
		}
	}
	public List<RichiestaDiPartecipazione> getRichiesteForCreatore(Utente c) {
		this.db.setConn(super.getDbConnection());
		return db.readRDPForCreatore(c);
	}
	public List<RichiestaDiPartecipazione> getRichiesteForCreatoreViaggio(Utente c, Viaggio v) {
		this.db.setConn(super.getDbConnection());
		return db.readRDPForCreatoreViaggio(c, v);
	}
}
