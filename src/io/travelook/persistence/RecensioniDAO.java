package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Recensione;
import io.travelook.model.Utente;

public interface RecensioniDAO {
	public boolean create(Recensione r);
	public boolean delete(Recensione r);
	public boolean update(Recensione r);
	public Recensione read(int id);
	
	public List<Recensione> readRecensioniUtente(Utente u);

}
