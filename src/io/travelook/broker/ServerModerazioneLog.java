package io.travelook.broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.travelook.controller.chat.ChatController;
import io.travelook.controller.segnalazioni.SegnalazioniController;
import io.travelook.controller.utente.UtenteController;

public class ServerModerazioneLog {
	private static  SegnalazioniController contrchat=new SegnalazioniController() ;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerModerazioneLog() {
		
	}
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4005);
			while(true) {
				System.out.println("Il ServerChat è in attesa sulla porta " + serverSocket.getLocalPort());
				brokerSocket = serverSocket.accept();
				System.out.println("ServerChat:richiesta arrivata, lancio thread!");
				ServerModerazioneLogThread thread = new ServerModerazioneLogThread(brokerSocket,contrchat);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
