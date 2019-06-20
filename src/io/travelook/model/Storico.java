package io.travelook.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Storico implements Serializable{
	private int idUtente;
	private List<Viaggio> viaggiPassati;
	
	public Storico(int idUtente) throws IllegalArgumentException {
		if(idUtente < 0)
			throw new IllegalArgumentException();
		this.idUtente = idUtente;
		this.viaggiPassati = new ArrayList<Viaggio>();
	}
    public Storico(int idUtente, List<Viaggio> viaggiPassati) throws IllegalArgumentException {
    	if(idUtente < 0 || viaggiPassati == null)
			throw new IllegalArgumentException();
    	this.idUtente = idUtente;
		this.viaggiPassati = viaggiPassati;
	}
    
    public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public List<Viaggio> getStorico() {
		return viaggiPassati;
	}
	public void setStorico(List<Viaggio> viaggiPassati) {
		this.viaggiPassati = viaggiPassati;
	}
	
	//Filtraggio temporaneo CERCHIAMO DI USARE LO STRATEGY ANCHE QUA perche tanto lavoriamo sempre con Viaggio object
	public List<Viaggio> filtraStoricoLingua(String lingua){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Viaggio v :viaggiPassati) {
			if(v.getLingua().equals(lingua)) {
				res.add(v);
			}
		}
		return res;
	}
	
	public List<Viaggio> filtraStoricoDest(String dest){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Viaggio v :viaggiPassati) {
			if(v.getDestinazione().equals(dest)) {
				res.add(v);
			}
		}
		return res;
	}
	
	public List<Viaggio> filtraStorico(Date t1,Date t2){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Viaggio v :viaggiPassati) {
			if((v.getDatainizio().compareTo(t1)>0)&&(v.getDatafine().compareTo(t2)<0)) {
				res.add(v);
			}
		}
		return res;
	}
    
}
