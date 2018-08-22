package com.theflamingo.MemeBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemeLinksDB {
	public static List<List<String>> memeDB = new ArrayList<List<String>>();
	public static List<String> memeList = new ArrayList<String>();
	/*
	 * 1 kermit
	 * 2 hime
	 * 3 garrett
	 * 4 noah
	 * 5 jonty
	 * 6 calm
	 */
	
	public static void init(int maxIterations) {
		
		//memeList = Arrays.asList("kermit", "hime", "garrett", "noah", "jonty", "calm");
		
		
		for (int i = 0; i < maxIterations; i++) {
			memeDB.add(new ArrayList<String>());
		}
		
		/*
		memeDB.add(new ArrayList<String>());
		memeDB.add(new ArrayList<String>());
		memeDB.add(new ArrayList<String>());
		memeDB.add(new ArrayList<String>());
		memeDB.add(new ArrayList<String>());
		memeDB.add(new ArrayList<String>());
		
		memeDB.get(0).add("https://media1.tenor.com/images/b25511087b27597960f77dd0dbaf568d/tenor.gif?itemid=5140737");
		memeDB.get(1).add("https://i.ytimg.com/vi/hPSQ23NRED8/maxresdefault.jpg");
		memeDB.get(2).add("https://media.poetryfoundation.org/m/image/1222/garrett-hongo.jpg?w=&h=293&fit=max");
		memeDB.get(3).add("https://i.imgur.com/fZB4s2b.png");
		memeDB.get(4).add("https://i.imgur.com/9Pl30ss.jpg");
		memeDB.get(5).add("https://cdn.discordapp.com/attachments/466443271938375692/480242239503728651/Mcp8aat0_400x400.png");
		*/
		
		
	}
}