package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Viaggio;

public class FiltraViaggioBudget implements Filtro  {
	
	private Object[] filtri=null;
	
    public FiltraViaggioBudget() {
		super();
	}
	
    public List<Viaggio> convertToViaggi(List<Object> oggetti){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Object o :oggetti) {
			Viaggio v=(Viaggio)o;
			res.add(v);
		}
		return res;
	}
	
   public Object[] convertToObjectArray (List<Viaggio> viaggi){
	   int size=viaggi.size();
	   Object[] res= new Object[size];
	   int count=0;
		for(Viaggio v : viaggi) {
	        res[count]=(Object)v;
	        count++;
		}
		return res;
   }
	@Override
	public List<Object> filtra(Object[] ogg,List<Object> viaggi) {
		this.filtri=ogg;
	    List<Object> viaggif= new ArrayList<Object>();
	    List<Integer> filtri=new ArrayList<Integer>();
	    for(Object o : ogg) {
	    	filtri.add(Integer.parseInt(String.valueOf(o)));
	    }
	    for(Object o :viaggi) {
	    	Viaggio v =(Viaggio)o;
	    	for(Integer s:filtri) {
	    		if(v.getBudget()==s) {
	    			Object ob=(Object)v;
	    			viaggif.add(ob);
	    		}
	    	}
	    }
		return viaggif;
	}

	@Override
	public Object[] getFiltri() {
		return this.filtri;
	}

	public void setFiltri(Object[] filtri) {
		this.filtri = filtri;
	}
}
