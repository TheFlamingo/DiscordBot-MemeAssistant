package com.theflamingo.MemeBot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemeRemoveCommand  extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent evt) {
		
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");
			
		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "remove")) {
				boolean validInput = validUserInput(strArgs);
				if (validInput) removeMeme(evt, strArgs);
				else sendErorrMessage(evt); //invalid input
			}
		} catch (ArrayIndexOutOfBoundsException e) {}	
	}

	private boolean validUserInput(String[] strArgs) {
		
		for (int i = 0; i < MemeLinksDB.memeList.size(); i++) {
			if (strArgs[2].equals(MemeLinksDB.memeList.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	private void sendErorrMessage(MessageReceivedEvent evt) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Invalid Usage");
		builder.setAuthor(evt.getMember().getUser().getName(), evt.getMember().getUser().getAvatarUrl(), evt.getMember().getUser().getAvatarUrl());
		builder.setColor(Color.decode("#EA2027"));
		builder.setDescription("{} = Required, [] = Optional");
		builder.addField("Proper usage: meme remove {meme name}", "Enter the name of the meme you would like to remove", false);
		evt.getChannel().sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
	}
	
	private void removeMeme(MessageReceivedEvent evt, String[] strArgs) { 
																			  //this is one pathetic ass solution.
		for (int i = 0; i < MemeLinksDB.memeList.size(); i++) {			      //need to figure out a way to return the found meme in  
			if (strArgs[2].equals(MemeLinksDB.memeList.get(i))) {			  //validUserInput
				int index = MemeLinksDB.memeList.indexOf(strArgs[2]);
				MemeLinksDB.memeList.remove(index);
				MemeLinksDB.memeDB.remove(index);
				break;
			}
		}
	}
}
