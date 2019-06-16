package io.travelook.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.travelook.model.Interessi;
import io.travelook.model.Utente;

public class MssqlUtente_InteressiDAO implements IUtente_InteressiDAO{
	Connection conn;
	
	public MssqlUtente_InteressiDAO(Connection c) {
		conn=c;
	}

	@Override
	public boolean create(Utente u, Interessi i) {
		/*Aggiunge un interesse all'utente e quindi aggiunge una tupla
		 * alla tabella che mappa gli interessi
		 */
		boolean esito = false;
		String query1 = "SELECT id from Utente WHERE nickname= ?";
		int id = -1;
		
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query1);
			prep_stmt.setString(1, u.getUsername());
			System.out.println(u.getUsername());
			ResultSet rs = prep_stmt.executeQuery();
			if(rs.next()) {
				id= rs.getInt(1);
			}
			else return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		if(id<0) return false;
		int idInteresse = i.ordinal()+1;
		
		String queryAdd = "INSERT INTO Utente_Interesse(idUtente,idInteresse) VALUES(?,?)";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(queryAdd);
			prep_stmt.setInt(1, id);
			prep_stmt.setInt(2, idInteresse);
			int righeAggiunte = prep_stmt.executeUpdate();
			if(righeAggiunte==0) return false;
			else return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean readInteressiByUtente(Utente u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Utente u, Interessi i) {
		// TODO Auto-generated method stub
		return false;
	}

}
