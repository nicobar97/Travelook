package io.travelook.controller;

public class NotificheEmail implements INotifica{

	@Override
	public void inviaNotifica(String email, String messaggio) {
		// TODO Auto-generated method stub
		System.out.println("EMAIL INVIATA a " + email + "\nMessaggio:\n" + messaggio);
	}

}
