package io.travelook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat implements Serializable {
	private Viaggio viaggio;
	private List<Messaggio> messaggi;
	
	public Chat(Viaggio viaggio) throws IllegalArgumentException {
		if(viaggio == null)
			throw new IllegalArgumentException();
		this.viaggio = viaggio;
		this.messaggi = new ArrayList<Messaggio>();
	}
	public Chat(Viaggio viaggio, List<Messaggio> messaggi) throws IllegalArgumentException {
		if(viaggio == null || messaggi == null)
			throw new IllegalArgumentException();
		this.viaggio = viaggio;
		this.messaggi = messaggi;
	}

	public Chat() {
		// TODO Auto-generated constructor stub
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	public void setViaggio(Viaggio viaggio) {
		this.viaggio = viaggio;
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
			if(m.getUtente().getId() == idUtente)
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
