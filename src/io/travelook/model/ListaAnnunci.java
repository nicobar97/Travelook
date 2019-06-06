package io.travelook.model;

import java.util.ArrayList;
import java.util.List;

public class ListaAnnunci { //DA RIMUOVERE
	private List<Viaggio> annunci = new ArrayList<Viaggio>();

	public ListaAnnunci(List<Viaggio> annunci) {
		this.annunci = annunci;
	}

	public List<Viaggio> getAnnunci() {
		return annunci;
	}
	public void setAnnunci(List<Viaggio> annunci) {
		this.annunci = annunci;
	}
	
	public Viaggio getViaggio(int idViaggio) {
		for(Viaggio v : this.annunci)
			if(v.getIdViaggio() == idViaggio)
				return v;
		return null;
	}
}
