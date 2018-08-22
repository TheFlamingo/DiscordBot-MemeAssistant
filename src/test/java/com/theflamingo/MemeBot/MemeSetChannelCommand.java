package com.theflamingo.MemeBot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemeSetChannelCommand extends ListenerAdapter {
	
	public void onMessageReceived(MessageReceivedEvent evt) {
		
		// Objects
		MessageChannel channel = evt.getChannel();
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");

		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "setchannel")) {
				boolean validUserInput = checkUserInput(strArgs);
				
				if (validUserInput) setChannel(evt, strArgs);
				else sendErrorMessage(channel, evt.getMember());
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	private boolean checkUserInput(String[] strArgs) {
		System.out.println("1a");
		if (!(strArgs[2].length() == 18)) {
			return false;
		}
		else if (strArgs[2].contains(".*[^a-z].*")) { //checks if user entered channel-id contains any letters
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
		builder.addField("Proper usage: .yu setchannel {channel-id}", "Right click on desired channel, and select 'copy channel-id'", false);
		channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
	}
	
	private void setChannel(MessageReceivedEvent evt, String[] strArgs) {
		SuggestMemeCommand.adminChannel = evt.getJDA().getTextChannelById(strArgs[2]); //sets the channel which SuggestMemeCommand will send suggestions to
	}
}
