package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.travelook.model.Entry;

public class FiltroLogGiorno implements Filtro{
 
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
	
	public FiltroLogGiorno() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Object> filtra(Object[] ogg, List<Object> entry) {
		List<Object> entryf = new ArrayList<Object>();
		List<Date> filtri=new ArrayList<Date>(); 
		for(Object obj : ogg) {
			Date d=(Date)obj;
			filtri.add(d);
		}
		for(Object o : entry ) {
			Entry e =(Entry)o;
			for(Date data: filtri) {
				if(e.getTimestamp().getDay()==data.getDay()) {
					Object res=(Object)e;
					entryf.add(res);
				}
			}
		}
		return entryf;
	}

}
