package io.travelook.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import io.travelook.model.Viaggio;
import io.travelook.utils.StatoUtils;

public class MssqlViaggioDAO implements ViaggioDAO {
	Connection conn;
	// campi interni alla table Viaggio // 
	static final String ID = "id";
	static final String IDC = "idCreatore";
	static final String IDP= "idPartecipante";
	static final String TITOLO = "titolo";
	static final String  DESTINAZIONE= "destinazione";
	static final String  DESCRIZIONE= "descrizione";
	static final String  LINGUA= "lingua";
	static final String  BUDGET= "budget";
	static final String  DATAP= "dataPartenza";
	static final String  DATAA= "dataFine";
	static final String  IMMP= "immagineProfilo";
	static final String  IMMA= "immaginiAlternative";

	
	static final String table = "Viaggio";
	
	//Query per inserire un viaggio nel DB//
	static final String insert = "INSERT INTO " + table + 
			" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,dataPartenza,dataFine"+
			",immagineProfilo,immaginiAlternative"+
			" VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	//Query per creare la table viaggio nel DB//
	static final String create="create table " + table + " (" + 
			"id int not null IDENTITY PRIMARY KEY," +
			"idCreatore int not null," +
			"titolo char(20) not null,"+
		    "destinazione char(20) not null,"+
		    "descrizione char(200) not null,"+
			"lingua char(20) not null,"+
			"budget int not null,"+
			"dataPartenza Date not null,"+
			"dataFine Date not null,"+
			"immagineProfilo char(1000),"+
			"immaginiAlternative char(1000),"+
			"primary key(id),"+
			"foreign key (idCreatore) references Utente(id),"+
			"foreign key(idPartecipante) references Utente(id),"+
			"unique(id, idCreatore, idPartecipante)"+
			")";
	
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + table + " v " +
			"inner join UTENTE AS C ON v."+IDC+"=c.id" 
		;
	
	// DELETE FROM table WHERE idcolumn = ?;
		static String delete = 
			"DELETE " +
				"FROM " + table + " " +
				"WHERE " + ID + " = ? "
			;

	public MssqlViaggioDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override //inserisce un viaggio nel db // 
	public void create(Viaggio viaggio) {
		// TODO Auto-generated method stub
		/*" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,dataPartenza,dataFine"+
		",immagineProfilo,immaginiAlternative"+*/
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1,viaggio.getIdViaggio());
			prep_stmt.setInt(2,viaggio.getCreatore().getId());
			prep_stmt.setString(3,viaggio.getTitolo());
			prep_stmt.setString(4,viaggio.getDestinazione());
			prep_stmt.setString(5,viaggio.getDescrizione());
			prep_stmt.setString(6,viaggio.getLingua());
			prep_stmt.setInt(7,viaggio.getBudget());
			prep_stmt.setDate(8,viaggio.getDatainizio());
			prep_stmt.setDate(9,viaggio.getDatafine());
			prep_stmt.setString(10,viaggio.getImmaginiProfilo());
			prep_stmt.setString(11,viaggio.getImmaginiAlte());			
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Viaggio read(int id) {
		try {
			/*" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,dataPartenza,dataFine"+
			",immagineProfilo,immaginiAlternative"+*/
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.read_by_id);
			prep_stmt.setInt(1,id);
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt("v.id"));
				int idU=rs.getInt("c.id");
				String user=rs.getS
				v.setCreatore(creatore);(rs.getInt(ID));
				entry.setName(rs.getString(NAME));
				result = entry;
			}
			rs.close();
			prep_stmt.close();
		    
		}
		catch(Exception e) {
			
		}
		return null;
	}

	@Override
	public boolean update(Viaggio viaggio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Viaggio> readViaggiListFromDb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dropTable() {
		// TODO Auto-generated method stub
		return false;
	}

}
