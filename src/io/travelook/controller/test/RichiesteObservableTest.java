package io.travelook.controller.test;

import java.util.ArrayList;
import java.sql.Date;

import io.travelook.controller.RichiesteObservableController;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class RichiesteObservableTest {
	public static void main(String[] args) {
		RichiesteObservableController controller = new RichiesteObservableController();
		Utente u = new Utente(1, "nicobar", "nicobar97@gmail.com", "nicolo", "bartelucci", new Date(new java.util.Date().getTime()), "immagine1");
		Utente c = new Utente(2, "nicsac", "nicolo.saccone.97@gmail.com", "nicolo", "saccone", new Date(new java.util.Date().getTime()), "immagine2");
		Utente a = new Utente(3, "andresalv", "andrea.salvucci@studio.unibo.it", "andrea", "salvucci", new Date(new java.util.Date().getTime()), "immagine2");
		//int idViaggio, String titolo, String destinazione, String lingua, int budget, ArrayList<Utente> partecipanti,
		//String descrizione, String luogoPartenza, Date dataInizio, Date dataFine, Stato stato
		ArrayList<Utente> listUser = new ArrayList<Utente>();
		Viaggio v = new Viaggio(1, u, "Viaggio uno", "Destinazione", "lingua", 2, listUser,
				"descrizione", "roma", new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), Stato.INIZIO );
		System.out.println("nuova richiesta in invio");
		//v.getPartecipanti().add(c);
		RichiestaDiPartecipazione nic = new RichiestaDiPartecipazione(c, v, "Ora vado a vedere se ha caricato in db");
		controller.nuovaRichiesta(nic);
		v.getPartecipanti().add(c);
		System.out.println("nuova richiesta in invio");
		RichiestaDiPartecipazione andre = new RichiestaDiPartecipazione(a, v, "Ora vado a vedere se ha caricato in db");
		controller.nuovaRichiesta(andre);
		v.getPartecipanti().add(a);
		
		nic.setMessaggioRichiesta("Ti ho accettato");
		nic.setStato(Stato.ACCETTATA);
		controller.rispondiRichiesta(nic);
		
		andre.setMessaggioRichiesta("Ti ho rifiuto");
		andre.setStato(Stato.NONACCETTATA);
		controller.rispondiRichiesta(andre);
		
		
	}
}
