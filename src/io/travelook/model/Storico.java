package io.travelook.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import  io.travelook.model.Viaggio;

public class Storico {
    public Storico(List<Viaggio> viaggi) {
		super();
		this.viaggi = viaggi;
	}

	public Storico() {
		super();
		// TODO Auto-generated constructor stub
	}

	private List<Viaggio> viaggi = new ArrayList<Viaggio>();
    
	public List<Viaggio> filtraStoricoLingua(String lingua){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Viaggio v :viaggi) {
			if(v.getLingua().equals(lingua)) {
				res.add(v);
			}
		}
		return res;
	}
	
	public List<Viaggio> filtraStoricoDest(String dest){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Viaggio v :viaggi) {
			if(v.getDestinazione().equals(dest)) {
				res.add(v);
			}
		}
		return res;
	}
	
	public List<Viaggio> filtraStorico(Date t1,Date t2){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Viaggio v :viaggi) {
			if((v.getDatainizio().compareTo(t1)>0)&&(v.getDatafine().compareTo(t2)<0)) {
				res.add(v);
			}
		}
		return res;
	}
    
}
