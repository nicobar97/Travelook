package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Utente;
//
public interface IUtente_InteressiDAO {
	//CRUD
	public boolean create(Utente u, Interessi i);
	public List<Interessi> readInteressiByUtente(Utente u);
	public boolean delete(Utente u, Interessi i);
}
