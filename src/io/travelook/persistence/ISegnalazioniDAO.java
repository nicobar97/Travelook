package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Segnalazione;

public interface ISegnalazioniDAO {
	
	public boolean create(Segnalazione s);
	public List<Segnalazione> readAll();
	public boolean update(Segnalazione s);

}
