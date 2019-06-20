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

import io.travelook.model.Viaggio;

public class ClientProxy {
	private static Socket s;
	private final static String INDIRIZZO_BROKER = "localhost";
	private final static int PORTA_BROKER = 4000;
	
	
	public static void main(String args[]) throws UnknownHostException, IOException, JSONException, ClassNotFoundException {
		ClientProxy c = new ClientProxy();
		Viaggio v = new Viaggio();
		c.creaAnnuncio(v);
		
		/*
		 * qua non ci vuole il main, i metodi vanno invocati da client
		 */
		
		
	}
	
	public List<Viaggio> getListaAnnunci() throws UnknownHostException, IOException, ClassNotFoundException{
		s= new Socket(INDIRIZZO_BROKER,PORTA_BROKER);
		List <Viaggio> listaViaggi = new ArrayList<Viaggio>();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		DataInputStream dis = new DataInputStream(s.getInputStream());
		Richiesta r = new Richiesta<Viaggio>("local",123,new ArrayList<Viaggio>(),"getListaAnnunci");
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		
		oos.writeObject(r);
		Risposta<Viaggio> replyFromServer = (Risposta<Viaggio>) ois.readObject();
		for(Viaggio v : replyFromServer.getValori())
			System.out.println(v.getTitolo());
		return replyFromServer.getValori();
	}
	
	public boolean creaAnnuncio(Viaggio v) throws UnknownHostException, IOException, ClassNotFoundException {
		s= new Socket(INDIRIZZO_BROKER,PORTA_BROKER);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		DataInputStream dis = new DataInputStream(s.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		List<Viaggio> viaggi = new ArrayList<Viaggio>();
		viaggi.add(v);
		Richiesta r = new Richiesta<Viaggio>(s.getLocalSocketAddress().toString(),s.getLocalPort(),viaggi,"creaAnnuncio");
		oos.writeObject(r);
		
		Risposta<Boolean> reply = (Risposta<Boolean>) ois.readObject();
		System.out.println(reply.getValori().get(0));
		return reply.getValori().get(0);
	}
	

}
