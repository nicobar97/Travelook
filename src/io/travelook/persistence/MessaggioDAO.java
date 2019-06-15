package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Viaggio;

public interface MessaggioDAO {
	
	public void create(Messaggio m, int idViaggio );
	
	//public Utente read(int id);
	
	//public boolean update(Utente u);
	
	//public boolean delete(int id);
	
	public Chat readChatForViaggio(Viaggio v);
	public List<Chat> readAllChat();
}
