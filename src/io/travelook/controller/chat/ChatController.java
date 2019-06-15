package io.travelook.controller.chat;

import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlMessaggioDAO;

public class ChatController extends Controller implements IChat{
	List<Chat> chats = null;
	MssqlMessaggioDAO db = null;
	public ChatController() {
		db = new MssqlMessaggioDAO();
		db.setConn(super.getDbConnection());
	}
	
	@Override
	public void inviaMessaggio(Messaggio newMessaggio, Viaggio v) {
		db.setConn(super.getDbConnection());
		db.create(newMessaggio, v.getIdViaggio());
		if(chats != null)
			for(Chat c : chats) {
				if(c.getViaggio().getIdViaggio() == v.getIdViaggio()) 
					c.getChat().add(newMessaggio);
			}
	}

	@Override
	public Chat getChat(Viaggio v) {	
		db.setConn(super.getDbConnection());
		return db.readChatForViaggio(v);
	}
	
	public void loadAllChats() {
		db.setConn(super.getDbConnection());
		this.chats = db.readAllChat();
	}
}
