package servers;

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
import java.util.ArrayList;
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
		ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
		
		Richiesta<Object> r = (Richiesta<Object>)ois.readObject();
		String servizio = r.getServizio();
		System.out.println("Ricevuta Richiesta da " + clientSocket.getInetAddress() + " per il servizio "+ servizio);
		switch(servizio) {
		case "getListaAnnunci":
			List<Viaggio> listaViaggi = lac.getAnnunci();
			RispostaViaggi<Viaggio> reply = new RispostaViaggi<Viaggio>(clientSocket.getInetAddress().toString(),clientSocket.getPort(),listaViaggi);
			oos.writeObject(reply);
			break;
		/*case "creaAnnuncio":
			Richiesta<Viaggio> rich = gson.fromJson(stringRichiesta,Richiesta.class);
			Viaggio daCreare = rich.getArgomenti().get(0);
			boolean esito = lac.creaAnnuncio(daCreare);
			List<Boolean> listaB = new ArrayList<Boolean>();
			listaB.add(esito);
			RispostaViaggi<Boolean> replycrea = new RispostaViaggi<Boolean>(clientSocket.getInetAddress().toString(), clientSocket.getPort(),listaB);
			String replyJson2 = gson.toJson(replycrea);
			System.out.println(replyJson2);
			dos.writeUTF(replyJson2);
			break;*/
			
			
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
