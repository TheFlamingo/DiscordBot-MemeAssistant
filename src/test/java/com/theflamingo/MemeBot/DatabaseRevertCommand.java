package com.theflamingo.MemeBot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DatabaseRevertCommand extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent evt) {
		
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");
			
		try {
			if ((strArgs[0] + strArgs[1] + strArgs[2]).equals(Ref.PREFIX + "database" + "revert")) {
				if (strArgs.length == 3) DatabaseSaveLoad.revertDatabase();
				else sendErrorMessage(evt);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}	
	}
	
	public void sendErrorMessage(MessageReceivedEvent evt) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Invalid Usage");
		builder.setAuthor(evt.getMember().getUser().getName(), evt.getMember().getUser().getAvatarUrl(), evt.getMember().getUser().getAvatarUrl());
		builder.setColor(Color.decode("#EA2027"));
		builder.addField("Proper usage: meme database revert", "Reverts database to last known backup.", false);
		evt.getChannel().sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
	}
}
