package io.travelook.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class RichiesteObservableController extends Controller implements IGestioneRichieste {
	private List<RichiestaDiPartecipazione> listaRichieste;
	private List<Observer> observers;
	
	public RichiesteObservableController() {
		super();
		this.listaRichieste = new ArrayList<RichiestaDiPartecipazione>();
		this.observers = new ArrayList<Observer>();
		initRDP();
	}
	private void initRDP() {
		Connection conn = super.getDbConnection();
		
	}
	public RichiesteObservableController(List<RichiestaDiPartecipazione> listaRichieste) {
		super();
		this.listaRichieste = listaRichieste;
		initObserver(); //implementare agganciaObservers per caricare obs da richdipart
		//CARICA DA DATABASE LE RICHIESTE DI PARTECIPAZIONE
	}
	
	@Override
	public boolean nuovaRichiesta(RichiestaDiPartecipazione richiesta) {
		addRichiesta(richiesta);
		addObserver((NotificheVerso) new NotificheVersoCreatore(richiesta.getViaggio().getCreatore(), richiesta.getViaggio()));
		addObserver((NotificheVerso) new NotificheVersoUtente(richiesta.getUtente(), richiesta.getViaggio()));
		notifyCreatore(richiesta.getViaggio(), richiesta.getMessaggioRichiesta());
		return false;
	}
	@Override
	public boolean rispondiRichiesta(RichiestaDiPartecipazione richiestaRisposta) {
		for(RichiestaDiPartecipazione r : this.listaRichieste) {
			if(r.getUtente().getId() == richiestaRisposta.getUtente().getId() &&
				r.getViaggio().getIdViaggio() == richiestaRisposta.getViaggio().getIdViaggio()) {
				removeRichiesta(r);
				addRichiesta(richiestaRisposta);
				notifyUtente(richiestaRisposta.getViaggio(), richiestaRisposta.getMessaggioRisposta(), 
						richiestaRisposta.getStato(), richiestaRisposta.getUtente());
			}
		}
		return false;
	}
	
	public void addObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException();
        if (!observers.contains(observer)) {
        	this.observers.add(observer);
        }
    }
    public void removeObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException();
        if (observers.contains(observer)) {
        	this.observers.remove(observer);
        }
    }
    public List<Observer> getListObserver() {
    	return this.observers;
    }	
	public void notifyCreatore(Viaggio viaggio, String messaggio) {
		boolean trovato = false;
		for(Observer o : getListObserver()) {
			if(trovato == false) {
				NotificheVerso n = (NotificheVerso) o;		
				if(n.getViaggio().getIdViaggio() == viaggio.getIdViaggio() && n.getUtente().getId() == viaggio.getCreatore().getId()) {
					NotificheVersoCreatore nc = (NotificheVersoCreatore) n;
					nc.setMessaggio(messaggio);
					nc.update();
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
		//SINCRONIZZAZIONE SU DB
		return true;
	}
	private boolean removeRichiesta(RichiestaDiPartecipazione richiesta) {
		boolean esito = false;
		if(this.listaRichieste.contains(richiesta)) {
			this.listaRichieste.remove(richiesta);
			esito = true;
		}
		//SINCRONIZZAZIONE SU DB
		return esito;
	}
	private void initObserver() {
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
