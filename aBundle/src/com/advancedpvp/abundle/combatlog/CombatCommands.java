package com.advancedpvp.abundle.combatlog;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.advancedpvp.abundle.bundle.aBundle;

public class CombatCommands implements CommandExecutor {
	
	private aBundle plugin;
	
	public CombatCommands()
	{
		
	}
	
	public CombatCommands(aBundle plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("combatlog")) {
			if(!(sender instanceof Player)) {
				if(args.length == 1) {
					String firstArg = args[0]; // First Argument for Command
					if(firstArg.equals("reload")) {
						plugin.reloadConfig();
						sender.sendMessage(plugin.gray + "The config has been " + plugin.green + "reloaded" + plugin.gray + " successfully.");
						return true;
					} else if(firstArg.equals("desc") || firstArg.equals("description")) {
						sender.sendMessage(plugin.gray + "The command to handle all " + plugin.green + "combat log" + plugin.gray + " features.");
						return true;
					} else if(firstArg.equals("help")) {
						sender.sendMessage(plugin.gray + "Combat Log Help:\n  - " + plugin.green + "/combatlog reload" + plugin.gray + " - " +
								"Reload the Config.\n  - " + plugin.green + "/combatlog desc "  + plugin.gray + " - Combat Log Description\n" +
										"  - "  + plugin.green + "/combatlog help" + plugin.gray + " - Show CombatLog Help");
						return true;
					} else if(firstArg.equals("stop")) {
						plugin.combatLogActive = false;
						return true;
					} else if(firstArg.equals("start")) {
						plugin.combatLogActive = true;
						return true;
					} else if(firstArg.equals("check")) {
						if(plugin.combatLogActive == true) {
							sender.sendMessage(plugin.gray + "This feature is currently " + plugin.green + "active" + plugin.gray + ".");
							return true;
						}
						sender.sendMessage(plugin.gray + "This feature is currently " + plugin.red + "disabled" + plugin.gray + ".");
						return true;
					}
					sender.sendMessage(plugin.red + "Correct Usage: /combatlog [reload/desc/help]");
					return false;
				}
			}
			Player p = (Player) sender;
			if(p.hasPermission(plugin.COMBAT_RELOAD_PERMISSION)) {
				if(args.length == 1) {
					String firstArg = args[0]; // First Argument for Command
					if(firstArg.equals("reload")) {
						plugin.reloadConfig();
						p.sendMessage(plugin.gray + "The config has been " + plugin.green + "reloaded" + plugin.gray + " successfully.");
						return true;
					} else if(firstArg.equals("desc") || firstArg.equals("description")) {
						p.sendMessage(plugin.gray + "The command to handle all " + plugin.green + "combat log" + plugin.gray + " features.");
						return true;
					} else if(firstArg.equals("help")) {
						p.sendMessage(plugin.gray + "Combat Log Help:\n  - " + plugin.green + "/combatlog reload" + plugin.gray + " - " +
								"Reload the Config.\n  - " + plugin.green + "/combatlog desc "  + plugin.gray + " - Combat Log Description\n" +
										"  - "  + plugin.green + "/combatlog help" + plugin.gray + " - Show CombatLog Help");
						return true;
					} else if(firstArg.equals("stop")) {
						plugin.combatLogActive = false;
						return true;
					} else if(firstArg.equals("start")) {
						plugin.combatLogActive = true;
						return true;
					} else if(firstArg.equals("check")) {
						if(plugin.combatLogActive == true) {
							p.sendMessage(plugin.gray + "This feature is currently " + plugin.green + "active" + plugin.gray + ".");
							return true;
						}
						p.sendMessage(plugin.gray + "This feature is currently " + plugin.red + "disabled" + plugin.gray + ".");
						return true;
					}
					p.sendMessage(plugin.red + "Correct Usage: /combatlog [reload/desc/help]");
					return false;
				}
			}
		}
		
		return false;
	}
}