package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public interface UtenteDAO {
	public boolean create(Utente u);
	
	public Utente read(int id);
	
	public boolean update(Utente u);
	
	public boolean delete(int id);
	
	public List<Utente> readUtentiFromDB();
	
	public List<Viaggio> readViaggiAttiviByUtente(Utente u);
	
	
}
