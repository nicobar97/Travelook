package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.model.Entry;

public class FiltroLogGiorno implements Filtro{
	
	private Object[] filtri=null;
	
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
	public FiltroLogGiorno() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Object> filtra(Object[] ogg, List<Object> entry) {
		this.filtri=ogg;
		List<Object> entryf = new ArrayList<Object>();
		List<Date> filtri=new ArrayList<Date>(); 
		for(Object obj : ogg) {
			if(obj instanceof Date) {
			Date d=(Date)obj;
			filtri.add(d);}
		}
		for(Object o : entry ) {
			if(o instanceof Entry) {
			  Entry e =(Entry)o;
			  for(Date data: filtri) {
				   if(e.getTimestamp().getDate()==data.getDate()) {
					entryf.add(e);
				    }
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
