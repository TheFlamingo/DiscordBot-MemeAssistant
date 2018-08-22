package com.theflamingo.MemeBot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.event.MenuDragMouseListener;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemeCommand extends ListenerAdapter {
	public void onMessageReceived(MessageReceivedEvent evt) {

		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");
		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "send")) {
				// needs to be 3 long for the meme name
				if (strArgs.length == 3) {
					// storing to be passed to getMeme()
					String user_entered_meme = strArgs[2];
					System.out.println(user_entered_meme);
					getMeme(user_entered_meme, evt);
					// length is not equal to 3 words
				} else {
					sendErrorMessage(evt.getTextChannel(), evt.getMember());
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	public void getMeme(String user_entered_meme, MessageReceivedEvent evt) {

		List<List<String>> memeLinks = MemeLinksDB.memeDB;
		List<String> memes = MemeLinksDB.memeList;

		boolean foundMeme = false;  
		
		for (int i = 0; i < memes.size(); i++) {
			if (user_entered_meme.equals(memes.get(i))) {
				deliverMeme(evt.getTextChannel(), evt.getMember(), memeLinks.get(i).get(0));
				foundMeme = true;
				
				break;
			}
		} 
		//use this to stop an error message from being sent even if a meme is sent. is set to true when a meme is found
		if (foundMeme != true) {
			sendErrorMessage(evt.getTextChannel(), evt.getMember());
		}
	}

	public void sendErrorMessage(TextChannel channel, Member member) {

		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Invalid Usage");
		builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
		builder.setColor(Color.decode("#EA2027"));
		builder.setDescription("{} = Required, [] = Optional");
		builder.addField("Proper usage: .yu meme {name}", "Options: kermit, hime, garrett, noah, jonty, calm", false);
		channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
	}

	// sends embedded message in chat.
	public void deliverMeme(TextChannel channel, Member member, String memeUrl) {

		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.decode("#0652DD"));
		builder.setImage(memeUrl);

		channel.sendMessage(builder.build()).queue();
	}
}
