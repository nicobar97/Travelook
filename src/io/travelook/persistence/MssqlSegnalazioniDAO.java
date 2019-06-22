package io.travelook.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Segnalazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class MssqlSegnalazioniDAO implements ISegnalazioniDAO {
	private Connection conn;
	
	public MssqlSegnalazioniDAO(Connection c) {
		conn=c;
	}

	public MssqlSegnalazioniDAO() {
		// TODO Auto-generated constructor stub
	}

	final String create = "INSERT INTO Segnalazione(idSegnalato, idSegnalante, Messaggio, stato)"
			+ " VALUES(?,?,?,?)";
	@Override
	public boolean create(Segnalazione s) {
		boolean esito=false;
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(create);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, s.getSegnalato().getId());
			prep_stmt.setInt(2, s.getSegnalante().getId());
			prep_stmt.setString(3, s.getMessaggio());
			prep_stmt.setInt(4, s.getStato().ordinal());
			if(prep_stmt.executeUpdate()>0) {
				return true;
			}
			else return false;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List<Segnalazione> readAll() {
		// TODO Auto-generated method stub
		List<Segnalazione> listaSegnalazioni = new ArrayList<Segnalazione>();
		Segnalazione s=new Segnalazione();
		
		String query = "SELECT * FROM Segnalazione";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				int i=1;
				s.setIdSegnalazione(rs.getInt(i++));
				s.setSegnalato(readUtente(rs.getInt(i++)));
				s.setSegnalante(readUtente(rs.getInt(i++)));
				s.setMessaggio(rs.getString(i++));
				s.setStato(Stato.values()[rs.getInt(i++)]);
				listaSegnalazioni.add(s);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return listaSegnalazioni;
	}
	public List<Segnalazione> readSegnalazioniUtente(int idUtente) {
		// TODO Auto-generated method stub
		List<Segnalazione> listaSegnalazioni = new ArrayList<Segnalazione>();
		Segnalazione s=new Segnalazione();
		
		String query = "SELECT * FROM Segnalazione WHERE idSegnalante=?";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			prep_stmt.setInt(1,idUtente);
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				int i=1;
				s.setIdSegnalazione(rs.getInt(i++));
				s.setSegnalato(readUtente(rs.getInt(i++)));
				s.setSegnalante(readUtente(rs.getInt(i++)));
				s.setMessaggio(rs.getString(i++));
				s.setStato(Stato.values()[rs.getInt(i++)]);
				listaSegnalazioni.add(s);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return listaSegnalazioni;
	}
	@Override
	public boolean update(Segnalazione s) {
		boolean esito=false;
		String query ="UPDATE Segnalazione SET idSegnalato=?,idSegnalante=?,messaggio=?,stato=? WHERE id=?";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			prep_stmt.setInt(1, s.getSegnalato().getIdUtente());
			prep_stmt.setInt(2, s.getSegnalante().getIdUtente());
			prep_stmt.setString(3, s.getMessaggio());
			prep_stmt.setInt(4,s.getStato().ordinal());
			prep_stmt.setInt(5, s.getIdSegnalazione());
			if(prep_stmt.executeUpdate()>0)
				esito=true;
			else return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return esito;
	}
	
	public boolean marcaSegnalazioneComeLetta(Segnalazione s) {
		boolean esito=false;
		String query ="UPDATE Segnalazione SET stato=7 WHERE id=?";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			prep_stmt.setInt(1, s.getIdSegnalazione());
			if(prep_stmt.executeUpdate()>0)
				return true;
			else return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean eliminaSegnalazione(int idsegn) {
		boolean res=false;
		String q="DELETE FROM Segnalazione WHERE id=?";
		try {
			PreparedStatement p=conn.prepareStatement(q);
			p.setInt(1,idsegn);
			if(p.executeUpdate()>0) {
				res=true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return res;
		}
		return res;
	}
	
	public Utente readUtente(int id) {
		String readUtenteById = "select id,nickname,email,nome,cognome,bio,dataNascita,"
				+ "imgProfilo from Utente where id=?";
		Utente result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(readUtenteById);
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
				u.setBio(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				result = u;
			}
			rs.close();
		}
		catch (Exception e) {
			System.out.println("read(): failed to retrieve entry with id = " + id+": "+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
