package servers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import io.travelook.broker.Richiesta;
public class ServerViaggi extends Thread {
	
	
	public static void main(String[] args) throws Exception {
		
			ServerSocket sSock = new ServerSocket(4000);
			System.out.println("Il Server è in attesa sulla porta 4000.");
			while(true) {
			Socket socket = sSock.accept();
			
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String stringRichiesta = dis.readUTF();
			System.out.println("Ho ricevuto la richiesta");
			System.out.println(stringRichiesta);
			Gson gson = new Gson();
			Richiesta r = gson.fromJson(stringRichiesta, Richiesta.class);
			String servizio = r.getServizio();
			System.out.println("Ricevuta Richiesta da " + socket.getInetAddress() + " per il servizio "+ servizio);
			}
		
		
	}

}
