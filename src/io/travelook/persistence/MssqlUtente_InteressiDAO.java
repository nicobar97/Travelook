package io.travelook.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Interessi;
import io.travelook.model.Stato;
import io.travelook.model.Utente;

public class MssqlUtente_InteressiDAO implements IUtente_InteressiDAO{
	Connection conn;
	private String read_interessi_utente="SELECT i.idInteresse"
			+ "FROM Utente_Interessi as i INNER JOIN Utente as u "
			+ "WHERE i.idUtente=? ";
	private String delete="DELETE FROM Utente_Interessi WHERE idInteresse=?,idUtente=?";
	public MssqlUtente_InteressiDAO(Connection c) {
		conn=c;
	}
	public MssqlUtente_InteressiDAO() {
		// TODO Auto-generated constructor stub
	}
	//
	@Override
	public boolean create(Utente u, Interessi i) {
		/*Aggiunge un interesse all'utente e quindi aggiunge una tupla
		 * alla tabella che mappa gli interessi
		 */
		boolean esito = false;
		String query1 = "SELECT id from Utente WHERE nickname= ?";
		int id = -1;
		
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(query1);
			prep_stmt.setString(1, u.getUsername());
			System.out.println(u.getUsername());
			ResultSet rs = prep_stmt.executeQuery();
			if(rs.next()) {
				id= rs.getInt(1);
			}
			else return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		if(id<0) return false;
		int idInteresse = i.ordinal()+1;
		
		String queryAdd = "INSERT INTO Utente_Interesse(idUtente,idInteresse) VALUES(?,?)";
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(queryAdd);
			prep_stmt.setInt(1, id);
			prep_stmt.setInt(2, idInteresse);
			int righeAggiunte = prep_stmt.executeUpdate();
			if(righeAggiunte==0) return false;
			else return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Interessi> readInteressiByUtente(Utente u) {
	   /*"SELECT i.idInteresse"
			+ "FROM Utente_Interessi as i INNER JOIN Utente as u "
			+ "WHERE i.idUtente=? ";*/
		List<Interessi> res=new ArrayList<Interessi>();
		if(u==null) {
			System.out.println("l'utente non può essere nullo nella ricerca dei suoi interessi \n ");
			return res;
		}
		try {
			PreparedStatement ps=conn.prepareStatement(read_interessi_utente);
			ps.setInt(1,u.getId());
			ResultSet rs=ps.executeQuery();
			  while(rs.next()) {
				   Interessi i =Interessi.values()[rs.getInt("idInteresse")];
				   res.add(i);
			       }
		    }
	    catch(SQLException e) {
		e.printStackTrace();
		return res;
		}
		return res;
	}

	@Override
	public boolean delete(Utente u, Interessi i) {
		/*"DELETE FROM Utente_Interessi WHERE idInteresse=?,idUtente=?";*/
		boolean res=false;
		if(u==null) {
			System.out.println("l'utente non può essere nullo nella rimozione di un suo interesse \n ");
			return res;
		}
		if(i==null) {
			System.out.println("impossibile rimuovere un interesse nullo!");
		}
		try {
			PreparedStatement ps=conn.prepareStatement(delete);
			ps.setInt(1,i.ordinal());
			ps.setInt(2,u.getId());
			if(ps.executeUpdate()>0) {
				ps.close();
				System.out.println("Ho eliminato l'interesse "+i+"dall' utente con id :" +u.getId());
				res= true;
				}
				else {
					System.out.println("Non ho eliminato  l'interesse "+i+"dall' utente con id :" +u.getId());
					res=false;
				}
		}
		catch(SQLException e ) {
			
		}
		return res;
	}

}
