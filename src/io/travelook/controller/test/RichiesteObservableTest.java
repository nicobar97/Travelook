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
		Utente u = new Utente(1, "nicobar", "email1@dom.it", "nicolo", "bartelucci", new Date(), "immagine1");
		Utente c = new Utente(2, "nicsac", "nicolo.saccone.97@gmail.com", "nicolo", "saccone", new Date(), "immagine2");
		//int idViaggio, String titolo, String destinazione, String lingua, int budget, ArrayList<Utente> partecipanti,
		//String descrizione, String luogoPartenza, Date dataInizio, Date dataFine, Stato stato
		ArrayList<Utente> listUser = new ArrayList<Utente>();
		listUser.add(u);
		listUser.add(c);
		Viaggio v = new Viaggio(1, c, "Viaggio uno", "Destinazione", "lingua", 2, listUser,
				"descrizione", "roma", new Date(), new Date(), Stato.INIZIO );
		System.out.println("nuova richiesta in invio");
		controller.nuovaRichiesta(new RichiestaDiPartecipazione(u, v, "Voglio partecipare fra"));
	}
}
