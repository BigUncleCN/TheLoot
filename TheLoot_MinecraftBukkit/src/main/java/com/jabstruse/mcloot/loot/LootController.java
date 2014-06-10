package com.jabstruse.mcloot.loot;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
	private static Material[] tools = { Material.WOOD_AXE, Material.WOOD_HOE,
			Material.WOOD_PICKAXE, Material.WOOD_SPADE };

	public static ItemStack[] GetLoots(Entity entity, Player player,
			LootPlugin plugin) {
		// MonsterType monsterType
		float mf = 1.0f; // Magic Find 100%
		byte maxDrops = 2; // How many items should drops
		byte minDrop = 2; // Min items should drops
		byte realDrop = 0; // How many items will drops
		byte itemQualitySize = (byte) ItemQuality.values().length;
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		float mfOffset = 0.0f;
		EntityType monsterType = entity.getType();
		
		if(random.nextInt(100) < 95){
			return null;
		}
		
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
		realDrop = (byte) random.nextInt(maxDrops);

		if (realDrop < minDrop) {
			realDrop = minDrop;
		}
		mf += mfOffset;
		if (plugin != null)
			plugin.getLogger().info(" MF : " + mf + " DROPS : " + realDrop);
		else
			System.out.println(" MF : " + mf + " DROPS : " + realDrop);
		ItemQuality itemQuality = null;
		ItemStack[] itemStacks = new ItemStack[realDrop];
		for (int i = 0; i < realDrop; i++) {
			int isTool = random.nextInt(2);
			int r = 0;
			Material material = null;
			if (isTool == 0) {
				r = random.nextInt(tools.length);
				material = tools[r];
			} else if (isTool == 1) {
				r = random.nextInt(weapons.length);
				material = weapons[r];
			}
			int r1 = -1;
			for (int j = 0; j < itemQualitySize; j++) {
				r1 = (random.nextInt(99) + 1)
						+ random.nextInt(itemQualitySize * 2);
				int needle = (j * 10) + 30;
				if (j == 0) {
					needle = 5;
				}
				if (j == 1) {
					needle = 3;
				}
				if (mf > 1) {
					// worked when Magic Find Bigger Than 100%
					r1 = r1 - (int) (needle * mf);
				} else if (mf < 1) {
					r1 = r1 + (int) (needle * mf);
				}
				System.out.println(" NEED : " + needle + " ROLL : " + r1);
				if (r1 < needle) {
					itemQuality = ItemQuality.values()[j];
					break;
				}

			}
			if (itemQuality == null)
				itemQuality = ItemQuality.Nomore;
			// item.setItemQuality(itemQuality);
			// item.setItemType(itemType);

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
			if (plugin != null)
				plugin.getLogger().info(
						" => Type " + material + "\tQuality\t" + itemQuality
								+ " Enchantment Size : " + enchantmentSize);
			else
				System.out.println(" => Type " + material + "\tQuality\t"
						+ itemQuality + "");
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
				while (enchanted < 1) {
					int rnd = random.nextInt(Enchantment.values().length);
					Enchantment ench = Enchantment.values()[rnd];
					plugin.getLogger().info("Enchantment : " + ench);
					try {
						itemStacks[i].addEnchantment(ench, lv);
						enchanted++;
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}

			}
		}
		return itemStacks;
		/*
		 * String myDisplayName = "Awesome Sword"; // use the displayname you
		 * want // here ItemStack myItem = new
		 * ItemStack(Material.DIAMOND_SWORD); // your item ItemMeta im =
		 * myItem.getItemMeta(); // get the itemmeta of the item
		 * im.setDisplayName(myDisplayName); // set the displayname
		 * myItem.setItemMeta(im); // give the item the new itemmeta
		 */
	}
}
