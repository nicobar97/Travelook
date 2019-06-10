package io.travelook.persistence;

import io.travelook.controller.RichiesteObservableController;

public class test {
	
	public static void main(String[]args) {
		RichiesteObservableController r = new RichiesteObservableController();
		MssqlRichiestaDiPartecipazioneDAO rdp = new MssqlRichiestaDiPartecipazioneDAO(r.startConnection());
		System.out.println(rdp.createTable() ? "Creata" : "Fallito");
		
	}
}
