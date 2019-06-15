package io.travelook.controller.filtro;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.filtro.Filtro;
import io.travelook.model.Viaggio;

public class FiltraViaggioLingua implements Filtro  {
	
    public FiltraViaggioLingua() {
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
	    List<String> filtri=new ArrayList<String>();
	    for(Object o : ogg) {
	    	filtri.add(String.valueOf(o));
	    }
	    for(Object o :viaggi) {
	    	Viaggio v =(Viaggio)o;
	    	for(String s:filtri) {
	    		if(v.getLingua().equals(s)) {
	    			Object ob=(Object)v;
	    			viaggif.add(ob);
	    		}
	    	}
	    }
		return viaggif;
	}

}
