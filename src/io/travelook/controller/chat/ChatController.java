package io.travelook.controller.chat;

import java.util.Date;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Chat;
import io.travelook.model.Entry;
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
	public boolean inviaMessaggio(Messaggio newMessaggio, Viaggio v) {
		boolean res=false;
		db.setConn(super.getDbConnection());
		db.create(newMessaggio, v.getIdViaggio());
		if(chats != null) {
			for(Chat c : chats) {
				if(c.getViaggio().getIdViaggio() == v.getIdViaggio()) 
					c.getChat().add(newMessaggio);
			}
			res=true;
		}
		else {
			res=false;
		}
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," "+newMessaggio +"in viaggio"+v.getIdViaggio());
        super.scriviOperazioneLog(e);
		return res;
	}

	@Override
	public Chat getChat(Viaggio v) {	
		db.setConn(super.getDbConnection());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth,"per viaggio numero "+v.getIdViaggio());
        super.scriviOperazioneLog(e);
		return db.readChatForViaggio(v);
	}
	
	public void loadAllChats() {
		db.setConn(super.getDbConnection());
		Date d=new Date();
        super.openWriterLog("hello2.txt");
        int ide=super.getLogcount()+1;
        String nameofmeth=new Throwable().getStackTrace()[0].getMethodName();
        Entry e=new Entry(ide,1,d,nameofmeth," ");
        super.scriviOperazioneLog(e);
		this.chats = db.readAllChat();
	}
}
