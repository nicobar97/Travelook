package io.travelook.controller;

import io.travelook.model.Utente;

public interface INotifica {
	void inviaNotifica(Utente utente, String messaggio);
}
