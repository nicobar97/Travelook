package io.travelook.controller.autenticazione;

import java.util.Date;

public interface IRegistrazione {
	boolean registraUtente(String nome, String cognome, Date dataNascita, String username, String password);
}
