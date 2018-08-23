package com.theflamingo.MemeBot;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemeHelpCommand extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent evt) {
		
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");
			
		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "help")) {
				if (strArgs.length == 2) sendHelpMessage(evt);
				else sendErrorMessage(evt);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}	
	}
	
	private void sendErrorMessage(MessageReceivedEvent evt) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Invalid Usage");
		builder.setAuthor(evt.getMember().getUser().getName(), evt.getMember().getUser().getAvatarUrl(), evt.getMember().getUser().getAvatarUrl());
		builder.setColor(Color.decode("#EA2027"));
		builder.addField("Proper usage: meme help", "Displays a list of all commands and how to use them", false);
		evt.getChannel().sendMessage(builder.build()).queue();
	}
	
	private void sendHelpMessage(MessageReceivedEvent evt) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Commands");
		builder.addField("meme health", "Returns the status of the meme database", false);
		builder.addField("meme list", "Returns a list of all available memes ", false);
		builder.addField("meme add {meme name} {image url}", "Adds meme to the database", false);
		builder.addField("meme remove {meme name}", "Removes a meme from the database", false);
		builder.addField("meme setchannel {channel id}", "Sets channel meme suggestions will be sent to", false);
		builder.addField("meme send {meme name}", "Sends a specified meme in chat", false);
		builder.addField("meme suggest {meme name} {meme link}", "Sends a meme suggestion in a set channel", false);
		evt.getChannel().sendMessage(builder.build()).queue();
		
	}
}
