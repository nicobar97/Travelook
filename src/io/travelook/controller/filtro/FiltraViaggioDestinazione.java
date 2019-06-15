package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Viaggio;

public class FiltraViaggioDestinazione implements Filtro {
    
	 public FiltraViaggioDestinazione() {
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
	public List<Object> filtra(Object[] ogg, List<Object> o) {
		List<Object> viaggi= new ArrayList<Object>();
		List<String> destinazioni= new ArrayList<String>();
		 for(Object oggetto : ogg) {
		    	destinazioni.add(String.valueOf(oggetto));
		    }
		    for(Object oggetto :o) {
		    	Viaggio v =(Viaggio)o;
		    	for(String s:destinazioni) {
		    		if(v.getDestinazione().equals(s)) {
		    			Object ob=(Object)v;
		    			viaggi.add(ob);
		    		}
		    	}
		    }
			return viaggi;
	}

}
