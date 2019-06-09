package io.travelook.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.travelook.model.Viaggio;

public class FiltraViaggioData implements Filtro {
    public FiltraViaggioData() {
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
	    List<Object> viaggif= new ArrayList<Object>();
	    List<Date> filtri=new ArrayList<Date>();
	    for(Object o : ogg) {
	    	Calendar c1 = Calendar.getInstance();
	    	c1.setTime((Date)o);
	    	filtri.add(c1.getTime());
	    }
	    for(Object o :viaggi) {
	    	Viaggio v =(Viaggio)o;
	    	for(Date s:filtri) {
	    		if(v.getDatainizio().before(s) && v.getDatafine().after(s)) {
	    			Object ob=(Object)v;
	    			viaggif.add(ob);
	    		}
	    	}
	    }
		return viaggif;
	}
}
