package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.segnalazioni.SegnalazioniController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Segnalazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlSegnalazioniDAO;
import junit.framework.Assert;

class SegnalazioniTest {

	@Test
	/*void testAggiungiSegnalazione() {
		Utente segnalato = new Utente(1, "asalvucci", "andrea@gmail.com", "Andrea", "Salvucci", new Date(1997,11,14) ,"C:/");
		Utente segnalante = new Utente(3, "nbartelucci", "nicolo1@gmail.com", "Nicolo", "Bartelucci", new Date(1997,11,14) ,"C:/");
		Viaggio v = new Viaggio(2, segnalato ,"TITOLO AGGIORNATO", "Montecosaro", "Dialetto Maceratese", 2, "se magna",
				"Macerata", new Date(2019,11,14), new Date(2019,11,17), Stato.INIZIO);
		AnnuncioController ac = new AnnuncioController();
		MssqlSegnalazioniDAO db = new MssqlSegnalazioniDAO(ac.getDbConnection());
		Segnalazione s = new Segnalazione(1, v, segnalato, segnalante, "HA OFFESO MIA MADRE");
		assertTrue(db.create(s));
	}*/
	
	public static void main(String[] args) {
		SegnalazioniController sc =new SegnalazioniController();
		List<Utente> listu=new ArrayList<Utente>();
		List<Segnalazione> lists=new ArrayList<Segnalazione>();
		List<Segnalazione> lists2=new ArrayList<Segnalazione>();
		UtenteController u =new UtenteController();
		listu=u.getListaUtenti();
		Utente u1 =listu.get(0);
		Utente u2 =listu.get(1);
		Segnalazione s = new Segnalazione(1, u1, u2, "Mi ha offeso ",Stato.NONVISTA);
		Segnalazione s2 = new Segnalazione(2, u2, u1, "Mi ha insultato ",Stato.NONVISTA);
		boolean res=sc.segnalaUtente(s);
		boolean res2=sc.segnalaUtente(s2);
		Assert.assertTrue(res);
		Assert.assertTrue(res2);
		lists=sc.getSegnalazioni();
		lists2=sc.getSegnalazioniUtente(u2.getId());
		for(Segnalazione segn : lists) {
			System.out.println("Segnalazione numero : "+segn.getIdSegnalazione());
		}
		for(Segnalazione segn : lists2) {
			System.out.println("Segnalazione numero : "+segn.getIdSegnalazione());
		}
		Segnalazione sletta=sc.leggiSegnalazione(s.getIdSegnalazione());
		System.out.println("test superato!");
	}
}
