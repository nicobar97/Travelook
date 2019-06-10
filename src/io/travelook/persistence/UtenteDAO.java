package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Utente;

public interface UtenteDAO {
	public void create(Utente u);
	
	public Utente read(int id);
	
	public void update(Utente u);
	
	public boolean delete(int id);
	
	public List<Utente> readUtentiFromDB();
	
	
}
