package io.travelook.persistence;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.chat.ChatController;
import io.travelook.controller.rdp.RichiesteObservableController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
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
			System.out.println("User + " + us.getUsername());
		MssqlViaggioDAO db = new MssqlViaggioDAO(new UtenteController().getDbConnection());
		List<Viaggio> list = db.readViaggiListFromDb();
		for(Viaggio v: list) {
			System.out.println(v.getImmaginiProfilo());
		}*/
		/*Timestamp ts = new Timestamp(System.currentTimeMillis());
		Messaggio m = new Messaggio();
		Utente u = new Utente();
		u.setId(3);
		m.setUtente(u);
		m.setMessaggio("primo mex");
		m.setTimestamp(ts);
		ChatController c = new ChatController();
		Viaggio v = new Viaggio();
		v.setIdViaggio(2);
		c.inviaMessaggio(m, v);
		Chat chatte = c.getChat(v);
		System.out.println(chatte.getChat().size());*/
		System.out.println(new MssqlUtenteDAO(new UtenteController().getDbConnection()).getIdUtenteByUsername("andresalv"));
	}


}
