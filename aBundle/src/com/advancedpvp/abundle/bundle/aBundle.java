package com.advancedpvp.abundle.bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.advancedpvp.abundle.admin.AdminCommand;
import com.advancedpvp.abundle.admin.AdminListener;
import com.advancedpvp.abundle.combatlog.CombatCommands;
import com.advancedpvp.abundle.combatlog.CombatListener;
import com.advancedpvp.abundle.userfiles.CreateUserFiles;

public class aBundle extends JavaPlugin {
	
	public final Logger l = Bukkit.getLogger();
	
	public boolean combatLogActive = 
			true; // Ensure that Combat Log is Enabled!
	
	// HashSet
	public static Set<String> staffChatLock = new HashSet<String>(); // Allows staff to be locked into Staff Chat
	
	// HashMap
	public static Map<String, Long> reportCooldown = new HashMap<String, Long>(); // Allows a one minute cooldown for 
	
	// ChatColors
	public ChatColor
	red = ChatColor.RED, // Red (Errors)
	gray = ChatColor.GRAY, // Gray (Success)
	green = ChatColor.GREEN, // Green (Success)
	aqua = ChatColor.AQUA, // Aqua (Staff Chat)
	white = ChatColor.WHITE;
	
	// Permissions
	public final String COMBAT_RELOAD_PERMISSION = "abundle.combatlog.reload";
	public final String REPORT_RECEIVE_PERMISSION = "abundle.report.receive";
	public final String SCHAT_RECEIVE_PERMISSION = "abundle.staffchat.receive";
	
	// Location Variables
	
	
	@Override
	public void onDisable() {
		final PluginDescriptionFile file = this.getDescription();
		this.l.log(Level.INFO, "[" + file.getName() + "] has been disabled.");
	}
	
	@Override
	public void onEnable() {
		final PluginDescriptionFile file = this.getDescription();
		this.l.log(Level.INFO, "[" + file.getName() + "] has been enabled on version " + file.getVersion() + ".");
		
		// Generating Configuration
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		Bukkit.getServer().getPluginManager().registerEvents(new CombatListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new AdminListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CreateUserFiles(this), this);
		Bukkit.getServer().getPluginManager().getPlugin("WorldEditPlugin");
		
		// Executing Commands
		  // Admin
		getCommand("report").setExecutor(new AdminCommand(this));
		getCommand("sc").setExecutor(new AdminCommand(this));
		getCommand("schat").setExecutor(new AdminCommand(this));

		  // Combat
		getCommand("combatlog").setExecutor(new CombatCommands(this));
	}
}