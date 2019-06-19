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
	static final String update = "UPDATE"+TABLE_NAME +"SET voto=?,corpo=?,titolo=?,risposta=?,foto=?"+
	"WHERE id=?";
	static final String read = " SELECT * FROM "+TABLE_NAME+"WHERE id=?";
	public MssqlRecensioniDAO(Connection c) {
		this.conn=c;
	}

	public MssqlRecensioniDAO() {
		// TODO Auto-generated constructor stub
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
	public boolean update(Recensione r) {
		// TODO Auto-generated method stub
		if(r == null)
			return false;
		try {
			/**
			update = "UPDATE"+TABLE_NAME +"SET voto=?,corpo=?,titolo=?,risposta=?,foto=?"+
	        "WHERE id=?";*/
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.setInt(1,r.getVoto());
			prep_stmt.setString(2,r.getCorpo());
			prep_stmt.setString(3,r.getTitolo());
			prep_stmt.setString(4,r.getRisposta());
			prep_stmt.setString(5,r.getFoto());
			prep_stmt.setInt(7,r.getId());
			if(prep_stmt.executeUpdate()>0) {
				System.out.println("Aggiornata recensione con id " + r.getId());
				return true;
			}
			else {
				System.out.println("Non ho potuto aggiornare la recensione con id " + r.getId());
				return false;
			}
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	@Override
	public Recensione read(int id) {
		// TODO Auto-generated method stub
		//" SELECT * FROM "+TABLE_NAME+"WHERE id=?"
		Recensione res = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return res;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read );
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			/*
			select id,nickname,email,nome,cognome,dataNascita,"
			+ "imgProfilo from Utente where id=? 
			 */
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				Recensione r = new Recensione(); 
				int i=1;
				r.setId(id);
				r.setIdUtenteRecensitore(rs.getInt(i++));
				r.setIdUtenteRecensito(rs.getInt(i++));
			    r.setVoto(rs.getInt(i++));
				r.setCorpo(rs.getString(i++));
				r.setTitolo(rs.getString(i++));
				r.setRisposta(rs.getString(i++));
				r.setFoto(rs.getString(i++));
				res = r;
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("read(): failed to retrieve entry with id = " + id+": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
		

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
