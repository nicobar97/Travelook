package io.travelook.broker;

public interface IBroker {
   public boolean registraServizio(String metodo,EndPointServer s);
   public boolean inoltraRichiesta(Richiesta r ,EndPointServer s);
   public boolean inoltraRisposta(Risposta r ,EndPointClient c);
}
