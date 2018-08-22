package com.theflamingo.MemeBot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class SuggestMemeCommand extends ListenerAdapter {

	public static MessageChannel adminChannel = null;
	
	public void onMessageReceived(MessageReceivedEvent evt) {

		// Objects
		MessageChannel channel = evt.getChannel();
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");

		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "memesuggest")) {
				if (strArgs.length == 4) {
					boolean validUserInput = checkUserInput(strArgs);
					
					if (validUserInput) sendSuggestion(evt, strArgs); //if user input (which is strArgs[2] and [3]) pass the checks in checkUserInput(), call sendSuggestion
					else sendErrorMessage(channel, evt.getMember()); //user input fails checks
				}
				else { //fails initial length checks
					sendErrorMessage(channel, evt.getMember());
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	private boolean checkUserInput(String[] strArgs) {
		if (strArgs[2].length() < 2) {
			
			return false;
		}
		else if (strArgs[3].length() < 2) {
			return false;
		}
		if (!strArgs[2].contains("https") || !strArgs[2].contains("https")) { //this is a really bad way to check if index[3] is a link
			return false;
		}
		return true;
	}
	
	public void sendErrorMessage(MessageChannel channel, Member member) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Invalid Usage");
		builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
		builder.setColor(Color.decode("#EA2027"));
		builder.setDescription("{} = Required, [] = Optional");
		builder.addField("Proper usage: .yu memesuggest {meme name} {meme link}", "Meme name and meme link must greater than 2 characters", false);
		channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
	}
	
	void sendSuggestion (MessageReceivedEvent evt, String[] strArgs) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle(evt.getAuthor().getName() + " suggested a meme");
		builder.addField("Name: " + strArgs[2], "Link: " + strArgs[3], false);
		
		adminChannel.sendMessage(builder.build()).queue();
	}
}
