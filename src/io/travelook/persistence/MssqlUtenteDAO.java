package io.travelook.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Utente;

public class MssqlUtenteDAO implements UtenteDAO {
	
	Connection conn;
	static final String table = "Utente";
	
	static final String insert = "INSERT INTO " + table + "(nickname, email, nome,"
			+ " cognome, dataNascita, imgProfilo) " + "VALUES(?,?,?,?,?,?)";
	
	static final String inserisci_hobby="INSERT INTO Utente_Hobby" + "(idUtente,idHobby) "
			+ "VALUES(?,?)";
	

	public MssqlUtenteDAO(Connection dbConnection) {
		conn = dbConnection;
	}

	@Override
	public void create(Utente u) {
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlUtenteDAO.insert);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, u.getUsername());
			prep_stmt.setString(2, u.getEmail());
			prep_stmt.setString(3, u.getNome());
			prep_stmt.setString(4, u.getCognome());
			prep_stmt.setDate(5, u.getDataNascita());
			prep_stmt.setString(6, u.getImmagineProfilo());
			prep_stmt.executeUpdate();
			prep_stmt.close();
			
			for(Interessi i : u.getInteressi()) {
				PreparedStatement prep_stmt2 = conn.prepareStatement(MssqlUtenteDAO.inserisci_hobby);
				prep_stmt2.setInt(1, i.ordinal());
				prep_stmt2.setInt(2, u.getId());
				prep_stmt2.executeQuery();
				prep_stmt2.close();
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Utente read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Utente u) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Utente> readUtentiFromDB() {
		// TODO Auto-generated method stub
		return null;
	}

}
