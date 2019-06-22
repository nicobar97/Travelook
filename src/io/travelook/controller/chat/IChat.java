package io.travelook.controller.chat;

import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Viaggio;

public interface IChat {
	public boolean inviaMessaggio(Messaggio newMessaggio, Viaggio v);
	public Chat getChat(Viaggio v);
}
