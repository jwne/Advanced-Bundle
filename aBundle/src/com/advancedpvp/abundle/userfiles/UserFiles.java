package com.advancedpvp.abundle.userfiles;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.advancedpvp.abundle.bundle.aBundle;

public class UserFiles {
	
	private aBundle plugin;
	
	// Player Config Files
	public FileConfiguration playerConfig;
	public File playerConfigFile;
	
	public UserFiles()
	{
		
	}
	
	public UserFiles(aBundle plugin) {
		this.plugin = plugin;
	}
	
	// Reload Player Config
	public void reloadPlayerConfig(Player p) {
		if(this.playerConfigFile == null) {
			playerConfigFile = new File(plugin.getDataFolder() + File.separator + "players", p.getName() + ".yml");
		}
		playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);
		
		InputStream defConfigStream = plugin.getResource("players" + File.separator + p.getName() + ".yml");
		if(defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			playerConfig.setDefaults(defConfig);
		}
	}
	
	// Get Player Config
	public FileConfiguration getPlayerConfig(Player p) {
		if(playerConfig == null) {
			reloadPlayerConfig(p);
		}
		return playerConfig;
	}
	
	// Save Player Config
	public void savePlayerConfig(Player p) {
		if(playerConfig == null || playerConfigFile == null) {
			return;
		}
		
		try {
			getPlayerConfig(p).save(playerConfigFile);
		} catch (IOException e) {
			plugin.l.log(Level.SEVERE, "Cannot save player config to " + playerConfigFile);
		}
	}
	
	// Save Player Default Config
	public void saveDefaultPlayerConfig(Player p) {
		if(playerConfigFile == null) {
			playerConfigFile = new File(plugin.getDataFolder() + File.separator + "players", p.getName() + ".yml");
		}
		if(!playerConfigFile.exists()) {
			plugin.saveResource(p.getName() + ".yml", false);
		}
	}
}