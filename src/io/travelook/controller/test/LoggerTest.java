package io.travelook.controller.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import io.travelook.controller.filtro.FiltroLogGiorno;
import io.travelook.controller.filtro.FiltroLogTipo;
import io.travelook.controller.logger.Logger;
import io.travelook.model.Entry;
import junit.framework.Assert;

public class LoggerTest  {
	public static void main(String[] args) {
     FiltroLogTipo ftipo= new FiltroLogTipo();
     Object[] filtrotipo= new Object[1];
     filtrotipo[0]="profilo";
     ftipo.setFiltri(filtrotipo);
     FiltroLogGiorno fgiorno=new FiltroLogGiorno();
     Object[] filtrogiorno= new Object[1];
     Calendar t1 =Calendar.getInstance();
     t1.set(2019,5,25);
     Date test=t1.getTime();
     filtrogiorno[0]=test;
     fgiorno.setFiltri(filtrogiorno);
     Calendar c1 =Calendar.getInstance();
     c1.set(2019,5,25,7,21,24);
     Date ts1=c1.getTime();
     Calendar c2 =Calendar.getInstance();
     c2.set(2019,7,21,5,20,17);
     Date ts2=c2.getTime();
     Entry e1= new Entry(1,4,ts1,"profilo","registrato utente");  
     Entry e2=new Entry(2,5,ts2,"utente","profilo");
     Entry e3=new Entry(3,4,ts1,"profilo","modificato il profilo");
     Logger l = new Logger("hello2.txt");  
     // testo la funzione di scrittura sul file 
     l.scriviEntry(e1);
     l.scriviEntry(e2);
     l.scriviEntry(e1);
     l.scriviEntry(e2);
     l.scriviEntry(e1);
     l.scriviEntry(e2);
     List<Entry> logs=new ArrayList<Entry>();
     logs=l.visualizzaLog();
     Assert.assertEquals(6,logs.size());
     System.out.println("test di lettura superato non filtrata!");
     for (Entry e : logs ) {
    	 System.out.println(e.toStringDisplay()+"\n") ;   	 
     }
     List<Entry> filtrati=l.visualizzaLog(ftipo);
     Assert.assertEquals(3,filtrati.size());
     System.out.println("test di lettura filtrata per tipo superato !");
     for (Entry e : filtrati) {
    	 System.out.println(e.toStringDisplay()+"\n") ;   	 
     }
     List<Entry> filtratigiorno=l.visualizzaLog(fgiorno);
     Assert.assertEquals(3,filtratigiorno.size());
     System.out.println("test di lettura filtrata per giorno superato !");
     for (Entry e : filtrati) {
    	 System.out.println(e.toStringDisplay()+"\n") ;   	 
     }
	}
}
