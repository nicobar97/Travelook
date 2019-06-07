package io.travelook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

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
		//CARICA DA DATABASE LE RICHIESTE DI PARTECIPAZIONE
	}
	public RichiesteObservableController(List<RichiestaDiPartecipazione> listaRichieste) {
		super();
		this.listaRichieste = listaRichieste;
		this.observers = initObserver(); //implementare agganciaObservers per caricare obs da richdipart
		//CARICA DA DATABASE LE RICHIESTE DI PARTECIPAZIONE
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
	@Override
	public boolean nuovaRichiesta(RichiestaDiPartecipazione richiesta) {
		aggiungiRichiesta(richiesta);
		addObserver((Observer) new NotificheVersoUtente(richiesta.getUtente(), richiesta.getViaggio()));
		notifyCreatore(richiesta.getViaggio(), richiesta.getMessaggioRichiesta());
		return false;
	}
	
	@Override
	public boolean rispondiRichiesta(RichiestaDiPartecipazione richiesta) {
		// TODO Auto-generated method stub
		return false;
	}

	public void notifyCreatore(Viaggio viaggio, String messaggio) {
		boolean trovato = false;
		for(Observer o : getListObserver()) {
			if(trovato == false) {
				NotificheVerso n = (NotificheVerso) o;
				if(n.getViaggio().getIdViaggio() == viaggio.getIdViaggio() && n.getUtente().getId() == viaggio.getIdCreatore()) {
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
				if(n.getViaggio().getIdViaggio() == viaggio.getIdViaggio() /* && se l'utente è nel viaggio */) {
					NotificheVersoUtente nc = (NotificheVersoUtente) n;
					nc.setMessaggio(messaggio);
					nc.setStato(stato);
					nc.update();
				}
			}
		}
		
	}
	public void getObserver(int idUtente) {
		// TODO Auto-generated method stub
		
	}
	private void aggiungiRichiesta(RichiestaDiPartecipazione richiesta) {
		this.listaRichieste.add(richiesta);
		//SINCRONIZZAZIONE SU DB
	}
	private List<Observer> initObserver() {
		// TODO Auto-generated method stub
		return null;
	}
}
