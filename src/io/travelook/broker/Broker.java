package io.travelook.broker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.sun.javafx.collections.MappingChange.Map;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Viaggio;
public class Broker extends Thread {
	private List<EndPointServer> listaServer;
	private HashMap<String, EndPointServer> serviziServer;
	private Socket clientSocket;
	private Socket serverSocket;
	private final static String IP_SERVER_LISTA_ANNUNCI = "localhost";
	private final static String IP_SERVER_UTENTE = "localhost";
	private final int PORTA_SERVER_LISTA_ANNUNCI = 4001;
	private final int PORTA_SERVER_UTENTE=4003;
	

	
	
	public Broker(Socket s) {
		this.clientSocket = s;
		EndPointServer serverListaAnnunci = new EndPointServer("serverListaAnnunci", "localhost", 4001);
		EndPointServer serverLogin = new EndPointServer("serverLogin", "localhost", 4002);
		EndPointServer epsutente=new EndPointServer("serverUtente",IP_SERVER_UTENTE,PORTA_SERVER_UTENTE);
		serviziServer = new HashMap<String,EndPointServer>();
		serviziServer.put("getListaAnnunci",serverListaAnnunci);
		serviziServer.put("creaAnnuncio",serverListaAnnunci);
		serviziServer.put("eliminaAnnuncio",serverListaAnnunci);
		serviziServer.put("visualizzaAnnuncio",serverListaAnnunci);
		serviziServer.put("modificaAnnuncio",serverListaAnnunci);
		serviziServer.put("lasciaRecensione",serverListaAnnunci);
		serviziServer.put("setViaggio",serverListaAnnunci);
		serviziServer.put("abbandonaAnnuncio",serverListaAnnunci);
		serviziServer.put("getViaggioById",serverListaAnnunci);
		serviziServer.put("visualizzaUtentiPartecipanti",serverListaAnnunci);
		serviziServer.put("verificaCredenziali", serverLogin);
		serviziServer.put("getListaUtenti", epsutente);
		serviziServer.put("visualizzaRecensioni", epsutente);
		serviziServer.put("getViaggiinPartecipazione", epsutente);
		serviziServer.put("getViaggiinAttesadiConferma", epsutente);
		serviziServer.put("aggiungiInteresse", epsutente);
		serviziServer.put("eliminaUtente", epsutente);
		serviziServer.put("visualizzaStorico", epsutente);
		serviziServer.put("aggiornaRecensione", epsutente);
		serviziServer.put("modificaProfilo", epsutente);
		serviziServer.put("attachInteressitoUser", epsutente);
		serviziServer.put("getUtenteById", epsutente);
		serviziServer.put("getUtente", epsutente);
		serviziServer.put("setUtente", epsutente);
		serviziServer.put("getIdUtenteFromUsername", epsutente);		
	}
	
	public void run() {
		System.out.println("Thread Lanciato per il cliente " + clientSocket.toString());
		AnnuncioController ac = new AnnuncioController();
		ListaAnnunciController lac = new ListaAnnunciController();
		try {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
		ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
		
		Richiesta<Object> richiesta = (Richiesta<Object>)ois.readObject();
		String servizio = richiesta.getServizio();
		System.out.println("Ricevuta Richiesta da " + clientSocket.getInetAddress() + " per il servizio "+ servizio);
		
		Socket servizioSocket = new Socket(serviziServer.get(servizio).getIpserver(),serviziServer.get(servizio).getPort());
		System.out.println("Broker: Invio la richiesta al server...");
		Risposta<Object> replyFromBroker = dispatch(richiesta, servizioSocket); //questa è la risposta che torna AL broker DAL server
		System.out.println("Broker: Ricevuta Risposta dal Server!");
		oos.writeObject(replyFromBroker); // questa è la risposta che torna al cliente tramite la sua socket
		System.out.println("Broker: risposta inviata al Cliente!");
		
		}
		catch(Exception e) {
			
		}
			
			
		}
	
	public Risposta<Object> dispatch(Richiesta<Object> req, Socket s) throws IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		out.writeObject(req);
		System.out.println("Broker: mandata la richiesta al server " +s.toString());
		return (Risposta<Object>) in.readObject();	
	}
		
		
	
	
	public static void main(String[] args) throws Exception {
		
		
			AnnuncioController ac = new AnnuncioController();
			ListaAnnunciController lac = new ListaAnnunciController();
		
			ServerSocket sSock = new ServerSocket(4000);
			System.out.println("Il Broker è in attesa sulla porta 4000.");
			while(true) {
			Socket socket = sSock.accept();
			System.out.println("Arrivata richiesta, lancio thread");
			Broker thread = new Broker(socket);
			thread.start();
			}
			
			
		
		
	

}
}
