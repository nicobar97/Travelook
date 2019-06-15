package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Messaggio;

public interface MessaggioDAO {
	
	public void create(Messaggio u);
	
	//public Utente read(int id);
	
	//public boolean update(Utente u);
	
	//public boolean delete(int id);
	
	public List<Messaggio> readChatForViaggio(int idViaggio);
}
