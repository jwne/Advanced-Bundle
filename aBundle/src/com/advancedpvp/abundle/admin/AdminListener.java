package com.advancedpvp.abundle.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.advancedpvp.abundle.bundle.aBundle;

public class AdminListener implements Listener {
	
	// All Events related to Admins
	
	private aBundle plugin;
	
	// -- Constructors -- //
	public AdminListener() 
	{
		
	}
	
	public AdminListener(aBundle plugin) {
		this.plugin = plugin;
	}
	
	// -- Events -- //
	@EventHandler
	public void onStaffChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		String pMsg = event.getMessage();
		if(p.hasPermission(plugin.SCHAT_RECEIVE_PERMISSION)) {
			if(pMsg.startsWith("!")) {
				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					if(player.hasPermission(plugin.SCHAT_RECEIVE_PERMISSION)) {
						player.sendMessage(plugin.aqua + "(STAFF) " + p.getName() + ": " + plugin.white + pMsg.replace("!", ""));
					}
				}
				event.setCancelled(true);
			}
		}
	}
	
	// Allow admins to be locked into Staff Chat
	@EventHandler
	public void onStaffLockChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		String pMsg = event.getMessage();
		if(plugin.staffChatLock.contains(p.getName())) {
			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				if(player.hasPermission(plugin.SCHAT_RECEIVE_PERMISSION)) {
					player.sendMessage(plugin.aqua + "(STAFF) " + p.getName() + ": " + plugin.white + pMsg);
				}
			}
			event.setCancelled(true);
		}
	}
	
}