package io.travelook.serverchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.travelook.controller.chat.ChatController;
import io.travelook.controller.utente.UtenteController;

public class ServerChat {
	private static  ChatController contrchat=new ChatController() ;
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public ServerChat() {
	}
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4004);
			while(true) {
				System.out.println("Il ServerChat è in attesa sulla porta " + serverSocket.getLocalPort());
				brokerSocket = serverSocket.accept();
				System.out.println("ServerChat:richiesta arrivata, lancio thread!");
				ServerChatThread thread = new ServerChatThread(brokerSocket,contrchat);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
