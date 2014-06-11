package com.jabstruse.mcloot.loot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LootPlugin extends JavaPlugin {
	private static Plugin instance;
	private static int dropChance = 50;
	private LootListener playerListener = null;// = new LootListener(this);
	private static List<String> dropItems = null;
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		// TODO Insert logic to be performed when the plugin is enabled
		instance = this;
		PluginManager pm = getServer().getPluginManager();
		dropItems = (List<String>) this.getConfig().getList("loot.drop.items");
		if(dropItems == null){
			dropItems = new ArrayList<String>();
			for (Material material : Material.values()) {
				dropItems.add(material.name() + ":" + "100.0");
			}
			this.saveDefaultConfig();
			this.getConfig().options().copyDefaults(true);
			this.getConfig().set("loot.drop.items", dropItems);
			this.getConfig().set("loot.drop.chance", dropChance);
			this.saveConfig();
			getLogger().info("Loots Item Init...");
		}else{
			getLogger().info(dropItems.size() + " Loots Items Loaded.");
		}
		dropChance = this.getConfig().getInt("loot.drop.chance");
		if(dropChance == 0){
			this.getConfig().set("loot.drop.chance", 50);
			this.saveConfig();
		}
		playerListener = new LootListener(this,dropItems,dropChance);
		pm.registerEvents(playerListener, this);
		getLogger().info("onEnable has been invoked!");
	}

	@Override
	public void onDisable() {
		// TODO Insert logic to be performed when the plugin is disabled
		getLogger().info("onDisable has been invoked!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("basic")) { // If the player typed /basic then do the following...
			// doSomething
			return true;
		} //If this has happened the function will return true. 
	        // If this hasn't happened the value of false will be returned.
		return false; 
	}
	
	public static Plugin getInstance() {
		return instance;
	}
}
