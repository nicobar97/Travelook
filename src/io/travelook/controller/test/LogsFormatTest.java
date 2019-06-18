package io.travelook.controller.test;

import java.util.Date;
import java.util.Calendar;

import io.travelook.model.Entry;

public class LogsFormatTest {
	
	public static void main(String[] args) {
	Date d = new Date();    
	Date ent=new Date(d.getTime());
	Entry e = new Entry (1,5,ent,"utente","aggiunta tubla nel db utente" );
	System.out.println("Formato Entry \n"+e.toString());
	}
}
