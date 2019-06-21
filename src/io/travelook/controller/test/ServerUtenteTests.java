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
    List<Viaggio> viaggi=new ArrayList<Viaggio>();
    boolean test=true;
    boolean res=false;
    boolean res2=false;
    ClientProxy c=null;
    Interessi i = Interessi.CUCINA;
    Viaggio v=new Viaggio();
	try {
	c = new ClientProxy();
	viaggi=c.getListaAnnunci();
	v=c.getViaggioById(viaggi.get(0).getIdViaggio());
	}catch(Exception e )
	{
		e.printStackTrace();
	}
	Assert.assertEquals(2,viaggi.size());
	System.out.println("Test getListaUtenti superato !");
    System.out.println(v.getTitolo());
	
	
	}
}
