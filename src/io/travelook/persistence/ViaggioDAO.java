package io.travelook.persistence;

import java.util.List;

import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Viaggio;

public interface ViaggioDAO {
	  //PROSCIUTTO CRUD
		public boolean create(Viaggio viaggio);

		public Viaggio read(int id);
		
		public boolean update(Viaggio viaggio);

		public boolean delete(int id);
		
		//Utils
		
		public List<Viaggio> readViaggiListFromDb();
		
		public boolean createTable();

		public boolean dropTable();
}
