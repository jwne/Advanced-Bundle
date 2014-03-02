package com.advancedpvp.abundle.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.advancedpvp.abundle.bundle.aBundle;

public class AdminCommand implements CommandExecutor {
	
	// All Commands related to Staff Members.
	
	private aBundle plugin;
	
	public AdminCommand()
	{
		
	}
	
	public AdminCommand(aBundle plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		// Report Commnad - /report [name] [message]
		if(cmd.getName().equalsIgnoreCase("report")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("You do not need to report someone!");
				return false;
			}
			Player p = (Player) sender;
			if(plugin.reportCooldown.containsKey(p.getName())) {
				int reportingCooldown = plugin.getConfig().getInt("report.cooldown");
				long secondsLeft = ((plugin.reportCooldown.get(p.getName())/1000) + reportingCooldown) - (System.currentTimeMillis()/1000);
				if(secondsLeft > 0) {
					p.sendMessage(plugin.gray + "Please wait " + plugin.green + secondsLeft + plugin.gray + " seconds before doing this again.");
					return true;
				}
			}
			if(args.length >= 2) {
				Player t = Bukkit.getServer().getPlayer(args[0]);
					
				if(t == null) {
					p.sendMessage(plugin.red + "Player specified could not be found.");
					return false;
				}
					
				String reportMsg = "";
				for(int r = 1; r < args.length; r++) {
					reportMsg += args[r] + " "; // Get all message
				}
					
				for(Player admins : Bukkit.getServer().getOnlinePlayers()) {
					if(admins.hasPermission(plugin.REPORT_RECEIVE_PERMISSION)) {
						admins.sendMessage(plugin.red + "(REPORT) " + p.getName() + " says " + t.getName() + " is: " + reportMsg);
					}
				}
				plugin.reportCooldown.put(p.getName(), System.currentTimeMillis());
				p.sendMessage(plugin.gray + "Player " + t.getName() + " has been reported.");
				//logs.reportLog(logs.currentDateTime() + "(REPORT) " + p.getName() + " > " + t.getName() + ": " + reportMsg);
				return true;
			}
			p.sendMessage(plugin.red + "Correct Usage: /report [name] [reason]");
		}
		
		// Staff Chat Command - /sc [message]
		if(cmd.getName().equalsIgnoreCase("sc") || cmd.getName().equalsIgnoreCase("schat")) {
			if(!(sender instanceof Player)) {
				if(args.length >= 1) {
					String acMsg = "";
					for(int a = 0; a < args.length; a++) {
						acMsg += args[a] + " ";
					}
					
					for(Player admin : Bukkit.getServer().getOnlinePlayers()) {
						if(admin.hasPermission(plugin.SCHAT_RECEIVE_PERMISSION)) {
							admin.sendMessage(plugin.aqua + "(STAFF) Console:" + acMsg);
						}
					}
					return true;
				}
				sender.sendMessage(plugin.red + "Correct Usage: /sc [message]");
				return false;
			}
			Player p = (Player) sender;
			if(p.hasPermission(plugin.SCHAT_RECEIVE_PERMISSION)) {
				if(args.length >= 1) {
					String acMsg = "";
					for(int a = 0; a < args.length; a++) {
						acMsg += args[a] + " ";
					}
					
					for(Player admin : Bukkit.getServer().getOnlinePlayers()) {
						if(admin.hasPermission(plugin.SCHAT_RECEIVE_PERMISSION)) {
							admin.sendMessage(plugin.aqua + "(STAFF) " + p.getName() + ": " + plugin.white + acMsg);
						}
					}
					return true;
				}
				if(plugin.staffChatLock.contains(p.getName())) {
					plugin.staffChatLock.remove(p.getName());
					p.sendMessage(plugin.gray + "You are now talking in " + plugin.green + "main" + plugin.gray + " chat.");
					return true;
				}
				plugin.staffChatLock.add(p.getName());
				p.sendMessage(plugin.gray + "You are now always talking in " + plugin.green + "staff" + plugin.gray + " chat.");
				return true;
			}
			p.sendMessage(plugin.red + "You are not a staff member!");
			return false;
		}
		
		return false;
	}
}