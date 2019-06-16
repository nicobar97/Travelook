package io.travelook.persistence;

import java.util.Optional;

import io.travelook.model.Utente;

public interface LoginDAO {
	
	public boolean create(String username, String hash);
	public Optional<String> read(String username);
	public boolean update(Utente u);
	public boolean delete(Utente u);

}
