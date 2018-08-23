package com.theflamingo.MemeBot;

import java.util.ArrayList;
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
		for (int i = 0; i < maxIterations; i++) {
			memeDB.add(new ArrayList<String>());
		}
	}
}