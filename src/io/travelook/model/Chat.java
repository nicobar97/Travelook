package io.travelook.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat {
	private List<Messaggio> messaggi;

	public Chat() {
		messaggi = new ArrayList<Messaggio>();
	}
	
	public List<Messaggio> getChat() {
		return messaggi;
	}
	public void setChat(List<Messaggio> messaggi) {
		this.messaggi = messaggi;
	}
	public void addMessaggio(Messaggio m) {
		messaggi.add(m);
	}
	
	public List<Messaggio> filtraChat(String idUtente) {
		List<Messaggio> result = new ArrayList<Messaggio>();
		for(Messaggio m : messaggi)
			if(m.getIdUtente().equals(idUtente))
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
