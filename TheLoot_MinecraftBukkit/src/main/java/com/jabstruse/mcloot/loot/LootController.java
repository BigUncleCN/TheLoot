package com.jabstruse.mcloot.loot;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootController {
	public static ItemStack[] GetLoots(LivingEntity entity, Player player,
			LootPlugin plugin, List<String> itemDrops, int testNeedle) {
		// MonsterType monsterType
		double mf = 1.0f; // Magic Find 100%
		byte maxDrops = 2; // How many items should drops
		byte minDrop = 2; // Min items should drops
		byte realDrop = 0; // How many items will drops
		byte itemQualitySize = (byte) ItemQuality.values().length;
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		double mfOffset = 0.0f;
		EntityType monsterType = entity.getType();
		int testInt = random.nextInt(100);
		mfOffset += (entity.getMaxHealth() / 2) / 10;
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
			testNeedle = 60;
			break;
		case PIG:
			maxDrops = 1;
			minDrop = 0;
			mfOffset += 0.2;
		case COW:
			maxDrops = 1;
			minDrop = 0;
			mfOffset += 0.2;
		case CHICKEN:
			maxDrops = 1;
			minDrop = 0;
			mfOffset += 0.2;
		default:
			mfOffset += 0;
			break;
		}
		if (testInt < testNeedle) {
			return null;
		}
		realDrop = (byte) random.nextInt(maxDrops);
		if (realDrop < minDrop) {
			realDrop = minDrop;
		}
		mf += mfOffset;
		//plugin.getLogger().info(" [" + entity.getType().name() + "] Health : " + entity.getMaxHealth() + " MF:" + mf);
		ItemQuality itemQuality = null;
		ItemStack[] itemStacks = new ItemStack[realDrop];
		for (int i = 0; i < realDrop; i++) {
			int r = 0;
			Material material = null;
			r = random.nextInt(itemDrops.size());
			String itemD = itemDrops.get(r);
			String dd[] = itemD.split(":");
			double dropRate = Float.parseFloat(dd[1]);
			double rr = random.nextInt(99) + 1;
			rr = rr - (rr * ((mf-1) / 2));
			//plugin.getLogger().info(" == > Item " + itemD + " RR:" + rr);
			while (rr > dropRate) {
				r = random.nextInt(itemDrops.size());
				itemD = itemDrops.get(r);
				dd = itemD.split(":");
				dropRate = Double.parseDouble(dd[1]);
				rr = random.nextInt(99) + 1;
				rr = rr - (rr * ((mf-1) / 2));
				//plugin.getLogger().info(" Item " + itemD + " RR:" + rr);
			}
			
			material = Material.getMaterial(dd[0]);
			double r1 = -1;
			for (int j = 0; j < itemQualitySize; j++) {
				r1 = (random.nextInt(99) + 1);
				r1 = r1 - (r1 * ((mf-1) / 2));
				//System.out.println("Item:" + ItemQuality.values()[itemQualitySize-j-1] + " -> " + r1 + "/" + dropRate);
				if (r1 <= dropRate) {
					itemQuality = ItemQuality.values()[itemQualitySize-j-1];
					break;
				}
				dropRate -= (dropRate * 0.4);
			}
			if (itemQuality == null)
				itemQuality = ItemQuality.Nomore;
			int enchantmentSize = 0;
			String display_name = "";
			if (itemQuality == ItemQuality.Magical) {
				enchantmentSize = 1;
				display_name = ChatColor.GREEN + "";
			} else if (itemQuality == ItemQuality.Set) {
				enchantmentSize = 2;
				display_name = ChatColor.BLUE + "";
			} else if (itemQuality == ItemQuality.Unique) {
				enchantmentSize = 3;
				display_name = ChatColor.YELLOW + "";
			} else if (itemQuality == ItemQuality.Rare) {
				enchantmentSize = 4;
				display_name = ChatColor.RED + "";
			}
			itemStacks[i] = new ItemStack(material);
			itemStacks[i].setAmount(1);

			ItemMeta im = itemStacks[i].getItemMeta();
			display_name = display_name + itemStacks[i].getType();
			im.setDisplayName(display_name);

			itemStacks[i].setItemMeta(im);

			for (int e = 0; e < enchantmentSize; e++) {
				int lv = random.nextInt(2) + 1
						+ (random.nextInt(enchantmentSize) / 2);
				int enchanted = 0;
				int enchErr = 0;
				while (enchanted < 1) {
					int rnd = random.nextInt(Enchantment.values().length);
					Enchantment ench = Enchantment.values()[rnd];
					//
					try {
						itemStacks[i].addEnchantment(ench, lv);
						//plugin.getLogger().info("Enchantment : " + ench);
						enchanted++;
					} catch (Exception e2) {
						enchErr++;
						// TODO: handle exception
					}
					if (enchErr >= 5) {
						break;
					}
				}

			}
		}
		return itemStacks;
	}
}
