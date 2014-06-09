package com.jabstruse.mcloot.loot;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootController {
	public static ItemStack[] GetLoots(Entity entity,Player player){
		String myDisplayName = "Awesome Sword"; //use the displayname you want here 
		ItemStack myItem = new ItemStack(Material.DIAMOND_SWORD);  //your item
		ItemMeta im = myItem.getItemMeta(); //get the itemmeta of the item
		im.setDisplayName(myDisplayName); //set the displayname
		myItem.setItemMeta(im); //give the item the new itemmeta
		return null;
	}
}
