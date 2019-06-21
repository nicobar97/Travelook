package io.travelook.broker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Recensione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class ServerListaAnnunci extends Thread {
	private ListaAnnunciController listaAnnunciController;
	private AnnuncioController annuncioController;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerListaAnnunci(Socket brokerSocket) {
		this.listaAnnunciController = new ListaAnnunciController();
		this.brokerSocket=brokerSocket;
		this.annuncioController = new AnnuncioController();
		
	}
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4001);
			while(true) {
				System.out.println("Il ServerListaAnnunci è in attesa sulla porta " + serverSocket.getLocalPort());
				brokerSocket = serverSocket.accept();
				System.out.println("Richiesta arrivata, lancio thread!");
				ServerListaAnnunci thread = new ServerListaAnnunci(brokerSocket);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		//da registrare sul broker
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
		//da registrare sul broker
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
		//da registrare sul broker
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
		//da registrare sul broker
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
		//da registrare sul broker
		if(servizioRichiesto.equals("getViaggioById")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Integer idViaggio = (Integer) listaArgomenti.get(0);
			Viaggio v = annuncioController.getViaggioById(idViaggio);
			List<Viaggio> listaViaggio = new ArrayList<Viaggio>();
			listaViaggio.add(v);
			Risposta<Viaggio> replyGetViaggioById = new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaViaggio);
			ous.writeObject(replyGetViaggioById);
		}
		//da registrare sul broker e da convertire da List a array nel proxy
		if(servizioRichiesto.equals("visualizzaUtentiPartecipanti")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Integer idViaggio = (Integer) listaArgomenti.get(0);
			Utente[] partecipanti = annuncioController.visuallizzaUtentiPartecipanti(idViaggio);
			List<Utente> listaDaRestituire = new ArrayList<Utente>();
			for(Utente u : partecipanti) {
				listaDaRestituire.add(u);
			}
			Risposta<Utente> replyVisualizzaUtentiPartecipanti = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listaDaRestituire);
			ous.writeObject(replyVisualizzaUtentiPartecipanti);
		}
		brokerSocket.close();
		}
		catch(Exception e) {
		}
		}
	}


