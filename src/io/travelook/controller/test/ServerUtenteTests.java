package io.travelook.controller.test;

import java.util.ArrayList;
import java.util.List;

import io.travelook.broker.ClientProxy;
import io.travelook.model.Interessi;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import junit.framework.Assert;

public class ServerUtenteTests { 
	public static void main(String[] args) {
    List<Utente> utenti=new ArrayList<Utente>();
    boolean test=true;
    boolean res=false;
    boolean res2=false;
    ClientProxy c=null;
    Interessi i = Interessi.CUCINA;
    Utente u=new Utente();
    int id=1;
	try {
	c = new ClientProxy();
	utenti=c.getListaUtenti();
	u=c.getUtenteById(id);
	}catch(Exception e )
	{
		e.printStackTrace();
	}
	Assert.assertEquals(3,utenti.size());
	System.out.println("Test getListaUtenti superato !");
	System.out.println("Utente "+u.getId());
	}
}
