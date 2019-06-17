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

	final String create = "INSERT INTO Segnalazione(idSegnalato, idSegnalante, idViaggio, Messaggio, stato)"
			+ " VALUES(?,?,?,?,'DALEGGERE')";
	@Override
	public boolean create(Segnalazione s) {
		boolean esito=false;
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(this.create);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, s.getSegnalato().getId());
			prep_stmt.setInt(2, s.getSegnalante().getId());
			prep_stmt.setInt(3, s.getViaggio().getIdViaggio());
			prep_stmt.setString(4, s.getMessaggio());
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
		Segnalazione s=null;
		
		String query = "SELECT * FROM Segnalazione WHERE stato='DALEGGERE'";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				s.setIdSegnalazione(rs.getInt("id"));
				s.setMessaggio(rs.getString("messaggio"));
				s.setSegnalante(readUtente(rs.getInt("idSegnalante")));
				s.setSegnalato(readUtente(rs.getInt("idSegnalato")));
				s.setViaggio(readViaggio(rs.getInt("idViaggio")));
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return listaSegnalazioni;
	}

	@Override
	public boolean update(Segnalazione s) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean marcaSegnalazioneComeLetta(Segnalazione s) {
		boolean esito=false;
		String query ="UPDATE Segnalazione (stato='LETTA') WHERE id=?";
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
	
	public Viaggio readViaggio(int id) {
		Viaggio res=null;
		try {
			/*" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,dataPartenza,dataFine"+
			",immagineProfilo,immaginiAlternative"+*/
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.read_by_id);
			prep_stmt.setInt(1,id);
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(1));
				int idU=rs.getInt(13);
				String user=rs.getString("nickname");
				String mail=rs.getString("email");
				String nome=rs.getString("nome");
				String cgnome=rs.getString("cognome");
				Date datan=rs.getDate("dataNascita");
				String imgP=rs.getString("imgProfilo");
				Utente c = new Utente(idU,user,mail,nome,cgnome,datan,imgP);
				v.setCreatore(c);
				v.setTitolo(rs.getString("titolo"));
				v.setDestinazione(rs.getString("destinazione"));
				v.setDescrizione(rs.getString("descrizione"));
				v.setLingua(rs.getString("lingua"));
				v.setBudget(rs.getInt("budget"));
				v.setLuogopartenza(rs.getString("luogoPartenza"));
				v.setDatainizio(rs.getDate("dataPartenza"));
				v.setDatafine(rs.getDate("dataFine"));
				v.setStato(Stato.values()[rs.getInt("stato")]);
				res=v;
			}
			rs.close();
			prep_stmt.close();
		    
		}
		catch(Exception e) {
			
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
	
	public Utente readUtente(int id) {
		String readUtenteById = "select id,nickname,email,nome,cognome,dataNascita,"
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

}
