package io.travelook.serverchat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.broker.Richiesta;
import io.travelook.broker.Risposta;
import io.travelook.controller.chat.ChatController;
import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Viaggio;

public class ServerChatThread extends Thread{
   private Socket bsock;
   private ChatController chcontr;
   
   public ServerChatThread(Socket socket,ChatController chat) {
	   this.bsock=socket;
	   this.chcontr=chat;
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
		System.out.println("ServerUtente: servizio richiesto: "+servizioRichiesto);
		if(servizioRichiesto.equals("inviaMessaggio")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
	        Messaggio m = (Messaggio)listaArgomenti.get(0);
	        Viaggio v=(Viaggio)listaArgomenti.get(1);
			Boolean res = chcontr.inviaMessaggio(m, v);
			List<Boolean> listarec = new ArrayList<Boolean>();
			listarec.add(res);
			Risposta<Boolean> replyUtenti = new Risposta<Boolean>(bsock.getInetAddress().toString(),bsock.getPort(),listarec);
			ous.writeObject(replyUtenti);
		   }
		if(servizioRichiesto.equals("getChat")) {
			List<Object> listaArgomenti = richiestaDaBroker.getArgomenti();
	        Viaggio v=(Viaggio)listaArgomenti.get(0);
			Chat res =chcontr.getChat(v);
			List<Chat> listarec = new ArrayList<Chat>();
			listarec.add(res);
			Risposta<Chat> replyUtenti = new Risposta<Chat>(bsock.getInetAddress().toString(),bsock.getPort(),listarec);
			ous.writeObject(replyUtenti);
		}
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
   }
}
