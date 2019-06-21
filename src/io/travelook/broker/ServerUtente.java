package io.travelook.broker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class ServerUtente extends Thread {
	private UtenteController utentec;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerUtente(Socket brokerSocket) {
		this.utentec = new UtenteController();
		this.brokerSocket=brokerSocket;
		
	}
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4003);
			while(true) {
				System.out.println("Il ServerUtente è in attesa sulla porta " + serverSocket.getLocalPort());
				brokerSocket = serverSocket.accept();
				System.out.println("Richiesta arrivata, lancio thread!");
				ServerUtente thread = new ServerUtente(brokerSocket);
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
		System.out.println("ServerUtente: servizio richiesto: "+servizioRichiesto);
		if(servizioRichiesto.equals("getListaUtenti")) {
			List<Utente> listautenti = utentec.getListaUtenti();
			Risposta<Utente> replyUtenti = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listautenti);
			ous.writeObject(replyUtenti);
			//
		   }
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}	
}
