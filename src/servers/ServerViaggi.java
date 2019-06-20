package servers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;

import io.travelook.broker.Richiesta;
import io.travelook.broker.RispostaViaggi;
import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Viaggio;
public class ServerViaggi extends Thread {
	Socket clientSocket;
	
	public ServerViaggi(Socket s) {
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
		
		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
		String stringRichiesta = dis.readUTF();
		System.out.println("Ho ricevuto la richiesta");
		System.out.println(stringRichiesta);
		Gson gson = new Gson();
		Richiesta r = gson.fromJson(stringRichiesta, Richiesta.class);
		String servizio = r.getServizio();
		System.out.println("Ricevuta Richiesta da " + clientSocket.getInetAddress() + " per il servizio "+ servizio);
		switch(servizio) {
		case "getListaAnnunci":
			List<Viaggio> listaViaggi = lac.getAnnunci();
			RispostaViaggi reply = new RispostaViaggi(clientSocket.getInetAddress().toString(),clientSocket.getPort(), listaViaggi);
			String replyJSON = gson.toJson(reply);
			System.out.println(replyJSON);
			dos.writeUTF(replyJSON);
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
			ServerViaggi thread = new ServerViaggi(socket);
			thread.start();
			}
			
			
		
		
	

}
}
