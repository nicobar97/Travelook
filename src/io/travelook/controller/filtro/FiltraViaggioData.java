package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import io.travelook.model.Viaggio;

public class FiltraViaggioData implements Filtro {
	private Object[] filtri=null;
	
    public FiltraViaggioData() {
		super();
	}
	
    public List<Viaggio> convertToViaggi(List<Object> oggetti){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Object o :oggetti) {
			if(o instanceof Viaggio) {
			Viaggio v=(Viaggio)o;
			res.add(v);
			}
		}
		return res;
	}
	
   public Object[] convertToObjectArray (List<Viaggio> viaggi){
	   int size=viaggi.size();
	   Object[] res= new Object[size];
	   int count=0;
		for(Viaggio v : viaggi) {
	        res[count]=v;
	        count++;
		}
		return res;
   }
   
	@Override
	public List<Object> filtra(Object[] ogg,List<Object> viaggi) {
	    List<Object> viaggif= new ArrayList<Object>();
	    List<Date> filtri=new ArrayList<Date>();
	    for(Object o : ogg) {
	    	if( o instanceof Date) {
            Date d1=(Date)o;
	    	filtri.add(d1);
	    	}
	    }
	    for(Object o :viaggi) {
	    	if(o instanceof Viaggio) {
	    	  Viaggio v =(Viaggio)o;
	    	     for(Date s:filtri) {
	    		     if(v.getDatainizio().before(s) && v.getDatafine().after(s)) {
	    			    Object ob=v;
	    			    viaggif.add(ob);
	    		     }
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
