package io.travelook.broker;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RichiesteDispatcher implements IBroker {
    
	private EndPointServer serverViaggi;
	private EndPointServer serverProfilo;
	private EndPointServer serverLogin;
	private EndPointServer serverModerazioneLog;
	private EndPointServer serverChat;
	
	private HashMap<String,EndPointServer> serviziMap;
	private HashMap<EndPointClient,EndPointServer> connessioniMap;
    
	
	public RichiesteDispatcher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private boolean aggiungiConnessione(EndPointClient c, EndPointServer s ) {
		boolean res=false;
		if(c == null || s==null) {
			res=false;
		}
		connessioniMap.put(c,s);
		res=true;
		return res;
	}
	
	@Override
	public boolean registraServizio(String metodo, EndPointServer s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inoltraRichiesta(Richiesta r, EndPointServer s) {
		     boolean res =false;
		     EndPointClient c = new EndPointClient();
		     c.setIpclient(r.getIpMittente());
		     c.setPort(r.getPortaMittente());
		    /*inserisco una entry nella map connessioni per poter rispondere al client giusto */
		     connessioniMap.put(c,s);
		   //  JsonObject json = new JsonObject();
		     Socket socket = null;
		 	DataInputStream inputStream = null;
		 	DataOutputStream outputStream = null;
		 	try {
		 		socket = new Socket(s.getIpserver(),s.getPort());
		 		inputStream = new DataInputStream(socket.getInputStream());
		 		outputStream = new DataOutputStream(socket.getOutputStream());
		 	} catch (IOException e) {
		 		System.out.println("Errore: Creazione socket fallita.");
		 		e.printStackTrace();
		 		System.exit(1);
		 	}
		 	/*try (OutputStreamWriter out = new OutputStreamWriter(
		 	        outputStream, StandardCharsets.UTF_8)) {
		 	    //out.write(json.toString());
		 	}*/
		 	//cerco di serializzare la richiesta per inviarla all server s; 
		 	/*Object[] tosend=r.getArgomenti();
		 	for (Object o : tosend) {
		 		//outputStream.write();
		 	}
		 	*/
		 	return res; 
		     
	}
    
	public boolean EliminaServizio(EndPointServer s){
		return false; 
	}
	@Override
	public boolean inoltraRisposta(Risposta r, EndPointClient c) {
		// TODO Auto-generated method stub
		return false;
	}

}
