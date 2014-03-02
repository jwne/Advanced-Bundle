package com.advancedpvp.abundle.bundle;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BundleCommands implements CommandExecutor {
	
	private aBundle plugin;
	
	public BundleCommands()
	{
		
	}
	
	public BundleCommands(aBundle plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		// All Normal Player Cmds Here
		
		return false;
	}
}