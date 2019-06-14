package io.travelook.controller.test;

import java.sql.Date;

import io.travelook.controller.AnnuncioController;
import io.travelook.controller.ListaAnnunciController;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class AnnuncioControllerTest {
   public static void main(String args[]) {
	     ListaAnnunciController controller = new ListaAnnunciController();
//	     Viaggio v =ann.getViaggio();
//	 	Utente u = new Utente(1, "nicobar", "nicobar97@gmail.com", "nicolo", "bartelucci",new Date(System.nanoTime()), "immagine1");
//		Utente c = new Utente(2, "nicsac", "nicolo.saccone.97@gmail.com", "nicolo", "saccone", new Date(System.nanoTime()), "immagine2");
//		Utente a = new Utente(3, "andresalv", "andrea.salvucci@studio.unibo.it", "andrea", "salvucci", new Date(System.nanoTime()), "immagine2");
//		
	     for(Viaggio v: controller.getAnnunci()) {
	    	 System.out.println(v.getTitolo());
	     }
   }
}
