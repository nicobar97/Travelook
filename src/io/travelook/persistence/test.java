package io.travelook.persistence;

import io.travelook.controller.RichiesteObservableController;
import io.travelook.controller.UtenteController;
import io.travelook.model.Utente;

public class test {
	
	public static void main(String[]args) {
		/*RichiesteObservableController r = new RichiesteObservableController();
		MssqlRichiestaDiPartecipazioneDAO rdp = new MssqlRichiestaDiPartecipazioneDAO(r.startConnection());
		System.out.println(rdp.createTable() ? "Creata" : "Fallito");*/
		UtenteController u = new UtenteController();
		for(Utente us : u.getListaUtenti()) {
			System.out.println("User + " + us.getUsername());
		}
	}
}
