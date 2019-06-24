package io.travelook.broker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import io.travelook.model.Chat;
import io.travelook.model.Interessi;
import io.travelook.model.Messaggio;
import io.travelook.model.Recensione;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Segnalazione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class ClientProxy {
	private static Socket s;
	private final static String INDIRIZZO_BROKER = "localhost";
	private final static int PORTA_BROKER = 3999;
	private DataOutputStream out;
	private DataInputStream in; 
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	
	public ClientProxy() throws UnknownHostException, IOException {
		
		
	}
	
	private void initSocket() throws IOException {
		s= new Socket(INDIRIZZO_BROKER, PORTA_BROKER);
		out = new DataOutputStream(s.getOutputStream());
		in = new DataInputStream(s.getInputStream());
		oos = new ObjectOutputStream(out);
		ois = new ObjectInputStream(in);
	}
	
	
	public static void main(String args[]) throws UnknownHostException, IOException, JSONException, ClassNotFoundException {
		ClientProxy c = new ClientProxy();
		//Viaggio v = new Viaggio();
		//c.creaAnnuncio(v);
		//c.eliminaAnnuncio(18458);
		Utente[]listaUtenti=c.visualizzaUtentiPartecipanti(3);
		for(Utente u : listaUtenti) {
			System.out.println(u.getUsername());
		}
		/*
		 * qua non ci vuole il main, i metodi vanno invocati da client
		 */
		
		
		
	}
	
	
	
	public List<Viaggio> getListaAnnunci() throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List <Viaggio> listaViaggi = new ArrayList<Viaggio>();
		Richiesta r = new Richiesta<Viaggio>("local",123,new ArrayList<Viaggio>(),"getListaAnnunci");		
		oos.writeObject(r);
		Risposta<Viaggio> replyFromServer = (Risposta<Viaggio>) ois.readObject();
		for(Viaggio v : replyFromServer.getValori())
			System.out.println(v.getTitolo());
		s.close();
		return replyFromServer.getValori();
	}
	
	public boolean creaAnnuncio(Viaggio v) throws UnknownHostException, IOException, ClassNotFoundException {
		initSocket();
		List<Viaggio> viaggi = new ArrayList<Viaggio>();
		viaggi.add(v);
		Richiesta r = new Richiesta<Viaggio>(s.getLocalSocketAddress().toString(),s.getLocalPort(),viaggi,"creaAnnuncio");
		oos.writeObject(r);	
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	
	public boolean eliminaAnnuncio(int idAnnuncio) throws IOException, ClassNotFoundException {
		initSocket();
		List<Integer> argomentiRichiesta = new ArrayList<Integer>();
		argomentiRichiesta.add(idAnnuncio);
		Richiesta r = new Richiesta<Integer>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "eliminaAnnuncio");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);	
	}
	public Viaggio visualizzaAnnuncio(int idAnnuncio) throws IOException, ClassNotFoundException {
		Viaggio v;
		initSocket();
		List<Integer> argomentiRichiesta = new ArrayList<Integer>();
		argomentiRichiesta.add(idAnnuncio);
		Richiesta r = new Richiesta<Integer>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "eliminaAnnuncio");
		oos.writeObject(r);
		Risposta<Viaggio> reply = (Risposta<Viaggio>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
		
	}
	
	public boolean modificaAnnuncio(Viaggio daModificare) throws IOException, ClassNotFoundException {
		initSocket();
		List<Viaggio> argomentiRichiesta = new ArrayList<Viaggio>();
		argomentiRichiesta.add(daModificare);
		Richiesta r = new Richiesta<Viaggio>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "modificaAnnuncio");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
		
	}
	
	public boolean lasciaRecensione(Recensione daLasciare) throws IOException, ClassNotFoundException{
		initSocket();
		List<Recensione> argomentiRichiesta = new ArrayList<Recensione>();
		argomentiRichiesta.add(daLasciare);
		Richiesta r = new Richiesta<Recensione>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "lasciaRecensione");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	
	public boolean setViaggio(Viaggio daSettare) throws IOException, ClassNotFoundException {
		initSocket();
		List<Viaggio> argomentiRichiesta = new ArrayList<Viaggio>();
		argomentiRichiesta.add(daSettare);
		Richiesta r = new Richiesta<Viaggio>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "setViaggio");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
		
	}
	public boolean abbandonaAnnuncio(Utente daEliminare) throws IOException, ClassNotFoundException {
		initSocket();
		List<Utente> argomentiRichiesta = new ArrayList<Utente>();
		argomentiRichiesta.add(daEliminare);
		Richiesta r = new Richiesta<Utente>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "abbandonaAnnuncio");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	
	public Viaggio getViaggioById(int idViaggio) throws IOException, ClassNotFoundException {
		initSocket();
		List<Integer> argomentiRichiesta = new ArrayList<Integer>();
		argomentiRichiesta.add(idViaggio);
		Richiesta r = new Richiesta<Integer>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "getViaggioById");
		oos.writeObject(r);
		Risposta<Viaggio> reply = (Risposta<Viaggio>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	
	public Utente[] visualizzaUtentiPartecipanti(Integer idAnnuncio) throws IOException, ClassNotFoundException {
		initSocket();
		List<Integer> argomentiRichiesta = new ArrayList<Integer>();
		argomentiRichiesta.add(idAnnuncio);
		Richiesta r = new Richiesta<Integer>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "visualizzaUtentiPartecipanti");
		oos.writeObject(r);
		Risposta<Utente> reply = (Risposta<Utente>) ois.readObject();
		Utente[] arrayUtentiPartecipanti = new Utente[reply.getValori().size()];
		int i = 0;
		for(Utente u : reply.getValori()) {
			arrayUtentiPartecipanti[i++] = u;
			System.out.println("utente");
		}
		s.close();
		return arrayUtentiPartecipanti;
	}
	
	
	public boolean verificaCredenziali(String username, String hashPassword) throws IOException, ClassNotFoundException {
		initSocket();
		List<String> argomentiRichiesta = new ArrayList<String>();
		argomentiRichiesta.add(username);
		argomentiRichiesta.add(hashPassword);
		Richiesta<String> r = new Richiesta<String>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "verificaCredenziali");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		return reply.getValori().get(0);
	}
	
	public List<Utente> getListaUtenti() throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List<Utente> listutenti=new ArrayList<Utente>();
		Richiesta r= new Richiesta<Utente>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listutenti,"getListaUtenti");
		oos.writeObject(r);
		Risposta<Utente> reply=(Risposta<Utente>)ois.readObject();
		for(Utente u : reply.getValori()) {
			System.out.println("Utente "+u.getId()+" nome:"+u.getNome());
		    }
		s.close();
		return reply.getValori();
		}
	
	public boolean aggiungiInteressi(Interessi interesse,Utente u) throws UnknownHostException, IOException, ClassNotFoundException{
	  initSocket();
	  List<Object> argomentiRichiesta = new ArrayList<Object>();
		argomentiRichiesta.add(interesse);
		argomentiRichiesta.add(u);
		Richiesta r = new Richiesta<Object>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "aggiungiInteresse");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	public int getIdUtenteFromUsername(String user) throws UnknownHostException, IOException, ClassNotFoundException{
		  initSocket();
		  List<String> argomentiRichiesta = new ArrayList<String>();
			argomentiRichiesta.add(user);
			Richiesta r = new Richiesta<String>(s.getLocalSocketAddress().toString(),
					s.getLocalPort(),argomentiRichiesta, "getIdUtenteFromUsername");
			oos.writeObject(r);
			Risposta<Integer> reply = (Risposta<Integer>) ois.readObject();
			System.out.println(reply.getValori().get(0));
			s.close();
			return reply.getValori().get(0);
	}
	public Storico visualizzaStorico(Utente u) throws UnknownHostException, IOException, ClassNotFoundException{
		  initSocket();
		  List<Object> argomentiRichiesta = new ArrayList<Object>();
		  argomentiRichiesta.add(u);
		  Richiesta r = new Richiesta<Object>(s.getLocalSocketAddress().toString(),
					s.getLocalPort(),argomentiRichiesta, "visualizzaStorico");
			oos.writeObject(r);
			Risposta<Storico> reply = (Risposta<Storico>) ois.readObject();
			for(Viaggio v: reply.getValori().get(0).getStorico())
				System.out.println(v.getTitolo());
			s.close();
			return reply.getValori().get(0);
	}
	public List<Recensione> visualizzaRecensioni(Utente u) throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List<Utente> listutenti=new ArrayList<Utente>();
		listutenti.add(u);
		Richiesta r= new Richiesta<Utente>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listutenti,"visualizzaRecensioni");
		oos.writeObject(r);
		Risposta<Recensione> reply=(Risposta<Recensione>)ois.readObject();
		for(Recensione rec : reply.getValori()) {
			System.out.println("Recensione "+rec.getId());
		    }
		s.close();
		return reply.getValori();
	}
	public boolean modificaProfilo(Utente u) throws IOException, ClassNotFoundException {
		initSocket();
		List<Utente> argomentiRichiesta = new ArrayList<Utente>();
		argomentiRichiesta.add(u);
		Richiesta r = new Richiesta<Utente>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "modificaProfilo");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	public List<Viaggio> getViaggiInPartecipazione(Utente u) throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List<Utente> listutenti=new ArrayList<Utente>();
		listutenti.add(u);
		Richiesta r = new Richiesta<Utente>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listutenti,"getViaggiinPartecipazione");		
		oos.writeObject(r);
		Risposta<Viaggio> replyFromServer = (Risposta<Viaggio>) ois.readObject();
		for(Viaggio v : replyFromServer.getValori())
			System.out.println(v.getTitolo());
		s.close();
		return replyFromServer.getValori();
	}
	public Utente attachInteressiToUser(Utente u) throws IOException, ClassNotFoundException {
		initSocket();
		List<Utente> argomentiRichiesta = new ArrayList<Utente>();
		argomentiRichiesta.add(u);
		Richiesta r = new Richiesta<Utente>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "attachInteressiToUser");
		oos.writeObject(r);
		Risposta<Utente> reply = (Risposta<Utente>) ois.readObject();
		System.out.println("Utente "+reply.getValori().get(0).getId()+ " nome :"+reply.getValori().get(0).getNome());
		s.close();
		return reply.getValori().get(0);
	}
	public List<Viaggio> getViaggiInAttesadiConferma(Utente u) throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List<Utente> listutenti=new ArrayList<Utente>();
		listutenti.add(u);
		Richiesta r = new Richiesta<Utente>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listutenti,"getViaggiinAttesadiConferma");		
		oos.writeObject(r);
		Risposta<Viaggio> replyFromServer = (Risposta<Viaggio>) ois.readObject();
		for(Viaggio v : replyFromServer.getValori())
			System.out.println(v.getTitolo());
		s.close();
		return replyFromServer.getValori();
	}
	public boolean eliminaUtente() throws IOException, ClassNotFoundException {
		initSocket();
		List<Boolean> argomentiRichiesta = new ArrayList<Boolean>();
		Richiesta r = new Richiesta<Boolean>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "eliminaUtente");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);	
	}
	public Utente getUtenteById(int id) throws IOException, ClassNotFoundException {
		initSocket();
		List<Integer> argomentiRichiesta = new ArrayList<Integer>();
		argomentiRichiesta.add(id);
		Richiesta r = new Richiesta<Integer>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "getUtenteById");
		oos.writeObject(r);
		Risposta<Utente> reply = (Risposta<Utente>) ois.readObject();
		System.out.println("Utente "+reply.getValori().get(0).getId()+ " nome :"+reply.getValori().get(0).getNome());
		s.close();
		return reply.getValori().get(0);
	}
	public Utente getUtente() throws IOException, ClassNotFoundException {
		initSocket();
		List<Utente> argomentiRichiesta = new ArrayList<Utente>();
		Richiesta r = new Richiesta<Utente>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "getUtente");
		oos.writeObject(r);
		Risposta<Utente> reply = (Risposta<Utente>) ois.readObject();
		System.out.println("Utente "+reply.getValori().get(0).getId()+ " nome :"+reply.getValori().get(0).getNome());
		s.close();
		return reply.getValori().get(0);
	}
	public boolean setUtente(Utente u) throws IOException, ClassNotFoundException {
		initSocket();
		List<Utente> argomentiRichiesta = new ArrayList<Utente>();
		argomentiRichiesta.add(u);
		Richiesta r = new Richiesta<Utente>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "setUtente");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	public boolean aggiornaRecensione(Recensione r) throws IOException, ClassNotFoundException {
		initSocket();
		List<Recensione> argomentiRichiesta = new ArrayList<Recensione>();
		argomentiRichiesta.add(r);
		Richiesta ric = new Richiesta<Recensione>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "aggiornaRecensione");
		oos.writeObject(ric);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	
	public boolean registraUtente(Utente u, String hash) throws IOException, ClassNotFoundException {
		initSocket();
		List<Object> argomentiRichiesta = new ArrayList<Object>();
		argomentiRichiesta.add(u);
		argomentiRichiesta.add(hash);
		Richiesta ric = new Richiesta<Object>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "registraUtente");
		oos.writeObject(ric);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	
	public boolean inviaMessaggio(Messaggio m, Viaggio v ) throws IOException, ClassNotFoundException {
		initSocket();
		List<Object> argomentiRichiesta = new ArrayList<Object>();
		argomentiRichiesta.add(m);
		argomentiRichiesta.add(v);
		Richiesta ric = new Richiesta<Object>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "inviaMessaggio");
		oos.writeObject(ric);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	public Chat getChat(Viaggio v ) throws IOException, ClassNotFoundException {
		initSocket();
		List<Viaggio> argomentiRichiesta = new ArrayList<Viaggio>();
		argomentiRichiesta.add(v);
		Richiesta ric = new Richiesta<Viaggio>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "getChat");
		oos.writeObject(ric);
		Risposta<Chat> reply = (Risposta<Chat>) ois.readObject();
		System.out.println("ricevuta la chat per il viaggio"+v.getIdViaggio());
		s.close();
		return reply.getValori().get(0);
	}
	
	public boolean nuovaRichiesta(RichiestaDiPartecipazione nuova) throws IOException, ClassNotFoundException {
		initSocket();
		List<RichiestaDiPartecipazione> argomentiRichiesta = new ArrayList<RichiestaDiPartecipazione>();
		argomentiRichiesta.add(nuova);
		Richiesta ric = new Richiesta<RichiestaDiPartecipazione>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "nuovaRichiesta");
		oos.writeObject(ric);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
		
	}
	
	public List<RichiestaDiPartecipazione> getRichiesteForCreatoreViaggio(Utente creatore,
			Viaggio viaggio) throws IOException, ClassNotFoundException{
		initSocket();
		List<Object> argomentiRichiesta = new ArrayList<Object>();
		argomentiRichiesta.add(creatore);
		System.out.println("Creatore "+creatore.getUsername());
		System.out.println("Viaggio "+viaggio.getTitolo());
		argomentiRichiesta.add(viaggio);
		Richiesta<Object> ric = new Richiesta<Object>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "getRichiesteForCreatoreViaggio");
		oos.writeObject(ric);
		Risposta<RichiestaDiPartecipazione> reply = (Risposta<RichiestaDiPartecipazione>) ois.readObject();
		s.close();
		return reply.getValori();
		
	}
	
	public List<RichiestaDiPartecipazione> getRichiesteForCreatore(Utente creatore) throws IOException, ClassNotFoundException{
		initSocket();
		List<Object> argomentiRichiesta = new ArrayList<Object>();
		argomentiRichiesta.add(creatore);
		System.out.println("Creatore "+creatore.getUsername());
		Richiesta<Object> ric = new Richiesta<Object>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "getRichiesteForCreatore");
		oos.writeObject(ric);
		Risposta<RichiestaDiPartecipazione> reply = (Risposta<RichiestaDiPartecipazione>) ois.readObject();
		s.close();
		return reply.getValori();
		
	}
	public boolean rispondiRichiesta(RichiestaDiPartecipazione risposta) throws IOException, ClassNotFoundException {
		initSocket();
		List<RichiestaDiPartecipazione> argomentiRichiesta = new ArrayList<RichiestaDiPartecipazione>();
		argomentiRichiesta.add(risposta);
		System.out.println(risposta.getStato() + "!!!!!!");
		Richiesta<RichiestaDiPartecipazione> ric = new Richiesta<RichiestaDiPartecipazione>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "rispondiRichiesta");
		oos.writeObject(ric);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	public List<Segnalazione> getSegnalazioniUtente(int id) throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List<Integer> listids=new ArrayList<Integer>();
		listids.add(id);
		Richiesta r= new Richiesta<Integer>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listids,"getSegnalazioniUtente");
		oos.writeObject(r);
		Risposta<Segnalazione> reply=(Risposta<Segnalazione>)ois.readObject();
		for(Segnalazione s : reply.getValori()) {
			System.out.println("Segnalazione "+s.getIdSegnalazione());
		    }
		s.close();
		return reply.getValori();
	}
	public boolean segnalaUtente(Segnalazione segn) throws IOException, ClassNotFoundException {
		initSocket();
		List<Segnalazione> argomentiRichiesta = new ArrayList<Segnalazione>();
		argomentiRichiesta.add(segn);
		Richiesta<Segnalazione> ric = new Richiesta<Segnalazione>(s.getLocalSocketAddress().toString(),
				s.getLocalPort(),argomentiRichiesta, "segnalaUtente");
		oos.writeObject(ric);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	public Segnalazione leggiSegnalazione(int id) throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List<Integer> listids=new ArrayList<Integer>();
		listids.add(id);
		Richiesta r= new Richiesta<Integer>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listids,"leggiSegnalazione");
		oos.writeObject(r);
		Risposta<Segnalazione> reply=(Risposta<Segnalazione>)ois.readObject();
		System.out.println("Segnalazione "+reply.getValori().get(0).getIdSegnalazione());
		s.close();
		return reply.getValori().get(0);
	}
	public boolean rimuoviSegnalazione(int id) throws IOException, ClassNotFoundException {
		initSocket();
		List<Integer> listids=new ArrayList<Integer>();
		listids.add(id);
		Richiesta r= new Richiesta<Integer>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listids,"rimuoviSegnalazione");
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	public List<Segnalazione> getSegnalazioni() throws UnknownHostException, IOException, ClassNotFoundException{
		initSocket();
		List <Segnalazione> listaViaggi = new ArrayList<Segnalazione>();
		Richiesta r = new Richiesta<Segnalazione>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listaViaggi,"getSegnalazioni");		
		oos.writeObject(r);
		Risposta<Segnalazione> replyFromServer = (Risposta<Segnalazione>) ois.readObject();
		for(Segnalazione sg : replyFromServer.getValori())
			System.out.println("Segnalazione :"+sg.getIdSegnalazione());
		s.close();
		return replyFromServer.getValori();
	}
	public boolean setSegnalazioni(List<Segnalazione> segnalazioni) throws IOException, ClassNotFoundException {
		initSocket();
		List <Segnalazione> listasegn = new ArrayList<Segnalazione>();
		listasegn=segnalazioni;
		Richiesta r = new Richiesta<Segnalazione>(s.getLocalSocketAddress().toString(),s.getLocalPort(),listasegn,"setSegnalazioni");		
		oos.writeObject(r);
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		s.close();
		return reply.getValori().get(0);
	}
	
	
	
	
	
	
	
	
}
