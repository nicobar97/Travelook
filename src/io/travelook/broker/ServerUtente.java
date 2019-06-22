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
	private static UtenteController utentec=new UtenteController() ;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerUtente() {
	}
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4003);
			while(true) {
				System.out.println("Il ServerUtente è in attesa sulla porta " + serverSocket.getLocalPort());
				brokerSocket = serverSocket.accept();
				System.out.println("ServerUtente:richiesta arrivata, lancio thread!");
				ServerUtenteThread thread = new ServerUtenteThread(brokerSocket,utentec);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}			
}
