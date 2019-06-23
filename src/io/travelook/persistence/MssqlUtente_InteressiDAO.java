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
	private String read_interessi_utente="SELECT idInteresse FROM Utente_Interessi "
			+ "WHERE idUtente=?";
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
		String queryAdd = "INSERT INTO Utente_Interessi(idUtente,idInteresse) VALUES(?,?)";
		int idint=-1;
		for(int k=0; k<Interessi.values().length; k++) {
			if(Interessi.values()[k].compareTo(i) == 0)
				idint = k;
		}
		if(idint > 0) {
			try {
				PreparedStatement prep_stmt = conn.prepareStatement(queryAdd);
				prep_stmt.setInt(1, u.getId());
				prep_stmt.setInt(2, idint);
				int righeAggiunte = prep_stmt.executeUpdate();
				if(righeAggiunte==0) return false;
				else return true;
			}
			catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		else 
			return false;
	}

	@Override
	public List<Interessi> readInteressiByUtente(Utente u) {
		List<Interessi> res=new ArrayList<Interessi>();
		Interessi[] i = Interessi.values();
		if(u==null) {
			System.out.println("l'utente non pu� essere nullo nella ricerca dei suoi interessi \n ");
			return res;
		}
		try {
			PreparedStatement ps=conn.prepareStatement(read_interessi_utente);
			ps.setInt(1,u.getId());
			ResultSet rs=ps.executeQuery();
			  while(rs.next()) {	   
				   res.add(i[rs.getInt(1)]);
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
			System.out.println("l'utente non pu� essere nullo nella rimozione di un suo interesse \n ");
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
