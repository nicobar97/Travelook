package io.travelook.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Recensione;
import io.travelook.model.Utente;

public class MssqlRecensioniDAO implements RecensioniDAO {
	
	private Connection conn;
	
    static final String TABLE_NAME= "Recensione";
	static final String create = "INSERT INTO " + TABLE_NAME + "(idRecensore,idRecensito,voto, titolo, corpo,foto)"
			+" VALUES(?,?,?,?,?,?)";
	static final String delete = "DELETE FROM " + TABLE_NAME + " WHERE idRecensore=? AND idRecensito=?";
	
	public MssqlRecensioniDAO(Connection c) {
		this.conn=c;
	}

	@Override
	public boolean create(Recensione r) {
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRecensioniDAO.create);
			prep_stmt.setInt(1, r.getIdUtenteRecensitore());
			prep_stmt.setInt(2, r.getIdUtenteRecensito());
			prep_stmt.setInt(3, r.getVoto());
			prep_stmt.setString(4, r.getTitolo());
			prep_stmt.setString(5, r.getCorpo());
			prep_stmt.setString(6, r.getFoto());
			int esito = prep_stmt.executeUpdate();
			prep_stmt.close();
			if(esito>0) {
				return true;
			}
			else return false;
			
			
		}
		catch(SQLException sqle) {
			//EVIDENTEMENTE O IL RECENSORE O IL RECENSITO NON ESISTONO NELLA TABELLA UTENTI
			sqle.printStackTrace();
			return false;
		}
		

	}

	@Override
	public boolean delete(Recensione r) {
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.setInt(1, r.getIdUtenteRecensitore());
			prep_stmt.setInt(2, r.getIdUtenteRecensito());
			int esito = prep_stmt.executeUpdate();
			prep_stmt.close();
			if(esito>0) return true;
			else return false;
			
		}
		catch(SQLException e) {
			return false;
		}

	}

	@Override
	public void update(Recensione r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void read(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Recensione> readRecensioniUtente(Utente u) {
		List<Recensione> listaRecensioni = new ArrayList<Recensione>();
		String query = "SELECT idRecensore, idRecensito, voto, corpo, titolo, foto FROM Recensione WHERE idRecensito=?";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			prep_stmt.setInt(1, u.getId());
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				Recensione r = new Recensione(rs.getInt("idRecensito"),rs.getInt("voto"),
						rs.getString("titolo"),rs.getString("corpo"),rs.getInt("idRecensore"));
				listaRecensioni.add(r);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return listaRecensioni;

	}

}
