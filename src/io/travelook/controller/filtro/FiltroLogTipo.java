package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Entry;
import io.travelook.model.Viaggio;

public class FiltroLogTipo implements Filtro {
     
	 private Object[] filtri=null;
	
	  public FiltroLogTipo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public List<Entry> convertToEntry(List<Object> oggetti){
			List<Entry> res= new ArrayList<Entry>();
			for(Object o :oggetti) {
				if(o instanceof Entry) {
				Entry e=(Entry)o;
				res.add(e);}
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
		List<Object> entryfiltr= new ArrayList<Object>();
		List<String> filtri=new ArrayList<String>();
		for(Object obj : ogg ) {
			if(obj instanceof String)
			filtri.add((String)obj);
		}
		for(Object o:entry) {
			if(o instanceof Entry) {
			Entry e = (Entry)o;
			for(String s : filtri) {
				if(e.getTipo().equals(s)) {
					entryfiltr.add(e);
				}
			}
			}
			else {
				System.out.println("l'oggetto non è di tipo Entry !");
			}
		}
		return entryfiltr;
	}


	@Override
	public Object[] getFiltri() {
		return this.filtri;
	}


	public void setFiltri(Object[] filtri) {
		this.filtri = filtri;
	}

}
