package io.travelook.controller;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.Filtro;
import io.travelook.model.Viaggio;

public class ListaAnnunciController {
	private Filtro f;
	private List<Viaggio> annunci;
	public ListaAnnunciController() {
		super();
		annunci = new ArrayList<Viaggio>();
	}

	
	
	public List<Viaggio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(List<Viaggio> annunci) {
		this.annunci = annunci;
	}

	public boolean creaAnnuncio(Viaggio v) {
		boolean res=false;
		if(v==null) {
			res=false;
			System.out.println("viaggio nullo !");
		}else {
			annunci.add(v);
			res=true;
		}
		return res;
	}
	
	public boolean eliminaAnnuncio(int idannuncio) {
		boolean res=false;
		if(idannuncio<0 ) {
			res=false;
			System.out.println("id negativo ! ");
		}
		else {
			for(Viaggio v : annunci) {
				if(v.getIdViaggio()==idannuncio) {
					annunci.remove(v);
					res=true;
				}
			}
		}
		return res;
	}
	
	public List<Viaggio> convertToViaggi(List<Object> oggetti){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Object o :oggetti) {
			Viaggio v=(Viaggio)o;
			res.add(v);
		}
		return res;
	}
	
   public List<Object> convertToObject (List<Viaggio> viaggi){
	   List<Object> res= new ArrayList<Object>();
		for(Viaggio v : viaggi) {
	        res.add(v);
		}
		return res;
   }


	public Viaggio visualizzaAnnuncio(int id) {
		Viaggio res=null; 
		for(Viaggio v:annunci ) {
			if(v.getIdViaggio()==id) {
				res=v;
			}
		}
		return res;
	}

	public Filtro getF() {
		return f;
	}

	public void setF(Filtro f) {
		this.f = f;
	}
}
