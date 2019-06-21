package io.travelook.broker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Viaggio;

public class ServerListaAnnunci extends Thread {
	private ListaAnnunciController listaAnnunciController;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerListaAnnunci(Socket brokerSocket) {
		this.listaAnnunciController = new ListaAnnunciController();
		this.brokerSocket=brokerSocket;
		
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
		brokerSocket.close();
		}
		catch(Exception e) {
		}
		}
	}


