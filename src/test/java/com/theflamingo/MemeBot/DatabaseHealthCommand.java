package com.theflamingo.MemeBot;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DatabaseHealthCommand extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent evt) {

		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");

		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "health")) {
				checkDatabaseHealth(evt);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	public void checkDatabaseHealth(MessageReceivedEvent evt) {
		boolean[] errorFlag = new boolean[3];
		boolean errorFound = false; //is set to true if any errors in the database are found
		
		// if the size of size of memeList does not equal the amount of added
		// links, there's a discrepancy in the database
		if (MemeLinksDB.memeList.size() != getFilledDBLists()) {
			errorFlag[0] = true;
			errorFound = true;
		}
		if (MemeLinksDB.memeDB.size() != getFilledDBLists()) {
			errorFlag[1] = true;
			errorFound = true;
		}
		if (MemeLinksDB.memeList.size() != MemeLinksDB.memeDB.size()) {
			errorFlag[2] = true;
			errorFound = true;
		}
		
		returnDatabaseHealth(errorFlag, errorFound, evt);
	}
	
	private void returnDatabaseHealth(boolean[] errorFlag, boolean errorFound, MessageReceivedEvent evt) {
		if (errorFound == true) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setTitle("Errors found");
			builder.setColor(Color.decode("#EA2027"));
			builder.setDescription(errorFlag[0] + "\n" + errorFlag[1] + "\n" + errorFlag[2] + "\n");
			evt.getChannel().sendMessage(builder.build()).queue();
		}
		else if (errorFound == false) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setTitle("Database nominal");
			evt.getChannel().sendMessage(builder.build()).queue();
		}
	}
	
	private int getFilledDBLists() {
		
		int filledLists = 0;
		for (int i = 0; i < MemeLinksDB.memeDB.size(); i++) {
			if (!MemeLinksDB.memeDB.get(i).contains("")) {
				filledLists++;
			}
		}
		
		return filledLists;
	}
}
