package io.travelook.controller;

import io.travelook.model.Chat;
import io.travelook.model.Messaggio;

public interface IChat {
	public void inviaMessaggio(Messaggio newMessaggio);
	public Chat getChat();
}