package io.travelook.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.utils.StatoUtils;

public class MssqlRichiestaDiPartecipazioneDAO implements RichiestaDiPartecipazioneDAO {
	private Connection conn;
	static final String table = "Richiesta_Di_Partecipazione";
	static final String insert = "INSERT INTO " + table + 
			" (idUtente,idViaggio,idCreatore,messaggioRichiesta,messaggioRisposta,stato)" +
			" VALUES (?,?,?,?,?,?)";
	static final String read = "SELECT 	rdp.id, rdp.messaggioRichiesta, rdp.messaggioRisposta, rdp.stato, \n" + 
			"		rdp.idCreatore, c.nickname, c.email, c.nome, c.cognome,c.bio,c.dataNascita, c.imgProfilo,\n" + 
			"		rdp.idUtente, u.nickname, u.email, u.nome, u.cognome,u.bio, u.dataNascita, u.imgProfilo,\n" + 
			"		rdp.idViaggio, v.titolo, v.destinazione, v.descrizione, v.budget, v.luogoPartenza, v.dataPartenza, v.dataFine, v.immagineProfilo  FROM Richiesta_Di_Partecipazione AS rdp\n" + 
			"INNER JOIN Utente AS c ON c.id = rdp.idCreatore\n" + 
			"INNER JOIN Viaggio AS v ON v.id = rdp.idViaggio\n" + 
			"INNER JOIN Utente AS u ON u.id = rdp.idUtente\n" + 
			"WHERE rdp.id=?\n";
			
	static final String create = "create table " + table + " (" + 
			"     id int not null IDENTITY PRIMARY KEY," + 
			"     idUtente int not null," + 
			"     idViaggio int not null," + 
			"     idCreatore int not null," + 
			"     messaggioRichiesta char(100) not null," + 
			"     messaggioRisposta char(100)," + 
			"     stato int not null," + 
			"     primary key(id)," + 
			"     foreign key (idUtente) references Utente(id)," + 
			"     foreign key (idViaggio) references Viaggio(id)," + 
			"     foreign key (idCreatore) references Utente(id)," + 
			"     unique(id, idUtente, idViaggio, idCreatore)" + 
			"     );";
	static final String delete = "delete from table Richiesta_Di_Partecipazione where id=?";
	static final String update = "UPDATE Richiesta_Di_Partecipazione SET idUtente=?, idViaggio=?,"
			+ "idCreatore=?, messaggioRichiesta=?, messaggioRisposta=?, stato=? WHERE id=?";
	static final String rdp_creatore_viaggio = "SELECT 	rdp.id, rdp.messaggioRichiesta, rdp.messaggioRisposta, rdp.stato, \n" + 
			"		rdp.idCreatore, c.nickname, c.email, c.nome, c.cognome,c.bio, c.dataNascita, c.imgProfilo,\n" + 
			"		rdp.idUtente, u.nickname, u.email, u.nome, u.cognome,u.bio,u.dataNascita, u.imgProfilo,\n" + 
			"		rdp.idViaggio, v.titolo, v.destinazione, v.descrizione, v.budget, v.luogoPartenza, v.dataPartenza, v.dataFine, v.immagineProfilo  FROM Richiesta_Di_Partecipazione AS rdp\n" + 
			"INNER JOIN Utente AS c ON c.id = rdp.idCreatore\n" + 
			"INNER JOIN Viaggio AS v ON v.id = rdp.idViaggio\n" + 
			"INNER JOIN Utente AS u ON u.id = rdp.idUtente\n" + 
			"WHERE rdp.idCreatore=? AND rdp.idViaggio=?";
	static final String rdp_creatore = "SELECT 	rdp.id, rdp.messaggioRichiesta, rdp.messaggioRisposta, rdp.stato, \n" + 
			"		rdp.idCreatore, c.nickname, c.email, c.nome, c.cognome,c.bio, c.dataNascita, c.imgProfilo,\n" + 
			"		rdp.idUtente, u.nickname, u.email, u.nome, u.cognome,u.bio, u.dataNascita, u.imgProfilo,\n" + 
			"		rdp.idViaggio, v.titolo, v.destinazione, v.descrizione, v.budget, v.luogoPartenza, v.dataPartenza, v.dataFine, v.immagineProfilo  FROM Richiesta_Di_Partecipazione AS rdp\n" + 
			"INNER JOIN Utente AS c ON c.id = rdp.idCreatore\n" + 
			"INNER JOIN Viaggio AS v ON v.id = rdp.idViaggio\n" + 
			"INNER JOIN Utente AS u ON u.id = rdp.idUtente\n" + 
			"WHERE rdp.idCreatore=?";
	static final String rdp_all = "SELECT 	rdp.id, rdp.messaggioRichiesta, rdp.messaggioRisposta, rdp.stato, \n" + 
			"		rdp.idCreatore, c.nickname, c.email, c.nome, c.cognome,c.bio, c.dataNascita, c.imgProfilo,\n" + 
			"		rdp.idUtente, u.nickname, u.email, u.nome, u.cognome,u.bio, u.dataNascita, u.imgProfilo,\n" + 
			"		rdp.idViaggio, v.titolo, v.destinazione, v.descrizione, v.budget, v.luogoPartenza, v.dataPartenza, v.dataFine, v.immagineProfilo  FROM Richiesta_Di_Partecipazione AS rdp\n" + 
			"INNER JOIN Utente AS c ON c.id = rdp.idCreatore\n" + 
			"INNER JOIN Viaggio AS v ON v.id = rdp.idViaggio\n" + 
			"INNER JOIN Utente AS u ON u.id = rdp.idUtente\n";
	public MssqlRichiestaDiPartecipazioneDAO(Connection conn) {
		this.conn = conn;
	}

