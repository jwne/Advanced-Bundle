package com.advancedpvp.abundle.userfiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.advancedpvp.abundle.bundle.aBundle;

public class CreateUserFiles implements Listener {
	
	private aBundle plugin;
	private UserFiles userFiles;
	
	private List<String> nullPlayerFriends;
	
	public CreateUserFiles()
	{
		
	}
	
	public CreateUserFiles(aBundle plugin) {
		this.plugin = plugin;
		this.userFiles = new UserFiles(plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		userFiles.playerConfig = null; // Clearing Other Players Configuration
		userFiles.playerConfigFile = null; // Clearing Other Players Configuration
		Player p = event.getPlayer();
		try {
			File playerFolder = new File(plugin.getDataFolder() + File.separator + "players");
			if(!playerFolder.exists()) 
				playerFolder.mkdir();
			
			File pFile = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + p.getName()
					+ ".yml");
			if(!pFile.exists()) {
				pFile.createNewFile();
				
				FileWriter fw = new FileWriter(pFile, true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("# This file is" + p.getName() + "'s configuration file."); // Printing a message to the file
				pw.flush(); pw.close();
				
				nullPlayerFriends.add(p.getName());
				
				//userFiles.getPlayerConfig(p).set(p.getName() + ".friends", nullPlayerFriends); // Fix Friends List
				userFiles.savePlayerConfig(p);
			}
		} catch (IOException e) {
			plugin.l.log(Level.SEVERE, "Could not create file for player " + p.getName() + ".", e);
			p.sendMessage(plugin.red + "There was an error creating your configuration file.");
			p.sendMessage(plugin.red + "An administrator should be contacted about this problem.");
			return;
		}
	}
}