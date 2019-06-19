package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Entry;
import io.travelook.model.Viaggio;

public class FiltroLogTipo implements Filtro {
     
	  public FiltroLogTipo() {
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
		List<Object> entryfiltr= new ArrayList<Object>();
		List<String> filtri=new ArrayList<String>();
		for(Object obj : ogg ) {
			filtri.add(String.valueOf(ogg));
		}
		for(Object o:entry) {
			Entry e = (Entry)o;
			for(String s : filtri) {
				if(e.getTipo().equals(s)) {
					Object entr=(Object)e;
					entryfiltr.add(entr);
				}
			}
		}
		return entryfiltr;
	}

}
