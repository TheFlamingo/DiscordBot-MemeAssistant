package com.theflamingo.MemeBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.omg.CORBA.SystemException;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class SaveMemes extends ListenerAdapter{
	public static File saveFile = new File("C:/Users/Noaha/Desktop/MemeBot save files/save.txt");
	
	public static boolean[] errorFlag = new boolean[3]; //represents different discrepancies in loading. See comments in checkDatabase() 
	
	public static List<List<String>> memeDBBackup; //this will store the latest version of the database, so it can be restored if there is an error in loading
	public static List<String> memeListBackup; //read above ^
	
	public void onMessageReceived(MessageReceivedEvent evt) { 
		
		Message msg = evt.getMessage();

		String[] strArgs = msg.getContentRaw().split(" ");
			
		try {
			if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "save")) {
				try {
					save();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			else if ((strArgs[0] + strArgs[1]).equals(Ref.PREFIX + "load")) {
				try {
					backupDatabase();
					read();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}	
	}
	
	public static void backupDatabase() {
		
		memeDBBackup = MemeLinksDB.memeDB;
		memeListBackup = MemeLinksDB.memeList;
	}
	
	public static void revertDatabase() {
		
		MemeLinksDB.memeDB = memeDBBackup;
		MemeLinksDB.memeList = memeListBackup;
	}
	
	public static void save() throws FileNotFoundException {
		
		PrintWriter pw = new PrintWriter(new FileOutputStream(saveFile));
		
		pw.println("memeList"); //marker for read() to start assigning memeList variables
		for (int i = 0; i < MemeLinksDB.memeList.size(); i++) {
			pw.println(MemeLinksDB.memeList.get(i));
		}
		pw.println("memeDB-size"); //marker for read() to tell how many        VVV        it should run
		pw.println(MemeLinksDB.memeDB.size()); //this is used for how many memeDB.add(new ArrayList<String>()); statements should be run when reinitalizing them
		pw.println("memeDB-links"); //
		for (int i = 0; i < MemeLinksDB.memeDB.size(); i++) {
			String finishedString = MemeLinksDB.memeDB.get(i).get(0);
			finishedString.replace("[", "");
			pw.println(finishedString);
		}
		pw.close();
	}

	public static void read() throws FileNotFoundException {
		
		Scanner inFile = new Scanner(saveFile);
		
		String temp;
		
		boolean readMemeList = false;
		boolean readMemeDBSize = false;
		boolean readMemeDBLinks = false;
		
		int linkIndex = 0;
		int maxIterations = 0;
		
		while (inFile.hasNextLine()) {
			temp = inFile.nextLine();
			
			//Scanner finds "memeList". Every line from now on until another keyword is triggered will be added to memeList.
			if (temp.equals("memeList")) {
				readMemeList = true;
				readMemeDBSize = false;
				readMemeDBLinks = false;
				
				continue;
			}
			else if (temp.equals("memeDB-size")) {
				readMemeList = false;
				readMemeDBSize = true;
				readMemeDBLinks = false;
				
				continue;
			}
			else if (temp.equals("memeDB-links")) {
				readMemeList = false;
				readMemeDBSize = false;
				readMemeDBLinks = true;
				
				continue;
			}
			
			//has previously found "memeList", and hasn't found another keyword. Current line is being added memeList
			if (readMemeList) {
				MemeLinksDB.memeList.add(temp);
				System.out.println(temp);
			}
			else if (readMemeDBSize) {
				maxIterations = Integer.parseInt(temp);
				MemeLinksDB.init(maxIterations);
			}
			else if (readMemeDBLinks) {
				MemeLinksDB.memeDB.get(linkIndex).add(temp);
				System.out.println(MemeLinksDB.memeDB.get(linkIndex));
				linkIndex++;
			}
		}
		
		inFile.close();
		
		boolean validDatabase = checkDatabase(linkIndex, maxIterations);
		if (!validDatabase) revertDatabase();
	}
	
		public static boolean checkDatabase(int addedLinks, int linkDBSize) {
		
		//if the size of size of memeList does not equal the amount of added links, there's a discrepancy in the database 
		if (MemeLinksDB.memeList.size() != addedLinks) { 
			errorFlag[0] = true;
			
			return false;
		}
		//if the amount of link Lists does not equal the size of memeList, there's a discrepancy in the database. This is separate
		if (linkDBSize != MemeLinksDB.memeList.size()) { //from the first error because of Lists added vs Links added to these lists.
			errorFlag[1] = true;
			
			return false;
		}
		//if the size of the link database does not equal the amount of added links, there's a discrepancy in the database
		else if (linkDBSize != addedLinks) {
			errorFlag[2] = true;
			
			return false;
		}
		
		System.out.println("Error flag 0 returns " + errorFlag[0]);
		System.out.println("Error flag 1 returns " + errorFlag[1]);
		System.out.println("Error flag 2 returns " + errorFlag[2]);
		
		return true;
	}
}