	public MssqlRichiestaDiPartecipazioneDAO() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void create(RichiestaDiPartecipazione rdp) {
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRichiestaDiPartecipazioneDAO.insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, rdp.getUtente().getId());
			prep_stmt.setInt(2, rdp.getViaggio().getIdViaggio());
			prep_stmt.setInt(3, rdp.getViaggio().getCreatore().getId());
			prep_stmt.setString(4, rdp.getMessaggioRichiesta());
			prep_stmt.setString(5, rdp.getMessaggioRisposta());
			prep_stmt.setInt(6, StatoUtils.getStatoId(rdp.getStato()));
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public RichiestaDiPartecipazione read(int id) {
		RichiestaDiPartecipazione result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRichiestaDiPartecipazioneDAO.read);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			/*
			 * 	rdp.id, rdp.messaggioRichiesta, rdp.messaggioRisposta, rdp.stato, \n" + 
			"	rdp.idCreatore, c.nickname, c.email, c.nome, c.cognome, c.dataNascita, c.imgProfilo,\n" + 
			"	rdp.idUtente, u.nickname, u.email, u.nome, u.cognome,u.bio, u.dataNascita, u.imgProfilo,\n" + 
			"	rdp.idViaggio, v.titolo, v.destinazione, v.descrizione, v.budget, v.luogoPartenza, 
				v.dataPartenza, v.dataFine, v.immagineProfilo 
			 */
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				RichiestaDiPartecipazione rdp = new RichiestaDiPartecipazione();
				int i=1;
				rdp.setId(rs.getInt(i++));
				rdp.setMessaggioRichiesta(rs.getString(i++));
				rdp.setRisposta(rs.getString(i++));
				rdp.setStato(Stato.values()[rs.getInt(i++)]);
				Utente c = new Utente();
				c.setId(rs.getInt(i++));
				c.setUsername(rs.getString(i++));
				c.setEmail(rs.getString(i++));
				c.setNome(rs.getString(i++));
				c.setCognome(rs.getString(i++));
				c.setBio(rs.getString(i++));
				c.setDataNascita(rs.getDate(i++));
				c.setImmagineProfilo(rs.getString(i++));
				Utente u = new Utente();
				u.setId(rs.getInt(i++));
				u.setUsername(rs.getString(i++));
				u.setEmail(rs.getString(i++));
				u.setNome(rs.getString(i++));
				u.setCognome(rs.getString(i++));
				u.setBio(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				rdp.setUtente(u);
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(i++));
				v.setTitolo(rs.getString(i++));
				v.setDestinazione(rs.getString(i++));
				v.setDescrizione(rs.getString(i++));
				v.setBudget(rs.getInt(i++));
				v.setLuogopartenza(rs.getString(i++));
				v.setDatainizio(rs.getDate(i++));
				v.setDatafine(rs.getDate(i++));
				v.setImmaginiProfilo(rs.getString(i++));
				v.setCreatore(c);
				rdp.setViaggio(v);
				result = rdp;
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
	public boolean update(RichiestaDiPartecipazione rdp) {
		boolean result = false;
		if ( rdp == null )  {
			System.out.println( "update(): failed to update a null entry");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRichiestaDiPartecipazioneDAO.update);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, rdp.getUtente().getId());
			prep_stmt.setInt(2, rdp.getViaggio().getIdViaggio());
			prep_stmt.setInt(3, rdp.getViaggio().getCreatore().getId());
			prep_stmt.setString(4, rdp.getMessaggioRichiesta());
			prep_stmt.setString(5, rdp.getMessaggioRisposta());
			prep_stmt.setInt(6, StatoUtils.getStatoId(rdp.getStato()));
			prep_stmt.setInt(7, rdp.getId());
			int esito;
			esito=prep_stmt.executeUpdate();
			prep_stmt.close();
			if(esito>=0) {
				System.out.println("Ho aggiornato il rdp con id "+ rdp.getId());
				result = true;
			}
			else {
				System.out.println("ERRORE: non ho potuto aggiornare il rdp con id "+ rdp.getId());
				result = false;
			}
			
		}
		catch (Exception e) {
			System.out.println("insert(): failed to update entry: "+e.getMessage());
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
	public boolean delete(int id) {
		boolean res=false;
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRichiestaDiPartecipazioneDAO.delete);
			prep_stmt.setInt(1,id);
			int resint=prep_stmt.executeUpdate();
			res = (resint > 0) ? true : false;
			prep_stmt.close();			
		}catch(Exception e) {
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
	public List<RichiestaDiPartecipazione> readRDPListFromDb() {
		List<RichiestaDiPartecipazione> result = new ArrayList<RichiestaDiPartecipazione>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRichiestaDiPartecipazioneDAO.rdp_all);
			prep_stmt.clearParameters();

			ResultSet rs = prep_stmt.executeQuery();
			while ( rs.next() ) {
				RichiestaDiPartecipazione rdp = new RichiestaDiPartecipazione();
				int i=1;
				rdp.setId(rs.getInt(i++));
				rdp.setMessaggioRichiesta(rs.getString(i++));
				rdp.setRisposta(rs.getString(i++));
				rdp.setStato(Stato.values()[rs.getInt(i++)]);
				Utente c = new Utente();
				c.setId(rs.getInt(i++));
				c.setUsername(rs.getString(i++));
				c.setEmail(rs.getString(i++));
				c.setNome(rs.getString(i++));
				c.setCognome(rs.getString(i++));
				c.setBio(rs.getString(i++));
				c.setDataNascita(rs.getDate(i++));
				c.setImmagineProfilo(rs.getString(i++));
				Utente u = new Utente();
				u.setId(rs.getInt(i++));
				u.setUsername(rs.getString(i++));
				u.setEmail(rs.getString(i++));
				u.setNome(rs.getString(i++));
				u.setCognome(rs.getString(i++));
				u.setBio(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				rdp.setUtente(u);
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(i++));
				v.setTitolo(rs.getString(i++));
				v.setDestinazione(rs.getString(i++));
				v.setDescrizione(rs.getString(i++));
				v.setBudget(rs.getInt(i++));
				v.setLuogopartenza(rs.getString(i++));
				v.setDatainizio(rs.getDate(i++));
				v.setDatafine(rs.getDate(i++));
				v.setImmaginiProfilo(rs.getString(i++));
				v.setCreatore(c);
				rdp.setViaggio(v);
				result.add( rdp );
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
	public List<RichiestaDiPartecipazione> readRDPForCreatoreViaggio(Utente creatore, Viaggio viaggio) {
		List<RichiestaDiPartecipazione> result = new ArrayList<RichiestaDiPartecipazione>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRichiestaDiPartecipazioneDAO.rdp_creatore_viaggio);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, creatore.getId());
			prep_stmt.setInt(2, viaggio.getIdViaggio());
			ResultSet rs = prep_stmt.executeQuery();
			while ( rs.next() ) {
				RichiestaDiPartecipazione rdp = new RichiestaDiPartecipazione();
				int i=1;
				rdp.setId(rs.getInt(i++));
				rdp.setMessaggioRichiesta(rs.getString(i++));
				rdp.setRisposta(rs.getString(i++));
				rdp.setStato(Stato.values()[rs.getInt(i++)]);
				Utente c = new Utente();
				c.setId(rs.getInt(i++));
				c.setUsername(rs.getString(i++));
				c.setEmail(rs.getString(i++));
				c.setNome(rs.getString(i++));
				c.setCognome(rs.getString(i++));
				c.setBio(rs.getString(i++));
				c.setDataNascita(rs.getDate(i++));
				c.setImmagineProfilo(rs.getString(i++));
				Utente u = new Utente();
				u.setId(rs.getInt(i++));
				u.setUsername(rs.getString(i++));
				u.setEmail(rs.getString(i++));
				u.setNome(rs.getString(i++));
				u.setCognome(rs.getString(i++));
				u.setBio(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				rdp.setUtente(u);
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(i++));
				v.setTitolo(rs.getString(i++));
				v.setDestinazione(rs.getString(i++));
				v.setDescrizione(rs.getString(i++));
				v.setBudget(rs.getInt(i++));
				v.setLuogopartenza(rs.getString(i++));
				v.setDatainizio(rs.getDate(i++));
				v.setDatafine(rs.getDate(i++));
				v.setImmaginiProfilo(rs.getString(i++));
				v.setCreatore(c);
				rdp.setViaggio(v);
				result.add( rdp );
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
	public List<RichiestaDiPartecipazione> readRDPForCreatore(Utente creatore) {
		List<RichiestaDiPartecipazione> result = new ArrayList<RichiestaDiPartecipazione>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlRichiestaDiPartecipazioneDAO.rdp_creatore);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, creatore.getId());
			ResultSet rs = prep_stmt.executeQuery();
			while ( rs.next() ) {
				RichiestaDiPartecipazione rdp = new RichiestaDiPartecipazione();
				int i=1;
				rdp.setId(rs.getInt(i++));
				rdp.setMessaggioRichiesta(rs.getString(i++));
				rdp.setRisposta(rs.getString(i++));
				rdp.setStato(Stato.values()[rs.getInt(i++)]);
				Utente c = new Utente();
				c.setId(rs.getInt(i++));
				c.setUsername(rs.getString(i++));
				c.setEmail(rs.getString(i++));
				c.setNome(rs.getString(i++));
				c.setCognome(rs.getString(i++));
				c.setBio(rs.getString(i++));
				c.setDataNascita(rs.getDate(i++));
				c.setImmagineProfilo(rs.getString(i++));
				Utente u = new Utente();
				u.setId(rs.getInt(i++));
				u.setUsername(rs.getString(i++));
				u.setEmail(rs.getString(i++));
				u.setNome(rs.getString(i++));
				u.setCognome(rs.getString(i++));
				u.setBio(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				rdp.setUtente(u);
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(i++));
				v.setTitolo(rs.getString(i++));
				v.setDestinazione(rs.getString(i++));
				v.setDescrizione(rs.getString(i++));
				v.setBudget(rs.getInt(i++));
				v.setLuogopartenza(rs.getString(i++));
				v.setDatainizio(rs.getDate(i++));
				v.setDatafine(rs.getDate(i++));
				v.setImmaginiProfilo(rs.getString(i++));
				v.setCreatore(c);
				rdp.setViaggio(v);
				result.add( rdp );
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
	@Override
	public boolean createTable() {
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(create);
			result = true;
			stmt.close();
		}
		catch (Exception e) {
			System.out.println("createTable(): failed to create table 'richiestadipart': "+e.getMessage());
		}
		finally {
			
		}
		return result;
	}
	@Override
	public boolean dropTable() {
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("DROP TABLE " + table);
			result = true;
			stmt.close();
		}
		catch (Exception e) {
			System.out.println("dropTable(): failed to drop table '"+table+"': "+e.getMessage());
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

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
}
