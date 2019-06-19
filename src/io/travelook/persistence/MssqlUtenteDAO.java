package io.travelook.persistence;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Stato;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class MssqlUtenteDAO implements UtenteDAO {
	
	private static String read = "select id,nickname,email,nome,cognome,bio,dataNascita,"
			+ "imgProfilo from Utente where id=?";
	private Connection conn;
	static String table = "Utente";
	
	static String insert = "INSERT INTO " + table + "(nickname, email, nome,"
			+ " cognome, bio,dataNascita, imgProfilo) " + "VALUES(?,?,?,?,?,?,?)";
	
	static String inserisci_hobby="INSERT INTO Utente_Interessi" + "(idUtente,idinteresse) "
			+ "VALUES(?,?)";
	static String getlist = "select id,nickname,email,nome,cognome,bio,dataNascita,imgProfilo from Utente";
	
	static String delete = "delete from Utente where id=?";
	//static final String deletePartecipante = "delete from Viaggio where idPartecipante=?";
	
	static String update = "UPDATE Utente SET nickname=?, email=?, nome=?, cognome=?,bio=?,dataNascita=?,"
			+ "imgProfilo=? WHERE id=?";
	
	static String getIdUtente = "SELECT * FROM Utente WHERE nickname=?";
	

	public MssqlUtenteDAO(Connection dbConnection) {
		conn = dbConnection;
	}

	public MssqlUtenteDAO() {
		// TODO Auto-generated constructor stub
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean create(Utente u) {
		boolean esito = false;
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlUtenteDAO.insert);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, u.getUsername());
			prep_stmt.setString(2, u.getEmail());
			prep_stmt.setString(3, u.getNome());
			prep_stmt.setString(4, u.getCognome());
			prep_stmt.setString(5, u.getBio());
			prep_stmt.setDate(6, u.getDataNascita());
			prep_stmt.setString(7, u.getImmagineProfilo());
			if(prep_stmt.executeUpdate()>0) esito=true;
			prep_stmt.close();
			
			for(Interessi i : u.getInteressi()) {
				PreparedStatement prep_stmt2 = conn.prepareStatement(MssqlUtenteDAO.inserisci_hobby);
				prep_stmt2.setInt(1, i.ordinal());
				prep_stmt2.setInt(2, u.getId());
				prep_stmt2.executeQuery();
				prep_stmt2.close();
			}
			
			esito = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				return esito;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}

	@Override
	public Utente read(int id) {
		/*"select id,nickname,email,nome,cognome,bio,dataNascita,"
			+ "imgProfilo from Utente where id=?";*/
		Utente result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlUtenteDAO.read );
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
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
	public boolean update(Utente u) {
		if(u == null)
			return false;
		try {
			/**
			 * update = "UPDATE Utente SET nickname=?, email=?, nome=?, cognome=?,bio=?,dataNascita=?,"
			+ "imgProfilo=? WHERE id=?";
			 */
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.setString(1, u.getUsername());
			prep_stmt.setString(2, u.getEmail());
			prep_stmt.setString(3, u.getNome());
			prep_stmt.setString(4, u.getCognome());
			prep_stmt.setString(5, u.getBio());
			prep_stmt.setDate(6, u.getDataNascita());
			prep_stmt.setString(7, u.getImmagineProfilo());
			prep_stmt.setInt(8, u.getId());
			if(prep_stmt.executeUpdate()>0) {
				System.out.println("Aggiornato utente con id " + u.getId());
				return true;
			}
			else {
				System.out.println("Non ho potuto aggiornare utente con id " + u.getId());
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
	public boolean delete(int id) {
		if(id<0) {
			System.out.println("delete(): cannot delete an entry with negative id");
			return false;
		}
		
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.setInt(1, id);
			if(prep_stmt.executeUpdate()>0) {
			prep_stmt.close();
			System.out.println("Ho eliminato utente con id :" +id);
			return true;
			}
			else {
				System.out.println("Non ho eliminato utente con id : "+id);
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
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
	
	public int getIdUtenteByUsername(String username) {
		System.out.println("cerco id dell'utente "+username);
		int id=-1;
		 /*"SELECT * FROM Utente WHERE nickname=?";*/
	   try {
		   PreparedStatement prep_stmt = conn.prepareStatement(MssqlUtenteDAO.getIdUtente);
		   prep_stmt.clearParameters();
		   prep_stmt.setString(1, username);
		   ResultSet rs = prep_stmt.executeQuery();
		   if(rs.next())
			   id=rs.getInt(1);
		   prep_stmt.close();
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
		return id;
		
	}

	@Override
	public List<Utente> readUtentiFromDB() {
		/* "select id,nickname,email,nome,cognome,bio,dataNascita,imgProfilo from Utente";*/
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
                entry.setBio(rs.getString(i++));
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
	
	public Storico getStorico(Utente u){
		Storico storico = null;
		int idUtente = u.getId();
		List<Viaggio> viaggiPassati = new ArrayList<Viaggio>();
		storico.setIdUtente(idUtente);
		String query = "SELECT 	v.id " + 
				"FROM Viaggio as v INNER JOIN Richiesta_Di_Partecipazione as rdp ON rdp.idViaggio=v.id " + 
				"INNER JOIN Utente as u ON rdp.idUtente = u.id " + 
				"WHERE rdp.stato=0 AND u.id=? AND v.dataFine<=?";
		java.sql.Date dataCorrente = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, u.getId());
			prep_stmt.setDate(2, dataCorrente);
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				viaggiPassati.add(readViaggio(rs.getInt("id")));
				//
			}
		}
		catch(SQLException sqle) {
			return null;
		}
		
		
		storico.setStorico(viaggiPassati);
		return storico;
		
	}

	@Override
	public List<Viaggio> readViaggiAttiviByUtente(Utente u) {
		String query = "SELECT 	v.id " + 
				"FROM Viaggio as v INNER JOIN Richiesta_Di_Partecipazione as rdp ON rdp.idViaggio=v.id " + 
				"INNER JOIN Utente as u ON rdp.idUtente = u.id " + 
				"WHERE rdp.stato=0 AND u.id=?";
		try {
			System.out.println("Utente con id "+u.getId());
			List<Viaggio> listaViaggi = new ArrayList<Viaggio>();
			MssqlViaggioDAO viaggidb = new MssqlViaggioDAO();
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, u.getId());
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				System.out.println("trovato viaggio a cui partecipa");
				listaViaggi.add(readViaggio(rs.getInt("id")));
				
			}
			return listaViaggi;
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	public List<Viaggio> readViaggiInAttesaDiConfermaUtente(Utente u) {
		String query = "SELECT 	v.id " + 
				"FROM Viaggio as v INNER JOIN Richiesta_Di_Partecipazione as rdp ON rdp.idViaggio=v.id " + 
				"INNER JOIN Utente as u ON rdp.idUtente = u.id " + 
				"WHERE rdp.stato=2 AND u.id=?";
		try {
			System.out.println("Utente con id "+u.getId());
			List<Viaggio> listaViaggi = new ArrayList<Viaggio>();
			MssqlViaggioDAO viaggidb = new MssqlViaggioDAO();
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, u.getId());
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				System.out.println("trovato viaggio a cui partecipa");
				listaViaggi.add(readViaggio(rs.getInt("id")));
				
			}
			//
			return listaViaggi;
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}
	
	
	public Viaggio readViaggio(int id) {
		Viaggio res=new Viaggio();
		Utente c=new Utente();
		try {
			/*" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,dataPartenza,dataFine"+
			",immagineProfilo,immaginiAlternative"+*/
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.read_by_id);
			prep_stmt.setInt(1,id);
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(1));
				c.setId(rs.getInt(13));
				c.setUsername(rs.getString("nickname"));
				c.setEmail(rs.getString("email"));
				c.setNome(rs.getString("nome"));
				c.setCognome(rs.getString("cognome"));
				c.setBio(rs.getString("bio"));
				c.setDataNascita(rs.getDate("dataNascita"));
				c.setImmagineProfilo(rs.getString("imgProfilo"));
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
		return res;
	}
}
