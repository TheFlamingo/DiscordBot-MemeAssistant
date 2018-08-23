package com.theflamingo.MemeBot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemeAddCommand extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent evt) {
		
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");
			
		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "add")) {
				if (strArgs.length != 3) {
					boolean validUserInput = inputChecks(strArgs);
					
					if (validUserInput) addMeme(strArgs);
					else sendErrorMessage(evt);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}	
	}
	
	private boolean inputChecks(String[] strArgs) {
		
		if (strArgs[2].length() < 2) {
			return false;
		}
		else if (strArgs[3].length() < 2) {
			return false;
		}
		if (strArgs[2].contains("https") || strArgs[2].contains("https")) { //this is a really bad way to check if index[3] is a link
			return true;
		}
		return true;
	}
	
	private void addMeme(String[] strArgs) {
		
		MemeLinksDB.memeList.add(strArgs[2]);
		MemeLinksDB.memeDB.add(new ArrayList<String>());
		
		int index = (MemeLinksDB.memeDB.size() - 1);
		
		MemeLinksDB.memeDB.get(index).add(strArgs[3]);
	}
	
	public void sendErrorMessage(MessageReceivedEvent evt) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Invalid Usage");
		builder.setAuthor(evt.getMember().getUser().getName(), evt.getMember().getUser().getAvatarUrl(), evt.getMember().getUser().getAvatarUrl());
		builder.setColor(Color.decode("#EA2027"));
		builder.setDescription("{} = Required, [] = Optional");
		builder.addField("Proper usage: meme add {meme name} {meme link}", "Meme name and meme link must greater than 2 characters", false);
		evt.getChannel().sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
	}
}
