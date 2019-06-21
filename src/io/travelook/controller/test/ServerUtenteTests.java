package io.travelook.controller.test;

import java.util.ArrayList;
import java.util.List;

import io.travelook.broker.ClientProxy;
import io.travelook.model.Utente;
import junit.framework.Assert;

public class ServerUtenteTests { 
	public static void main(String[] args) {
    List<Utente> utenti=new ArrayList<Utente>();
    ClientProxy c=null;
	try {
	c = new ClientProxy();
	utenti=c.getListaUtenti();
	}catch(Exception e )
	{
		e.printStackTrace();
	}
	Assert.assertEquals(3,utenti.size());
	System.out.println("Test getListaUtenti superato !");
	
	}
}
