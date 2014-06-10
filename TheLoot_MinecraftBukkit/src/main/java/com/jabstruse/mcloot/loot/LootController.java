package com.jabstruse.mcloot.loot;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootController {
	// ±¬ÂÊµÝÔö
	// ÎäÆ÷
	private static Material[] weapons = { Material.ARROW, Material.WOOD_SWORD,
			Material.STONE_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD,
			Material.BOW };
	private static Material[] tools = {};

	public static ItemStack[] GetLoots(Entity entity, Player player,LootPlugin plugin) {
		// MonsterType monsterType
		float gf = 1.0f; // Gold Find 100%
		float mf = 1.0f; // Magic Find 100%
		byte maxDrops = 2; // How many items should drops
		byte minDrop = 2; // Min items should drops
		byte realDrop = 0; // How many items will drops
		int itemTypeSize = weapons.length + tools.length;
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		float mfOffset = 0.0f;
		EntityType monsterType = entity.getType();
		// monsterType.
		switch (monsterType) {
		case CREEPER:
			maxDrops += 4;
			minDrop = 3;
			mfOffset += 0.5f;
			break;
		case PIG_ZOMBIE:
			maxDrops += 4;
			minDrop = 3;
			mfOffset += 0.5f;
			break;
		case ZOMBIE:
			maxDrops += 3;
			minDrop = 2;
			mfOffset += 0.25f;
			break;
		case SPIDER:
			maxDrops += 2;
			minDrop = 1;
			mfOffset += 0.15;
			break;
		case ENDERMAN:
			maxDrops += 4;
			minDrop = 4;
			mfOffset += 0.8f;
			break;
		default:
			mfOffset += 0;
			break;
		}
		realDrop = (byte) random.nextInt(maxDrops);

		if (realDrop < minDrop) {
			realDrop = minDrop;
		}
		mf += mfOffset;
		plugin.getLogger().info(" MF : " + mf);
		/*
		String myDisplayName = "Awesome Sword"; // use the displayname you want
												// here
		ItemStack myItem = new ItemStack(Material.DIAMOND_SWORD); // your item
		ItemMeta im = myItem.getItemMeta(); // get the itemmeta of the item
		im.setDisplayName(myDisplayName); // set the displayname
		myItem.setItemMeta(im); // give the item the new itemmeta*/
		return null;
	}
}
