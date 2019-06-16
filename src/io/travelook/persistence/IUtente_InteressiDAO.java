package io.travelook.persistence;

import io.travelook.model.Interessi;
import io.travelook.model.Utente;
//
public interface IUtente_InteressiDAO {
	//CRUD
	public boolean create(Utente u, Interessi i);
	public boolean readInteressiByUtente(Utente u);
	public boolean delete(Utente u, Interessi i);
}
