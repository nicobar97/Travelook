package io.travelook.persistence;

import io.travelook.model.Utente;

public interface LoginDAO {
	
	public boolean create(String username, byte[] hash);
	public byte[] read(String username);
	public boolean update(Utente u);
	public boolean delete(Utente u);

}
