package io.travelook.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

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
			" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,luogoPartenza,dataPartenza,dataFine"+
			",immagineProfilo,immaginiAlternative"+
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	//Query per creare la table viaggio nel DB//
	static final String create="create table " + table + " (" + 
			"id int not null IDENTITY PRIMARY KEY," +
			"idCreatore int not null," +
			"titolo char(20) not null,"+
		    "destinazione char(20) not null,"+
		    "descrizione char(200) not null,"+
			"lingua char(20) not null,"+
			"budget int not null,"+
			"luogoPartenza char(20) not null,"+
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
		// UPDATE  a Table 
		static String update = 
				"UPDATE " + table + " " +
					"SET "  
						 +IDC+ " = ?, " +
					"WHERE " + ID + " = ? "
				;

	public MssqlViaggioDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override //inserisce un viaggio nel db // 
	public void create(Viaggio viaggio) {
		if(viaggio==null) {
			System.out.println( "insert(): failed to insert a null entry");
			}
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
			prep_stmt.setString(8,viaggio.getLuogopartenza());
			prep_stmt.setDate(9,viaggio.getDatainizio());
			prep_stmt.setDate(10,viaggio.getDatafine());
			prep_stmt.setString(11,viaggio.getImmaginiProfilo());
			prep_stmt.setString(12,viaggio.getImmaginiAlte());			
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
				v.setStato(Stato.ACCETTATA);
				res=v;
			}
			rs.close();
			prep_stmt.close();
		    
		}
		catch(Exception e) {
			
		}
		return res;
	}

	@Override
	public boolean update(Viaggio viaggio) {
		boolean result = false;
		if ( viaggio == null )  {
			System.out.println( "update(): failed to update a null entry");
			return result;
		}
		try {
			/*PreparedStatement prep_stmt = conn.prepareStatement(Db2CourseDAO.update);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, course.getId());
			prep_stmt.setString(2, course.getName());
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();*/
		}
		catch (Exception e) {
			System.out.println("insert(): failed to update entry: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			//Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		boolean res=false;
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.delete);
			prep_stmt.setInt(1,id);
			prep_stmt.executeUpdate();
			res = true;
			prep_stmt.close();			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<Viaggio> readViaggiListFromDb() {
		
		List<Viaggio> listaViaggi = new ArrayList<Viaggio>();
		String leggituttiviaggi= "SELECT * FROM Viaggio";
		
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(leggituttiviaggi);
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				Viaggio v = new Viaggio();
				int idViaggio;
				MssqlUtenteDAO ud = new MssqlUtenteDAO(conn);
				idViaggio=rs.getInt("id");
				Utente creatore = ud.read(rs.getInt("idCreatore"));
				v.setCreatore(creatore);
				v.setBudget(rs.getInt("budget"));
				v.setDatafine(rs.getDate("dataFine"));
				v.setDatainizio(rs.getDate("dataPartenza"));
				v.setImmaginiAlte(rs.getString("immaginiAlternative"));
				v.setIdViaggio(rs.getInt("id"));
				v.setLingua(rs.getString("lingua"));
				v.setLuogopartenza(rs.getString("luogoPartenza"));
				v.setStato(Stato.valueOf(rs.getString("stato")));
				v.setDescrizione(rs.getString("descrizione"));
				v.setTitolo(rs.getString("titolo"));
				v.setImmaginiProfilo(rs.getString("immaginiProfilo"));
				List<Utente> listaPartecipanti = new ArrayList<Utente>();
				
				String queryinnestata = "SELECT rdp.idUtente FROM Richiesta_Di_Partecipazione AS rdp WHERE idViaggio=? and stato=0";
				PreparedStatement prep_stmt2 = conn.prepareStatement(queryinnestata);
				prep_stmt2.setInt(1, idViaggio);
				ResultSet rs2=prep_stmt2.executeQuery();
				while(rs2.next()) {
					listaPartecipanti.add(ud.read(rs.getInt("rdp.idUtente")));
				}
				v.setPartecipanti(listaPartecipanti);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return listaViaggi;
	}

	@Override
	public boolean createTable() {
		boolean res=false;
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(MssqlViaggioDAO.create);
			res = true;
			stmt.close();	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean dropTable() {
		// TODO Auto-generated method stub
		return false;
	}

}
