package io.travelook.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Utente;

public class MssqlUtenteDAO implements UtenteDAO {
	
	private static String read = "select id,nickname,email,nome,cognome,dataNascita,"
			+ "imgProfilo from Utente where id=?";
	Connection conn;
	static final String table = "Utente";
	
	static final String insert = "INSERT INTO " + table + "(nickname, email, nome,"
			+ " cognome, dataNascita, imgProfilo) " + "VALUES(?,?,?,?,?,?)";
	
	static final String inserisci_hobby="INSERT INTO Utente_Hobby" + "(idUtente,idHobby) "
			+ "VALUES(?,?)";
	static final String getlist = "select id,nickname,email,nome,cognome,dataNascita,imgProfilo from Utente";
	

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
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Utente read(int id) {
		Utente result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlUtenteDAO.read );
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			/*
			select id,nickname,email,nome,cognome,dataNascita,"
			+ "imgProfilo from Utente where id=? 
			 */
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				Utente u = new Utente(); 
				int i=1;
				u.setId(rs.getInt(i++));
				u.setUsername(rs.getString(i++));
				u.setEmail(rs.getString(i++));
				u.setNome(rs.getString(i++));
				u.setCognome(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				result = u;
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
		return result;
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
		List<Utente> result = new ArrayList<Utente>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlUtenteDAO.getlist);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while ( rs.next() ) {
				Utente entry = new Utente();
				int i=1;
				entry.setId(rs.getInt(i++));
				entry.setUsername(rs.getString(i++));
				entry.setEmail(rs.getString(i++));
				entry.setNome(rs.getString(i++));
				entry.setCognome(rs.getString(i++));
				entry.setDataNascita(rs.getDate(i++));
				entry.setImmagineProfilo(rs.getString(i++));
				result.add( entry );
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("find(): failed to retrieve entry " +e.getMessage());
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
		return result;
	}

}
