package io.travelook.broker;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;

import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.utils.SHA256;

public class ClientProxy {
	private static Socket s;
	private final static String INDIRIZZO_BROKER = "localhost";
	private final static int PORTA_BROKER = 4000;
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
		Viaggio v = new Viaggio();
		c.creaAnnuncio(v);
		c.eliminaAnnuncio(18458);
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
	

		
	

}
