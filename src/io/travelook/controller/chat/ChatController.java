package io.travelook.controller.chat;

import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Viaggio;

public class ChatController extends Controller implements IChat{
	List<Chat> chats = null;
	
	public ChatController() {
		
	}
	
	@Override
	public void inviaMessaggio(Messaggio newMessaggio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Chat getChat(Viaggio v) {
		// TODO Auto-generated method stub
		return null;
	}

}
