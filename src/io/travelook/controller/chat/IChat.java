package io.travelook.controller.chat;

import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Viaggio;

public interface IChat {
	public void inviaMessaggio(Messaggio newMessaggio);
	public Chat getChat(Viaggio v);
}
