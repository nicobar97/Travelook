package io.travelook.controller.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import io.travelook.controller.RegistrazioneController;
import io.travelook.controller.RichiesteObservableController;

class ConnessioneMSSQLTest {
	Connection conn;
	
	void testStartConnection() {
		RegistrazioneController rc = new RegistrazioneController();
		conn = rc.getDbConnection(); // la stringa qua non serve
		assertNotNull(conn);
		
	}
	@Test
	void testGetConnection() {
		RichiesteObservableController rc = new RichiesteObservableController(); // prendo un controller a caso
		conn = rc.getDbConnection();
		System.out.println(conn == null ? "PORCODIO": "bella");
		assertNotNull(conn);
	}

}
