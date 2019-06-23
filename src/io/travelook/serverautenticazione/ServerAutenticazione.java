package io.travelook.serverautenticazione;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.travelook.broker.Richiesta;
import io.travelook.broker.Risposta;
import io.travelook.controller.autenticazione.LoginController;
import io.travelook.controller.autenticazione.RegistrazioneController;
import io.travelook.model.Utente;

public class ServerAutenticazione extends Thread{
	private LoginController loginController;
	private RegistrazioneController registrazioneController;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;

	public ServerAutenticazione(Socket brokerSocket) {
		this.loginController = new LoginController();
		this.registrazioneController = new RegistrazioneController();
		this.brokerSocket=brokerSocket;

	}

	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4002);
			while(true) {
				System.out.println("Il ServerAutenticazione è in attesa sulla porta " + serverSocket.getLocalPort());
				brokerSocket = serverSocket.accept();
				System.out.println("Richiesta arrivata, lancio thread!");
				ServerAutenticazione thread = new ServerAutenticazione(brokerSocket);
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
			System.out.println("ServerLogin: servizio richiesto: "+servizioRichiesto);
			if(servizioRichiesto.equals("verificaCredenziali")) {
				System.out.println("Ottengo credenziali da richiesta...");
				String username = String.valueOf(richiestaDaBroker.getArgomenti().get(0));
				String hashPwd = String.valueOf(richiestaDaBroker.getArgomenti().get(1));
				System.out.println(username + " " + hashPwd);
				boolean esito = loginController.verificaCredenziali(username, hashPwd);
				List<Boolean> listaEsito = new ArrayList<Boolean>();
				listaEsito.add(esito);
				Risposta<Boolean> rispostaAlBroker = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
						brokerSocket.getPort(),listaEsito);
				ous.writeObject(rispostaAlBroker);
			}
			if(servizioRichiesto.equals("registraUtente")) {
				System.out.println("Devo registrare un utente");
				Utente daRegistrare = (Utente) richiestaDaBroker.getArgomenti().get(0);
				System.out.println(daRegistrare.getUsername());
				String hashPwd = String.valueOf(richiestaDaBroker.getArgomenti().get(1));
				boolean esito = registrazioneController.registraUtente(daRegistrare, hashPwd);
				List<Boolean> listaEsito = new ArrayList<Boolean>();
				listaEsito.add(esito);
				Risposta<Boolean> rispostaAlBroker = new Risposta<Boolean>(brokerSocket.getInetAddress().toString(),
						brokerSocket.getPort(),listaEsito);
				ous.writeObject(rispostaAlBroker);
			}


		}catch(Exception e) {
			e.printStackTrace();
		}




	}
}
