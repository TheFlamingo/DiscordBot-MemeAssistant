package com.theflamingo.MemeBot;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ListMemeCommand extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent evt) {
		
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");
		
		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "list")) {
				listMemes(evt);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}	
	}
	
	private void listMemes(MessageReceivedEvent evt) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.decode("#0652DD"));
		builder.setTitle("List of available memes");
		
		String listOfMemes = ""; 
		for (int i = 0; i < MemeLinksDB.memeList.size(); i++) { //iterates through memeLinksDB.memeList and adds each one to listOfMemes to then be displayed.
			listOfMemes += (MemeLinksDB.memeList.get(i) + ", "); 
		}
		builder.setDescription(listOfMemes);
		
		evt.getChannel().sendMessage(builder.build()).queue();
	}
}
