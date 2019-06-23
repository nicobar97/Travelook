package io.travelook.serverutente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.broker.Richiesta;
import io.travelook.broker.Risposta;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class ServerUtenteThread extends Thread{
   private Socket brokerSocket;
   private UtenteController uc;
   
   public ServerUtenteThread(Socket br,UtenteController ucontr) {
	   this.brokerSocket=br;
	   this.uc=ucontr;
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
		List<Utente> listautenti = uc.getListaUtenti();
		Risposta<Utente> replyUtenti = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listautenti);
		ous.writeObject(replyUtenti);
		//
	   }
	if(servizioRichiesto.equals("visualizzaRecensioni")){
		List<Recensione> listarecensioni=uc.visualizzaRecensioni();
		Risposta<Recensione> replyrec= new Risposta<Recensione>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarecensioni);
		ous.writeObject(replyrec);
	}
	if(servizioRichiesto.equals("getViaggiinPartecipazione")){
		List<Viaggio> listaviaggip=uc.getViaggiInPartecipazione();
		Risposta<Viaggio> replyviaggip= new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaviaggip);
		ous.writeObject(replyviaggip);
	}
	if(servizioRichiesto.equals("getViaggiinAttesadiConferma")){
		List<Viaggio> listaviaggic=uc.getViaggiInAttesaDiConferma();
		Risposta<Viaggio> replyviaggic= new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaviaggic);
		ous.writeObject(replyviaggic);
	}
	if(servizioRichiesto.equals("aggiungiInteresse")) {
		List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
		Interessi interesse = (Interessi) listaArgomenti.get(0);
		boolean esito =uc.aggiungiInteressi(interesse);
		List<Boolean> listaEsiti = new ArrayList<Boolean>();
		listaEsiti.add(esito);
		Risposta<Boolean> replyinteressi = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaEsiti);
		ous.writeObject(replyinteressi);
	}
	if(servizioRichiesto.equals("eliminaUtente")) {
		boolean esito =uc.eliminaUtente();
		List<Boolean> listaelimina = new ArrayList<Boolean>();
		listaelimina.add(esito);
		Risposta<Boolean> replyeliminau = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaelimina);
		ous.writeObject(replyeliminau);
	}
	if(servizioRichiesto.equals("visualizzaStorico")) {
		Storico storico =uc.visualizzaStorico();
		List<Storico> listastorici = new ArrayList<Storico>();
		listastorici.add(storico);
		Risposta<Storico> replystorico = new Risposta<Storico>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listastorici);
		ous.writeObject(replystorico);
	}
	if(servizioRichiesto.equals("lasciaRecensione")) {
		List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
		Recensione r = (Recensione) listaArgomenti.get(0);
		boolean esito =uc.lasciaRecensione(r);
		List<Boolean> listarec = new ArrayList<Boolean>();
		listarec.add(esito);
		Risposta<Boolean> replyrec = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarec);
		ous.writeObject(replyrec);
	}
	if(servizioRichiesto.equals("aggiornaRecensione")) {
		List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
		Recensione r = (Recensione) listaArgomenti.get(0);
		boolean esito =uc.aggiornaRecensione(r);
		List<Boolean> listarec = new ArrayList<Boolean>();
		listarec.add(esito);
		Risposta<Boolean> replyrec = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarec);
		ous.writeObject(replyrec);
	}
	if(servizioRichiesto.equals("modificaProfilo")) {
		List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
	    Utente u = (Utente)listaArgomenti.get(0);
	    UtenteController ut= new UtenteController(u);
		boolean esito =ut.modificaProfilo(u);
		List<Boolean> listarec = new ArrayList<Boolean>();
		listarec.add(esito);
		Risposta<Boolean> replyrec = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listarec);
		ous.writeObject(replyrec);
	}
	if(servizioRichiesto.equals("attachInteressiToUser")) {
		List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
		Utente  u = (Utente)listaArgomenti.get(0);
		UtenteController ut= new UtenteController(u);
		Utente user =ut.attachInteressiToUser(u);
		List<Utente> listautenti = new ArrayList<Utente>();
		listautenti.add(user);
		Risposta<Utente> replyinteressiuser = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),
				brokerSocket.getPort(),listautenti);
		ous.writeObject(replyinteressiuser);
	}
	if(servizioRichiesto.equals("getUtenteById")) {
		List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
		Integer idutente = (Integer) listaArgomenti.get(0);
		Utente u = uc.getUtenteById(idutente);
		List<Utente> listau = new ArrayList<Utente>();
		listau.add(u);
		Risposta<Utente> replyGetutenteById = new Risposta<Utente>(brokerSocket.getInetAddress().toString(),
				brokerSocket.getPort(),listau);
		ous.writeObject(replyGetutenteById);
	}
	if(servizioRichiesto.equals("getUtente")) {
		System.out.println("sto per fare la getUtente");
		Utente u = uc.getU();
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
		uc.setU(u);
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
		Integer id=uc.getIdUtenteFromUsername(s);
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
