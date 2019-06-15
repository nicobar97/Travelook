package io.travelook.controller.rdp;

import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public abstract class NotificheVerso implements Observer {
	private Utente utente;
	private Viaggio viaggio;
	public Utente getUtente() {
		return utente;
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	@Override
	public abstract void update();
	public abstract String getMessaggio();
	public abstract void setMessaggio(String messaggio);
}
