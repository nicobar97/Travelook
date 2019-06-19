package io.travelook.broker;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

public class ClientProva {
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Socket s = new Socket("localhost",4000);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		Object[] lista = new Object[2];
		Object a = new Object();
		lista[0] = a;
		lista[1] = a;
		Richiesta r = new Richiesta("local",123,lista,"ciao");
		Gson gson = new Gson();
		String stringaRichiesta = gson.toJson(r).toString();
		System.out.println(stringaRichiesta);
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(stringaRichiesta);
		s.close();
	}

}
