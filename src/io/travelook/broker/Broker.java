package io.travelook.broker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sun.javafx.collections.MappingChange.Map;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Viaggio;
public class Broker extends Thread {
	private Socket clientSocket;
	private Socket serverListaAnnunciSocket;
	private final static String IP_SERVER_LISTA_ANNUNCI = "localhost";
	private final int PORTA_SERVER_LISTA_ANNUNCI = 4001;
	
	
	public Broker(Socket s) {
		this.clientSocket = s;
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
		switch(servizio) {
		case "getListaAnnunci":
			this.serverListaAnnunciSocket = new Socket(IP_SERVER_LISTA_ANNUNCI,PORTA_SERVER_LISTA_ANNUNCI);
			ObjectInputStream oisListaAnnunci = new ObjectInputStream(serverListaAnnunciSocket.getInputStream());
			ObjectOutputStream ousListaAnnunci = new ObjectOutputStream(serverListaAnnunciSocket.getOutputStream());
			ousListaAnnunci.writeObject(richiesta);
			Risposta<Viaggio> listaViaggiRisposta = (Risposta<Viaggio>) oisListaAnnunci.readObject();
			oos.writeObject(listaViaggiRisposta);
			break;
		case "creaAnnuncio":
			Viaggio v =(Viaggio) richiesta.getArgomenti().get(0);
			boolean esito = lac.creaAnnuncio(v);
			List<Boolean> esiti = new ArrayList<Boolean>();
			esiti.add(esito);
			Risposta reply2 = new Risposta<Boolean>(clientSocket.getInetAddress().toString(),clientSocket.getPort(),esiti);
			oos.writeObject(reply2);
			break;
			
			
		}
		}
		catch(Exception e) {
			
		}
			
			
		}
		
		
	
	
	public static void main(String[] args) throws Exception {
		
		
			AnnuncioController ac = new AnnuncioController();
			ListaAnnunciController lac = new ListaAnnunciController();
		
			ServerSocket sSock = new ServerSocket(4000);
			System.out.println("Il Server è in attesa sulla porta 4000.");
			while(true) {
			Socket socket = sSock.accept();
			Broker thread = new Broker(socket);
			thread.start();
			}
			
			
		
		
	

}
}
