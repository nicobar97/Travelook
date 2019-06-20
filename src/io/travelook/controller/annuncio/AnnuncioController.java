package io.travelook.controller.annuncio;
import io.travelook.controller.Controller;
import io.travelook.model.Recensione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlViaggioDAO;

public class AnnuncioController extends Controller implements IAnnuncio {
    private Viaggio viaggio;
    private MssqlViaggioDAO db;
    
    
    public AnnuncioController(Viaggio v) {
    	/* nel costruttore reperisco le info sul viaggio dal db */ 
    	viaggio = v;
    	this.db = new MssqlViaggioDAO();
    }
	public AnnuncioController() {
		this.db = new MssqlViaggioDAO();
	}
	@Override
	public Utente[] visuallizzaUtentiPartecipanti(Integer idAnnuncio) {
		Utente[] res;
		if(idAnnuncio == null || idAnnuncio <0) {
			throw new IllegalArgumentException();
		}
		res=(Utente[]) viaggio.getPartecipanti().toArray();
		return res;
	}
	public Viaggio getViaggioById(int id) {
		db.setConn(super.getDbConnection());
		return db.read(id);
	}
	@Override
	public boolean modificaAnnuncio(Viaggio modificato) {
		/* questo metodo tramite un insert a livello db andr� a modificare i dati del viaggio passato come argomento */
		db.setConn(super.getDbConnection());
		if(db.update(modificato)) {
			viaggio=modificato;
			return true;
		}
		else return false;
	}

	@Override
	public boolean lasciaRecensione(Recensione newRecensione) {
		boolean rec=false; 
		if(newRecensione==null) {
			rec=false;
		}else {
			rec=true;
		}
		return rec;
	}

	@Override
	public boolean abbandonaAnnuncio(Utente utente) {
		boolean res=false; 
		if(utente==null) {
			res=false;
			throw new IllegalArgumentException();
		}else {
			viaggio.getPartecipanti().remove(utente);
			db.setConn(super.getDbConnection());
			db.utenteAbbandonaAnnuncio(utente, this.viaggio);
			res=true;
		}
		return res;
	}
	public Viaggio getViaggio() {
		return viaggio;
	}
	public void setViaggio(Viaggio viaggio) {
		this.viaggio = viaggio;
	}
	

}
