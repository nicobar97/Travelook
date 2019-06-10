package io.travelook.controller.test;

import java.util.ArrayList;
import java.util.Date;

import io.travelook.controller.RichiesteObservableController;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class RichiesteObservableTest {
	public static void main(String[] args) {
		RichiesteObservableController controller = new RichiesteObservableController();
		Utente u = new Utente(1, "nicobar", "nicobar97@gmail.com", "nicolo", "bartelucci", new Date(), "immagine1");
		Utente c = new Utente(2, "nicsac", "nicolo.saccone.97@gmail.com", "nicolo", "saccone", new Date(), "immagine2");
		Utente a = new Utente(3, "andresalv", "andrea.salvucci@studio.unibo.it", "andrea", "salvucci", new Date(), "immagine2");
		//int idViaggio, String titolo, String destinazione, String lingua, int budget, ArrayList<Utente> partecipanti,
		//String descrizione, String luogoPartenza, Date dataInizio, Date dataFine, Stato stato
		ArrayList<Utente> listUser = new ArrayList<Utente>();
		Viaggio v = new Viaggio(1, u, "Viaggio uno", "Destinazione", "lingua", 2, listUser,
				"descrizione", "roma", new Date(), new Date(), Stato.INIZIO );
		System.out.println("nuova richiesta in invio");
		//v.getPartecipanti().add(c);
		
		controller.nuovaRichiesta(new RichiestaDiPartecipazione(c, v, "Ora vado a vedere se ha caricato in db"));
		v.getPartecipanti().add(c);
		System.out.println("nuova richiesta in invio");
		controller.nuovaRichiesta(new RichiestaDiPartecipazione(a, v, "Ora vado a vedere se ha caricato in db"));
		v.getPartecipanti().add(a);
		//controller.nuovaRichiesta(new RichiestaDiPartecipazione(u,v2, "Ora vado a vedere se ha caricato in db"));
	}
}
