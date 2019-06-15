package io.travelook.persistence;

import java.sql.Connection;
import java.util.List;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.rdp.RichiesteObservableController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class test {
	
	public static void main(String[]args) {
		/*RichiesteObservableController r = new RichiesteObservableController();
		MssqlRichiestaDiPartecipazioneDAO rdp = new MssqlRichiestaDiPartecipazioneDAO(r.startConnection());
		System.out.println(rdp.createTable() ? "Creata" : "Fallito");
		UtenteController u = new UtenteController();
		for(Utente us : u.getListaUtenti()) {
			System.out.println("User + " + us.getUsername());*/
		MssqlViaggioDAO db = new MssqlViaggioDAO(new UtenteController().getDbConnection());
		List<Viaggio> list = db.readViaggiListFromDb();
		for(Viaggio v: list) {
			System.out.println(v.getImmaginiProfilo());
		}
	}


}
