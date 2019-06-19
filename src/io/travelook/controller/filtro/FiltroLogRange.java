package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.model.Entry;

public class FiltroLogRange implements Filtro {
    
	
	
	public FiltroLogRange() {
		super();
		// TODO Auto-generated constructor stub
	}


	public List<Entry> convertToEntry(List<Object> oggetti){
		List<Entry> res= new ArrayList<Entry>();
		for(Object o :oggetti) {
			Entry e=(Entry)o;
			res.add(e);
		}
		return res;
	}
	
  
   public Object[] convertToObjectArray (List<Entry> entry){
	   int size=entry.size();
	   Object[] res= new Object[size];
	   int count=0;
		for(Entry e : entry) {
	        res[count]=(Object)e;
	        count++;
		}
		return res;
   }
	
	@Override
	public List<Object> filtra(Object[] ogg, List<Object> entry) {
		List<Object> entryf= new ArrayList<Object>();
		int size=ogg.length;
		if(size!=2) {
			System.out.println("impossibile ricercare Log con un periodo comprendente 3 date !");
		}
		else {
		Date[] filtri= new Date[2];
		filtri[0]=(Date)ogg[0];
		filtri[1]=(Date)ogg[1];	
		   for(Object o :entry) {
			    Entry e =(Entry)o;
			        if(e.getTimestamp().after(filtri[0]) && e.getTimestamp().before(filtri[1])) {
				         Object res=(Object)e;
				         entryf.add(res);
			        }
		    }
		}
		return entryf;
	}

}