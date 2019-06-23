package io.travelook.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class MssqlMessaggioDAO implements MessaggioDAO {
	private Connection conn = null;
	public static String insert = "insert into Messaggio (idUtente, idViaggio, timestamp, body) values (?,?,?,?)";
	public static String readChatForViaggio = "select m.id, m.body, m.timestamp,  m.idUtente, u.nickname, u.email, u.nome, u.cognome,u.bio, u.dataNascita, u.imgProfilo from Messaggio m inner join Utente as u on u.id = m.idUtente where idViaggio=?";
	public static String readAll = " select m.id, m.body, m.timestamp,  m.idUtente, u.nickname, u.email, u.nome, u.cognome,u.bio, u.dataNascita, u.imgProfilo,\n" + 
			"    m.idViaggio,   v.titolo, v.destinazione, v.descrizione, v.budget, v.luogoPartenza, v.dataPartenza, v.dataFine, v.lingua, v.stato, v.immagineProfilo\n" + 
			"    from Messaggio m \n" + 
			"    inner join Utente as u on u.id= m.idUtente\n" + 
			"    inner join Viaggio as v on v.id = m.idViaggio\n" + 
			"    order by v.id";
	
	public MssqlMessaggioDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	public MssqlMessaggioDAO() {
		super();
	}

	@Override
	public void create(Messaggio m, int idViaggio) {
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlMessaggioDAO.insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, m.getUtente().getId());
			prep_stmt.setInt(2, idViaggio);
			prep_stmt.setString(3, m.getTimestamp().toString());
			prep_stmt.setString(4, m.getMessaggio());
			prep_stmt.executeUpdate();
			prep_stmt.close();
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
	public Chat readChatForViaggio(Viaggio v) {
		Chat chat = new Chat();
		chat.setViaggio(v);
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlMessaggioDAO.readChatForViaggio);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, v.getIdViaggio());
			ResultSet rs = prep_stmt.executeQuery();
			List<Messaggio> messaggi = new ArrayList<Messaggio>();
			while(rs.next()) {
				int i=1;
				Messaggio m = new Messaggio();
				m.setId(rs.getInt(i++));
				m.setMessaggio(rs.getString(i++));
				Timestamp ts = Timestamp.valueOf(rs.getString(i++).trim());
				m.setTimestamp(ts);
				Utente u = new Utente();
				u.setId(rs.getInt(i++));
				u.setUsername(rs.getString(i++));
				u.setEmail(rs.getString(i++));
				u.setNome(rs.getString(i++));
				u.setCognome(rs.getString(i++));
				u.setBio(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				m.setUtente(u);
				messaggi.add(m);
			}
			chat.setChat(messaggi);
			prep_stmt.close();
			rs.close();
			
		}
		catch(SQLException e) {
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
		return chat;
	}

	@Override
	public List<Chat> readAllChat() {
		List<Chat> chats = new ArrayList<Chat>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlMessaggioDAO.readAll);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			Chat c = new Chat();
			Viaggio v = null;
			boolean first = true;
			List<Messaggio> messaggi = new ArrayList<Messaggio>();
			int oldViaggioID = 0;
			while(rs.next()) {
				int i=1;
				Messaggio m = new Messaggio();
				m.setId(rs.getInt(i++));
				m.setMessaggio(rs.getString(i++));
				Timestamp ts = Timestamp.valueOf(rs.getString(i++).trim());
				m.setTimestamp(ts);
				Utente u = new Utente();
				u.setId(rs.getInt(i++));
				u.setUsername(rs.getString(i++));
				u.setEmail(rs.getString(i++));
				u.setNome(rs.getString(i++));
				u.setCognome(rs.getString(i++));
				u.setBio(rs.getString(i++));
				u.setDataNascita(rs.getDate(i++));
				u.setImmagineProfilo(rs.getString(i++));
				m.setUtente(u);
				messaggi.add(m);
				if(rs.getInt(11) != oldViaggioID) {
					if(first) {
						first = false;
					}
					else {
						c.setChat(messaggi);
						messaggi = new ArrayList<Messaggio>();
						c.setViaggio(v);
						oldViaggioID = v.getIdViaggio();
						chats.add(c);
						c = new Chat();
					}
					v = new Viaggio();
					v.setIdViaggio(rs.getInt(i++));
					v.setTitolo(rs.getString(i++));
					v.setDestinazione(rs.getString(i++));
					v.setDescrizione(rs.getString(i++));
					v.setBudget(rs.getInt(i++));
					v.setLuogopartenza(rs.getString(i++));
					v.setDatainizio(rs.getDate(i++));
					v.setDatafine(rs.getDate(i++));
					v.setLingua(rs.getString(i++));
					v.setStato(Stato.values()[rs.getInt(i++)]);
					v.setImmaginiProfilo(rs.getString(i++));
				}
				c.setChat(messaggi);
				c.setViaggio(v);
				chats.add(c);
			}
			prep_stmt.close();
			rs.close();
			
		}
		catch(SQLException e) {
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
		
		return chats;
	}
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		if(this.conn != null)
			try {
				this.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.conn = conn;
	}
}
