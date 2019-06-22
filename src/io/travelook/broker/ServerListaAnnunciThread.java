package io.travelook.broker;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.rdp.RichiesteObservableController;
import io.travelook.model.Recensione;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class ServerListaAnnunciThread extends Thread {
	private static ListaAnnunciController listaAnnunciController = new ListaAnnunciController();
	private static  AnnuncioController annuncioController = new AnnuncioController();
	private static RichiesteObservableController richiesteController = new RichiesteObservableController();
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerListaAnnunciThread(Socket brokerSocket,ListaAnnunciController lac,
			AnnuncioController ac, RichiesteObservableController roc) {
		this.listaAnnunciController=lac;
		this.brokerSocket=brokerSocket;
		this.annuncioController=ac;
		this.richiesteController=roc;	
	}
	
	public void run() {
		try {
	    System.out.println("Sono connesso ad "+brokerSocket.toString());
	    ObjectOutputStream ous = new ObjectOutputStream(brokerSocket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
		System.out.println("Attendo la richiesta di un servizio da "+brokerSocket.toString());
		
		Richiesta<Object> richiestaDaBroker = (Richiesta<Object>) ois.readObject();
		System.out.println("Richiesta di servizio ricevuta da broker " + brokerSocket.toString());
		String servizioRichiesto = richiestaDaBroker.getServizio();
		System.out.println("ServerListaAnnunci: servizio richiesto: "+servizioRichiesto);
		
		/*
		 * SERVIZI
		 */
		if(servizioRichiesto.equals("getListaAnnunci")) {
			List<Viaggio> listaAnnunci = listaAnnunciController.getAnnunci();
			Risposta<Viaggio> replyAnnunci = new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaAnnunci);
			ous.writeObject(replyAnnunci);
			//
		}
		if(servizioRichiesto.equals("creaAnnuncio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Viaggio daCreare = (Viaggio) listaArgomenti.get(0);
			boolean esito = listaAnnunciController.creaAnnuncio(daCreare);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyCreaAnnuncio = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyCreaAnnuncio);
		}
		if(servizioRichiesto.equals("eliminaAnnuncio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Integer idDaEliminare = (Integer) listaArgomenti.get(0);
			boolean esito = listaAnnunciController.eliminaAnnuncio(idDaEliminare);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyCreaAnnuncio = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyCreaAnnuncio);
		}
		if(servizioRichiesto.equals("visualizzaAnnuncio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Integer idAnnuncioDaVisualizzare = (Integer) listaArgomenti.get(0);
			Viaggio v = listaAnnunciController.visualizzaAnnuncio(idAnnuncioDaVisualizzare);
			List<Viaggio> listaViaggio = new ArrayList<Viaggio>();
			listaViaggio.add(v);
			Risposta<Viaggio> replyVisualizzaAnnuncio = new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaViaggio);
			ous.writeObject(replyVisualizzaAnnuncio);
		}
		if(servizioRichiesto.equals("modificaAnnuncio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Viaggio viaggioModificato = (Viaggio) listaArgomenti.get(0);
			boolean esito = annuncioController.modificaAnnuncio(viaggioModificato);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyModificaAnnuncio = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyModificaAnnuncio);
		}
		if(servizioRichiesto.equals("lasciaRecensione")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Recensione daLasciare = (Recensione) listaArgomenti.get(0);
			boolean esito = annuncioController.lasciaRecensione(daLasciare);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyLasciaRecensione = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyLasciaRecensione);
		}
		if(servizioRichiesto.equals("setViaggio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Viaggio daSettare = (Viaggio) listaArgomenti.get(0);
			boolean esito = true;
			annuncioController.setViaggio(daSettare);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replySetViaggio = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replySetViaggio);
		}
		if(servizioRichiesto.equals("abbandonaAnnuncio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Utente daEliminare = (Utente) listaArgomenti.get(0);
			boolean esito = annuncioController.abbandonaAnnuncio(daEliminare);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyAbbandonaAnnuncio = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyAbbandonaAnnuncio);
			
		}
		if(servizioRichiesto.equals("getViaggioById")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Integer idViaggio = (Integer) listaArgomenti.get(0);
			System.out.println("Cerco il viaggio con id " +idViaggio);
			Viaggio v = annuncioController.getViaggioById(idViaggio);
			System.out.println("Viaggio da inviare: "+ v.getTitolo());
			List<Viaggio> listaViaggio = new ArrayList<Viaggio>();
			listaViaggio.add(v);
			Risposta<Viaggio> replyGetViaggioById = new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaViaggio);
			ous.writeObject(replyGetViaggioById);
		}
		if(servizioRichiesto.equals("visualizzaUtentiPartecipanti")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Integer idViaggio = (Integer) listaArgomenti.get(0);
			System.out.println("Utenti che partecipano al viaggio " +idViaggio);
			Utente[] partecipanti = annuncioController.visuallizzaUtentiPartecipanti(idViaggio);
			System.out.println("array ricevuto");
			List<Utente> listaDaRestituire = new ArrayList<Utente>();
			for(Utente u : partecipanti) {
				System.out.println(u.getUsername());
				listaDaRestituire.add(u);
			}
			Risposta<Utente> replyVisualizzaUtentiPartecipanti = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaDaRestituire);
			ous.writeObject(replyVisualizzaUtentiPartecipanti);
		}
		//da registrare sul broker
		if(servizioRichiesto.equals("nuovaRichiesta")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			RichiestaDiPartecipazione daAggiungere = (RichiestaDiPartecipazione) listaArgomenti.get(0);
			boolean esito = richiesteController.nuovaRichiesta(daAggiungere);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyNuovaRichiesta = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyNuovaRichiesta);
		}
		//da registrare sul broker
		if(servizioRichiesto.equals("rispondiRichiesta")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			RichiestaDiPartecipazione rispostaRichiesta = (RichiestaDiPartecipazione) listaArgomenti.get(0);
			boolean esito = richiesteController.rispondiRichiesta(rispostaRichiesta);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyNuovaRichiesta = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyNuovaRichiesta);			
		}
		//da registrare sul broker
		if(servizioRichiesto.equals("getRichiesteForCreatore")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Utente utente = (Utente) listaArgomenti.get(0);
			List<RichiestaDiPartecipazione> listaRichieste = richiesteController.getRichiesteForCreatore(utente);
			Risposta<RichiestaDiPartecipazione> replyRichiesteForCreatore =
					new Risposta<RichiestaDiPartecipazione>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaRichieste);
			ous.writeObject(replyRichiesteForCreatore);
		}
		if(servizioRichiesto.equals("getRichiesteForCreatoreViaggio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Utente utente = (Utente) listaArgomenti.get(0);
			Viaggio v = (Viaggio) listaArgomenti.get(1);
			List<RichiestaDiPartecipazione> listaRichieste = richiesteController.getRichiesteForCreatoreViaggio(utente,v);
			System.out.println("Numero richieste: "+listaRichieste.size());
			Risposta<RichiestaDiPartecipazione> replyRichiesteForCreatore =
					new Risposta<RichiestaDiPartecipazione>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaRichieste);
			ous.writeObject(replyRichiesteForCreatore);
		}
		brokerSocket.close();
		}
		catch(Exception e) {
		}
		}
	}


