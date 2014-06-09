package com.jabstruse.mcloot.loot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LootPlugin extends JavaPlugin {
	private static Plugin instance;
	private final LPlayerListener playerListener = new LPlayerListener(this);
	@Override
	public void onEnable() {
		// TODO Insert logic to be performed when the plugin is enabled
		instance = this;
		PluginManager pm = getServer().getPluginManager();
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
