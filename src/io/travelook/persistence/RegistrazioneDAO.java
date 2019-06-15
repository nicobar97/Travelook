package io.travelook.persistence;

import io.travelook.model.Utente;

public interface RegistrazioneDAO {
	
	public boolean create(String username, String hash);
	public Utente read(String username);
	public boolean update(Utente u);
	public boolean delete(Utente u);

}
