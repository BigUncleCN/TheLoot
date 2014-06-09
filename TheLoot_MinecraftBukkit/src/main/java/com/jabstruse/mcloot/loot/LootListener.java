package com.jabstruse.mcloot.loot;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;

public class LootListener implements Listener {
	private final LootPlugin plugin;

	public LootListener(LootPlugin instance) {
		plugin = instance;
		plugin.getLogger().info("Player Listener Hooked.");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("Welcome " + event.getPlayer().getPlayerListName());
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(Entity damager, Entity damagee, DamageCause cause, int damage){
		plugin.getLogger().info(" == onEntityDamageByEntityEvent == " + damager.getLastDamageCause() + " Cause : " + cause);
	}
	
	@EventHandler
	public void onEntityDeathEvent(LivingEntity entity, List drops){
		plugin.getLogger().info(" == onEntityDeathEvent == " + entity.getLastDamageCause() + " Drops : " + drops.size());
	}
}
