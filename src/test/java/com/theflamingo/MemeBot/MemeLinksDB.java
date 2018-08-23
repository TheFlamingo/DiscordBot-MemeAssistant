package com.theflamingo.MemeBot;

import java.util.ArrayList;
import java.util.List;

public class MemeLinksDB {
	
	public static List<List<String>> memeDB = new ArrayList<List<String>>();
	public static List<String> memeList = new ArrayList<String>();
	
	public static void init(int maxIterations) {
		for (int i = 0; i < maxIterations; i++) {
			memeDB.add(new ArrayList<String>());
		}
	}
}