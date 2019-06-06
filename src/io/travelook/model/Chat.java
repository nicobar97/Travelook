package io.travelook.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat {
	private int idViaggio;
	private List<Messaggio> messaggi;
	
	public Chat(int idViaggio) throws IllegalArgumentException {
		if(idViaggio < 0)
			throw new IllegalArgumentException();
		this.idViaggio = idViaggio;
		this.messaggi = new ArrayList<Messaggio>();
	}
	public Chat(int idViaggio, List<Messaggio> messaggi) throws IllegalArgumentException {
		if(idViaggio < 0 || messaggi == null)
			throw new IllegalArgumentException();
		this.idViaggio = idViaggio;
		this.messaggi = messaggi;
	}

	public int getIdViaggio() {
		return idViaggio;
	}
	public void setIdViaggio(int idViaggio) {
		this.idViaggio = idViaggio;
	}
	public List<Messaggio> getChat() {
		return messaggi;
	}
	public void setChat(List<Messaggio> messaggi) {
		this.messaggi = messaggi;
	}
	public void addMessaggio(Messaggio m) {
		this.messaggi.add(m);
	}
	
	public List<Messaggio> filtraChat(int idUtente) {
		List<Messaggio> result = new ArrayList<Messaggio>();
		for(Messaggio m : messaggi)
			if(m.getIdUtente() == idUtente)
				result.add(m);
		return result;
	}
	public List<Messaggio> filtraChat(Date from, Date to) {
		List<Messaggio> result = new ArrayList<Messaggio>();
		for(Messaggio m : messaggi)
			if(m.getTimestamp().compareTo(from) > 0 || m.getTimestamp().compareTo(to) < 0)
				result.add(m);
		return result;
	}
}
