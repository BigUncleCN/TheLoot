package com.jabstruse.mcloot.loot;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class LootListener implements Listener {
	private final LootPlugin plugin;
	private List<String> dropitems = null;
	private int dropChance = 0;
	public LootListener(LootPlugin instance,List<String> dropItems,int chance) {
		plugin = instance;
		this.dropitems = dropItems;
		this.dropChance = chance;
		plugin.getLogger().info("Player Listener Hooked.");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("Welcome " + event.getPlayer().getPlayerListName());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent event) {
		//plugin.getLogger().info(" == onEntityDamageByEntityEvent == " + event.getCause());
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDeath(EntityDeathEvent event){
		plugin.getLogger().info(" == onEntityDeathEvent == " + event.getEntity().getKiller());
		if(event.getEntity().getKiller()!=null){
			List<ItemStack> items = event.getDrops();
			ItemStack[] loots = LootController.GetLoots(event.getEntity(), event.getEntity().getKiller(),plugin,this.dropitems,this.dropChance);
			if(loots != null){
				items.clear();
				for (ItemStack itemStack : loots) {
					items.add(itemStack);
				}
			}
		}
	}
}
