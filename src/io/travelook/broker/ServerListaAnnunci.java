package io.travelook.broker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Viaggio;

public class ServerListaAnnunci extends Thread {
	private ListaAnnunciController listaAnnunciController;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerListaAnnunci(Socket brokerSocket) {
		this.listaAnnunciController = new ListaAnnunciController();
		this.brokerSocket=brokerSocket;
		
	}
	
	public static void main() {
		try {
			serverSocket = new ServerSocket(4001);
			while(true) {
				brokerSocket = serverSocket.accept();
				ServerListaAnnunci thread = new ServerListaAnnunci(brokerSocket);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run() {
		try {
		ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
		ObjectOutputStream ous = new ObjectOutputStream(brokerSocket.getOutputStream());
		Richiesta<Object> richiestaDaBroker = (Richiesta<Object>) ois.readObject();
		String servizioRichiesto = richiestaDaBroker.getServizio();
		if(servizioRichiesto.equals("getListaAnnunci")) {
			List<Viaggio> listaAnnunci = listaAnnunciController.getAnnunci();
			Risposta<Viaggio> replyAnnunci = new Risposta<Viaggio>(brokerSocket.getInetAddress().toString(),brokerSocket.getPort(),listaAnnunci);
			ous.writeObject(replyAnnunci);
			
		}
		brokerSocket.close();
		}
		catch(Exception e) {
		}
		}
	}


