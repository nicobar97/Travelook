package io.travelook.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.model.Interessi;
import  io.travelook.model.Recensione;

public class Utente {
   
	private List<Interessi> interessi=new ArrayList<Interessi>();
	private Storico st= new Storico() ;
	private List<Recensione> recensioni=new ArrayList<Recensione>();
	private int id;
	private String email; 
	private String nome;
	private String cognome;
	private Date datadinascita;
	private String immagineprofilo;
	
	
}
