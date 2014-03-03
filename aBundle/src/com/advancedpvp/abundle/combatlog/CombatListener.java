package com.advancedpvp.abundle.combatlog;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitScheduler;

import com.advancedpvp.abundle.bundle.aBundle;

public class CombatListener implements Listener {
	
	// Listeners to deny Combat Logging
	
	private aBundle plugin;
	
	private Player combatLogger;
	
	private Villager villager;
	private PlayerInventory inv;
	
	// HashSet
	private Set<String> spawnSet = new HashSet<String>();
	private Set<String> killedSet = new HashSet<String>();

	public CombatListener()
	{
		
	}
	
	public CombatListener(aBundle plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerCombatLogout(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if(spawnSet.contains(p.getName())) {
			spawnNPC(p);
			combatLogger = p;
		}
	}
	
	@EventHandler
	public void onVillagerDeath(EntityDeathEvent event) {
		if(villager != null) {
			if(event.getEntity() == villager) {
				for(int i = 0; i <= 35; i++) {
					if(inv.getItem(i) != null) {
						villager.getWorld().dropItem(villager.getLocation(), inv.getItem(i));
					}
				}
				killedSet.add(combatLogger.getName());
				combatLogger = null;
			}
		}
	}
	
	@EventHandler
	public void onPlayerCombatSpawnRun(EntityDamageByEntityEvent event) {
		Entity e = event.getEntity();
		Entity eKiller = event.getDamager();
		if(e instanceof Player && eKiller instanceof Player) {
			final Player p = (Player) event.getEntity();
			
			if(plugin.combatLogActive == true) {
				spawnSet.add(p.getName());
			} else {
				return;
			}
			
			int removeCooldownCnfg = plugin.getConfig().getInt("removal.cooldown"); // 100 ticks = 10 seconds
			public long removeCooldown = removeCooldownCnfg * 20;
			int i = 0;
			BukkitScheduler sch = Bukkit.getServer().getScheduler();
		  	// Don't know if this will work.
			// Should count down.
		//	while(i < removeCooldown && spawnSet.contains(p.getName())) {
		//	  i++
		//	  removeCooldownCnfg--;
		//	}
 			sch.scheduleSyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					spawnSet.remove(p.getName());
				}
				
			}, removeCooldown);
		}
	}
	
	@EventHandler
	public void onPlayerJoinInList(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(killedSet.contains(p.getName())) {
			p.getInventory().clear();
			p.setHealth(0);
			p.sendMessage(plugin.red + plugin.getConfig().getString("combatlog.message"));
		}
	}
	
	public void spawnNPC(Player p) {
		Location loc = p.getLocation();
		inv = p.getInventory();
		
		villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		villager.setCustomName(plugin.red + "PvP Logger");
		int removeCooldownCnfg = plugin.getConfig().getInt("npc.cooldown"); // 100 ticks = 10 seconds
		long removeCooldown = removeCooldownCnfg * 20;
		BukkitScheduler sch = Bukkit.getServer().getScheduler();
		sch.scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				villager.remove();
				villager = null;
			}
			
		}, removeCooldown);
	}
}
