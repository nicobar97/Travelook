package io.travelook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;


public abstract class Observable {
	private List<Observer> observers;
	public Observable() {
		this.observers = new ArrayList<Observer>();
	}
	public void addObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException();
        if (!observers.contains(observer)) {
        	this.observers.add(observer);
        }
    }
    public void removeObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException();
        if (observers.contains(observer)) {
        	this.observers.remove(observer);
        }
    }
    public List<Observer> getListObserver() {
    	return this.observers;
    }
    public void notifyObservers() {
    	
    }
    public abstract void notifyCreatore(Viaggio viaggio, String messaggio);
    public abstract void notifyUtente(Viaggio viaggio, String messaggio, Stato stato, Utente idUtente);
    public abstract void getObserver(int idUtente);
    public abstract boolean nuovaRichiesta(RichiestaDiPartecipazione richiesta);
    public abstract boolean rispondiRichiesta(RichiestaDiPartecipazione richiesta);

}
