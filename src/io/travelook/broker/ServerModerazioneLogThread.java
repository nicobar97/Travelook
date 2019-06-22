package io.travelook.broker;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.chat.ChatController;
import io.travelook.controller.segnalazioni.SegnalazioniController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Chat;
import io.travelook.model.Interessi;
import io.travelook.model.Messaggio;
import io.travelook.model.Recensione;
import io.travelook.model.Segnalazione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class ServerModerazioneLogThread extends Thread{
   private Socket bsock;
   private SegnalazioniController contrsegn;
   
   public ServerModerazioneLogThread(Socket socket,SegnalazioniController segn) {
	   this.bsock=socket;
	   this.contrsegn=segn;
   }
   
   
   public void run() {
		try {
	    System.out.println("Sono connesso ad "+bsock.toString());
	    ObjectOutputStream ous = new ObjectOutputStream(bsock.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(bsock.getInputStream());
		System.out.println("Attendo la richiesta di un servizio da "+bsock.toString());
		
		Richiesta<Object> richiestaDaBroker = (Richiesta<Object>) ois.readObject();
		System.out.println("Richiesta di servizio ricevuta da broker " + bsock.toString());
		String servizioRichiesto = richiestaDaBroker.getServizio();
		System.out.println("ServerModerazioneLog: servizio richiesto: "+servizioRichiesto);
		if(servizioRichiesto.equals("segnalaUtente")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
	        Segnalazione s = (Segnalazione)listaArgomenti.get(0);
			Boolean res =contrsegn.segnalaUtente(s);
			List<Boolean> listarec = new ArrayList<Boolean>();
			listarec.add(res);
			Risposta<Boolean> replyres = new Risposta<Boolean>(bsock.getInetAddress().toString(),bsock.getPort(),listarec);
			ous.writeObject(replyres);
		   }
		if(servizioRichiesto.equals("getSegnalazioniUtente")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
	        Integer id=(Integer)listaArgomenti.get(0);
			List<Segnalazione> listasegn = new ArrayList<Segnalazione>();
			listasegn=contrsegn.getSegnalazioniUtente(id);
			Risposta<Segnalazione> replysegn = new Risposta<Segnalazione>(bsock.getInetAddress().toString(),bsock.getPort(),listasegn);
			ous.writeObject(replysegn);
		}
		if(servizioRichiesto.equals("leggiSegnalazione")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
	        Integer id=(Integer)listaArgomenti.get(0);
	        Segnalazione s=contrsegn.leggiSegnalazione(id);
			List<Segnalazione> listasegn = new ArrayList<Segnalazione>();
			listasegn.add(s);
			Risposta<Segnalazione> replysegn = new Risposta<Segnalazione>(bsock.getInetAddress().toString(),bsock.getPort(),listasegn);
			ous.writeObject(replysegn);
		}
		if(servizioRichiesto.equals("rimuoviSegnalazione")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
	        Integer id=(Integer)listaArgomenti.get(0);
	        Boolean res=contrsegn.rimuoviSegnalazione(id);
			List<Boolean> listasegn = new ArrayList<Boolean>();
			listasegn.add(res);
			Risposta<Boolean> replyres = new Risposta<Boolean>(bsock.getInetAddress().toString(),bsock.getPort(),listasegn);
			ous.writeObject(replyres);
		}
		if(servizioRichiesto.equals("getSegnalazioni")) {
			List<Segnalazione> listasegn = new ArrayList<Segnalazione>();
			listasegn=contrsegn.getSegnalazioni();
			Risposta<Segnalazione> replysegn = new Risposta<Segnalazione>(bsock.getInetAddress().toString(),bsock.getPort(),listasegn);
			ous.writeObject(replysegn);
		}
		if(servizioRichiesto.equals("setSegnalazioni")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
			List<Segnalazione> listasegn = new ArrayList<Segnalazione>();
			for(Object o : listaArgomenti) {
				if(o instanceof Segnalazione) {
					Segnalazione s=(Segnalazione)o;
					listasegn.add(s);
				}
			}
			contrsegn.setSegnalazioni(listasegn);
			boolean res=true;
			List<Boolean> listares = new ArrayList<Boolean>();
			listares.add(res);
			Risposta<Boolean> replysegn = new Risposta<Boolean>(bsock.getInetAddress().toString(),bsock.getPort(),listares);
			ous.writeObject(replysegn);
		}
		
		}catch(Exception e ) {
			e.printStackTrace();
		}
   }
}
