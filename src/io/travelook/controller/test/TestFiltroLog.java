package io.travelook.controller.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.travelook.controller.filtro.FiltroLogGiorno;
import io.travelook.controller.filtro.FiltroLogRange;
import io.travelook.controller.filtro.FiltroLogTipo;
import io.travelook.model.Entry;
import junit.framework.Assert;

public class TestFiltroLog {
	public static void main(String[] args) {
   List<Object> entry = new ArrayList<Object>();
   FiltroLogTipo filtrotipo=new FiltroLogTipo();
   FiltroLogGiorno filtrogiorno=new FiltroLogGiorno();
   FiltroLogRange filtrorange=new FiltroLogRange();
   Calendar c1 =Calendar.getInstance();
   c1.set(2019,5,25,7,21,24);
   Date ts1=c1.getTime();
   Calendar c2 =Calendar.getInstance();
   c2.set(2019,5,24,15,01,04);
   Date ts2=c2.getTime();
   Calendar c3 =Calendar.getInstance();
   c3.set(2019,6,19,18,32,04);
   Date ts3=c3.getTime();
   Calendar c4 =Calendar.getInstance();
   c4.set(2019,5,29,17,10,40);
   Date ts4=c4.getTime();
   Calendar c5=Calendar.getInstance();
   c5.set(2019,5,24);
   Date d5=c5.getTime();
   Calendar c7=Calendar.getInstance();
   c7.set(2019,5,23);
   Date d7=c7.getTime();
   Calendar c6=Calendar.getInstance();
   c6.set(2019,5,26);
   Date d6=c6.getTime();
   Date d = new Date();    
   Date ent=new Date(d.getTime());
   Entry e1= new Entry(1,4,ts1,"utente","registrato utente");
   Entry e2= new Entry(2,1,ts2,"viaggio","registrato viaggio");
   Entry e3= new Entry(3,3,ts3,"utente","loggato utente");
   Entry e4= new Entry(1,4,ts4,"utente","utente ha fatto richiesta di partecipare a un viaggio");
   Entry e5= new Entry(1,4,ent,"richiesta","richiestaeffettuata");
   String tipo="utente";
   Object[] filtritipo=new Object[1];
   Object[] filtridate=new Object[1];
   Object[] filtrod=new Object[2];
   filtritipo[0]=tipo;
   filtridate[0]=d5;
   filtrod[0]=d7;
   filtrod[1]=d6;	
   
   entry.add(e1);
   entry.add(e2);
   entry.add(e3);
   entry.add(e4);
   entry.add(e5);
   System.out.println("dimensione entry : "+entry.size());
   List<Entry> filtratepertipo=filtrotipo.convertToEntry(filtrotipo.filtra(filtritipo, entry));
   Assert.assertEquals(3,filtratepertipo.size());
   System.out.println("test tipo superato !");
   List<Entry> filtratidate=filtrogiorno.convertToEntry(filtrogiorno.filtra(filtridate, entry));
   Assert.assertEquals(1,filtratidate.size());
   System.out.println("test giorno superato !");
   List<Entry> filtratiperrange=filtrorange.convertToEntry(filtrorange.filtra(filtrod,entry));
   Assert.assertEquals(2,filtratiperrange.size());
   System.out.println("test range superato !");
   }
}
