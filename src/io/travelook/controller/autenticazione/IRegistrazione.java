package io.travelook.controller.autenticazione;

import io.travelook.model.Utente;

public interface IRegistrazione {
	boolean registraUtente(Utente u, String hash);
}
