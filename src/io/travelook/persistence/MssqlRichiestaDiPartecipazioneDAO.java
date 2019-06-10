package io.travelook.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.utils.StatoUtils;

public class MssqlRichiestaDiPartecipazioneDAO implements RichiestaDiPartecipazioneDAO {
	Connection conn;
	static final String table = "Richiesta_Di_Partecipazione";
	static final String insert = "INSERT INTO " + table + 
			" (idUtente,idViaggio,idCreatore,messaggioRichiesta,messaggioRisposta,stato)" +
			" VALUES (?,?,?,?,?,?)";
			
	static final String create = "create table " + table + " (" + 
			"     id int not null AUTO_INCREMENT," + 
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
	
	public MssqlRichiestaDiPartecipazioneDAO(Connection conn) {
		this.conn = conn;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(RichiestaDiPartecipazione rdp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RichiestaDiPartecipazione> readRDPListFromDb() {
		// TODO Auto-generated method stub
		return null;
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
	
}
