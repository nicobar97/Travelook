package io.travelook.persistence;

import java.util.List;

import io.travelook.model.RichiestaDiPartecipazione;

public interface RichiestaDiPartecipazioneDAO {
	//PROSCIUTTO CRUD
	public void create(RichiestaDiPartecipazione rdp);

	public RichiestaDiPartecipazione read(int id);
	
	public boolean update(RichiestaDiPartecipazione rdp);

	public boolean delete(int id);
	
	//Utils
	
	public List<RichiestaDiPartecipazione> readRDPListFromDb();
	
	public boolean createTable();

	public boolean dropTable();
}
