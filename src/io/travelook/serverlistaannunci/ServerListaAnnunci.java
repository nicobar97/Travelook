package io.travelook.serverlistaannunci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.rdp.RichiesteObservableController;

public class ServerListaAnnunci {
	private static ListaAnnunciController listaAnnunciController = new ListaAnnunciController();
	private static  AnnuncioController annuncioController = new AnnuncioController();
	private static RichiesteObservableController richiesteController = new RichiesteObservableController();
	private static ServerSocket serverSocket;
	private static Socket brokerSocket;
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4001);
			while(true) {
				System.out.println("Il ServerListaAnnunci è in attesa sulla porta " + serverSocket.getLocalPort());
				brokerSocket = serverSocket.accept();
				System.out.println("ServerListaAnnunci:richiesta arrivata, lancio thread!");
				ServerListaAnnunciThread thread = new ServerListaAnnunciThread(brokerSocket,
						listaAnnunciController, annuncioController,
						richiesteController);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		

}
