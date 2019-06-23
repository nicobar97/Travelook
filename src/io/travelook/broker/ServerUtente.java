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
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class ServerUtente extends Thread {
	private UtenteController utentec  = new UtenteController();
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerUtente(Socket brokerSocket) {
		this.brokerSocket=brokerSocket;
		
	}
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4003);
			while(true) {
				System.out.println("Il ServerUtente � in attesa sulla porta " + serverSocket.getLocalPort());
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
		if(servizioRichiesto.equals("visualizzaRecensioni")){
			List<Recensione> listarecensioni=utentec.visualizzaRecensioni();
			Risposta<Recensione> replyrec= new Risposta<Recensione>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarecensioni);
			ous.writeObject(replyrec);
		}
		if(servizioRichiesto.equals("getViaggiinPartecipazione")){
			List<Viaggio> listaviaggip=utentec.getViaggiInPartecipazione();
			Risposta<Viaggio> replyviaggip= new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaviaggip);
			ous.writeObject(replyviaggip);
		}
		if(servizioRichiesto.equals("getViaggiinAttesadiConferma")){
			List<Viaggio> listaviaggic=utentec.getViaggiInAttesaDiConferma();
			Risposta<Viaggio> replyviaggic= new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaviaggic);
			ous.writeObject(replyviaggic);
		}
		if(servizioRichiesto.equals("aggiungiInteresse")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Interessi interesse = (Interessi) listaArgomenti.get(0);
			boolean esito =utentec.aggiungiInteressi(interesse);
			List<Boolean> listaEsiti = new ArrayList<Boolean>();
			listaEsiti.add(esito);
			Risposta<Boolean> replyinteressi = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaEsiti);
			ous.writeObject(replyinteressi);
		}
		if(servizioRichiesto.equals("eliminaUtente")) {
			boolean esito =utentec.eliminaUtente();
			List<Boolean> listaelimina = new ArrayList<Boolean>();
			listaelimina.add(esito);
			Risposta<Boolean> replyeliminau = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaelimina);
			ous.writeObject(replyeliminau);
		}
		if(servizioRichiesto.equals("visualizzaStorico")) {
			//MANCA LA SET UTENTE PENSO
			Storico storico =utentec.visualizzaStorico();
			List<Storico> listastorici = new ArrayList<Storico>();
			listastorici.add(storico);
			Risposta<Storico> replystorico = new Risposta<Storico>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listastorici);
			ous.writeObject(replystorico);
		}
		if(servizioRichiesto.equals("lasciaRecensione")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Recensione r = (Recensione) listaArgomenti.get(0);
			boolean esito =utentec.lasciaRecensione(r);
			List<Boolean> listarec = new ArrayList<Boolean>();
			listarec.add(esito);
			Risposta<Boolean> replyrec = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarec);
			ous.writeObject(replyrec);
		}
		if(servizioRichiesto.equals("aggiornaRecensione")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Recensione r = (Recensione) listaArgomenti.get(0);
			boolean esito =utentec.aggiornaRecensione(r);
			List<Boolean> listarec = new ArrayList<Boolean>();
			listarec.add(esito);
			Risposta<Boolean> replyrec = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarec);
			ous.writeObject(replyrec);
		}
		if(servizioRichiesto.equals("modificaProfilo")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
		    Utente u = (Utente)listaArgomenti.get(0);
			boolean esito =utentec.modificaProfilo(u);
			List<Boolean> listarec = new ArrayList<Boolean>();
			listarec.add(esito);
			Risposta<Boolean> replyrec = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarec);
			ous.writeObject(replyrec);
		}
		if(servizioRichiesto.equals("attachInteressiToUser")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Utente  u = (Utente)listaArgomenti.get(0);
			System.out.println("Daje");
			Utente user =utentec.attachInteressiToUser(u);
			List<Utente> listautenti = new ArrayList<Utente>();
			listautenti.add(user);
			Risposta<Utente> replyinteressiuser = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listautenti);
			ous.writeObject(replyinteressiuser);
		}
		if(servizioRichiesto.equals("getUtenteById")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Integer idutente = (Integer) listaArgomenti.get(0);
			Utente u = utentec.getUtenteById(idutente);
			List<Utente> listau = new ArrayList<Utente>();
			listau.add(u);
			Risposta<Utente> replyGetutenteById = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listau);
			ous.writeObject(replyGetutenteById);
		}
		if(servizioRichiesto.equals("getUtente")) {
			System.out.println("sto per fare la getUtente");
			Utente u = utentec.getU();
			System.out.println("preso l'utente :"+u.getId()+" di nome: "+u.getNome());
			List<Utente> listautenti = new ArrayList<Utente>();
			listautenti.add(u);
			Risposta<Utente> replyGetutente = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),
					brokerSocket.getPort(),listautenti);
			ous.writeObject(replyGetutente);
		}
		if(servizioRichiesto.equals("setUtente")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			Utente  u = (Utente)listaArgomenti.get(0);
			utentec.setU(u);
			System.out.println("Settato con successo l'utente"+u.getId()+"di nome :"+u.getNome());
			boolean res=true;
			List<Boolean> listaset = new ArrayList<Boolean>();
			listaset.add(res);
			Risposta<Boolean> replyset = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaset);
			ous.writeObject(replyset);
		}
		if(servizioRichiesto.equals("getIdUtenteFromUsername")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			String  s = (String)listaArgomenti.get(0);
			Integer id=utentec.getIdUtenteFromUsername(s);
			List<Integer> listaid = new ArrayList<Integer>();
			listaid.add(id);
			Risposta<Integer> replyid = new Risposta<Integer>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaid);
			ous.writeObject(replyid);
		}		
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}	
}
