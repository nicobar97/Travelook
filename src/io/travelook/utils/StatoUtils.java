package io.travelook.utils;

import io.travelook.model.Stato;

public class StatoUtils {
	public static int getStatoId(Stato stato) {
		int result = -1;
		if(stato.compareTo(Stato.ACCETTATA) == 0) {
			result = 0;
		}
		else if(stato.compareTo(Stato.NONACCETTATA) == 0) {
			result = 1;
		}
		else if(stato.compareTo(Stato.NONVISTA) == 0) {
			result = 2;
		}
		else if(stato.compareTo(Stato.INIZIO) == 0) {
			result = 3;
		}
		else if(stato.compareTo(Stato.INCORSO) == 0) {
			result = 4;
		}
		else if(stato.compareTo(Stato.FINE) == 0) {
			result = 5;
		}
		return result;
		
	}
}
