package io.travelook.controller.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import io.travelook.controller.RegistrazioneController;

class ConnessioneMSSQLTest {
	Connection conn;
	@Test
	
	void testStartConnection() {
		RegistrazioneController rc = new RegistrazioneController();
		conn = rc.startConnection("prova"); // la stringa qua non serve
		assertNotNull(conn);
		
	}
	void testGetConnection() {
		RegistrazioneController rc = new RegistrazioneController(); // prendo un controller a caso
		conn = rc.getDbConnection();
		assertNotNull(conn);
	}

}
