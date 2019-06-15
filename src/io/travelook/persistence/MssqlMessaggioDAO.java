package io.travelook.persistence;

import java.util.List;

import io.travelook.model.Messaggio;

public class MssqlMessaggioDAO implements MessaggioDAO {
	public static String create = "insert into Messaggio () values (?,?,?,?,?,?)";
	public static String readChatForViaggio = "select * from Messaggio where idViaggio=?";
	@Override
	public void create(Messaggio u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Messaggio> readChatForViaggio(int idViaggio) {
		// TODO Auto-generated method stub
		return null;
	}

}
