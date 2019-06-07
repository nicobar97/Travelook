package io.travelook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class RichiesteObservableController extends Observable implements IGestioneRichieste {
	private List<RichiestaDiPartecipazione> listaRichieste;
	public RichiesteObservableController() {
		super();
		this.listaRichieste = new ArrayList<RichiestaDiPartecipazione>();
		//CARICA DA DATABASE LE RICHIESTE DI PARTECIPAZIONE
	}
	public RichiesteObservableController(List<RichiestaDiPartecipazione> listaRichieste) {
		super();
		this.listaRichieste = listaRichieste;
		//CARICA DA DATABASE LE RICHIESTE DI PARTECIPAZIONE
	}
	@Override
	public boolean nuovaRichiesta(RichiestaDiPartecipazione richiesta) {
		aggiungiRichiesta(richiesta);
		super.addObserver((Observer) new NotificheVersoUtente(richiesta.getUtente(), richiesta.getViaggio()));
		notifyCreatore(richiesta.getViaggio(), richiesta.getMessaggioRichiesta());
		return false;
	}
	
	@Override
	public boolean rispondiRichiesta(RichiestaDiPartecipazione richiesta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notifyCreatore(Viaggio viaggio, String messaggio) {
		boolean trovato = false;
		for(Observer o : super.getListObserver()) {
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

	@Override
	public void notifyUtente(Viaggio viaggio, String messaggio, Stato stato, Utente utente) {
		boolean trovato = false;
		for(Observer o : super.getListObserver()) {
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
	@Override
	public void getObserver(int idUtente) {
		// TODO Auto-generated method stub
		
	}
	private void aggiungiRichiesta(RichiestaDiPartecipazione richiesta) {
		this.listaRichieste.add(richiesta);
		//SINCRONIZZAZIONE SU DB
	}
}
