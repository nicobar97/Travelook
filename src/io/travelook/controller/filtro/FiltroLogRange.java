package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.model.Entry;

public class FiltroLogRange implements Filtro {
    
	private Object[] filtri;
	
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
		this.filtri=ogg;
		List<Object> entryf= new ArrayList<Object>();
		int size=ogg.length;
		if(size!=2) {
			System.out.println("impossibile ricercare Log con un periodo comprendente 3 date !");
		}
		else {
		Date[] filtri= new Date[2];
		filtri[0]=(Date)ogg[0];
		filtri[1]=(Date)ogg[1];
		if(filtri[0].after(filtri[1])) {
			System.out.println("ricerca di range impossibile: la prima data deve essere quella più piccola ");
		}
		   for(Object o :entry) {
			    Entry e =(Entry)o;
			        if(e.getTimestamp().after(filtri[0]) && e.getTimestamp().before(filtri[1])) {
				         entryf.add(e);
			        }
		    }
		}
		return entryf;
	}


	@Override
	public Object[] getFiltri() {
		return this.filtri;
	}


	public void setFiltri(Object[] filtri) {
		this.filtri = filtri;
	}

}
