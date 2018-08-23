package com.theflamingo.MemeBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemeBot extends ListenerAdapter {
	
	public static void main( String[] args ) throws Exception {
		
		JDA jda = new JDABuilder(AccountType.BOT).setToken(Ref.TOKEN).build();
		
		jda.getPresence().setGame(Game.playing("meme help"));
		
		jda.addEventListener(new SendMemeCommand());
		jda.addEventListener(new MemeSetChannelCommand());
		jda.addEventListener(new MemeAddCommand());
		jda.addEventListener(new SuggestMemeCommand());
		jda.addEventListener(new ListMemeCommand());
		jda.addEventListener(new SaveMemes());
		jda.addEventListener(new MemeRemoveCommand());
		jda.addEventListener(new DatabaseHealthCommand());
		SaveMemes.read(); //loads saved memes
		SaveMemes.backupDatabase();
	}
}
