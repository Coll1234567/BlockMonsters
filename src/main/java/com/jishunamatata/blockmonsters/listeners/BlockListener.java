package com.jishunamatata.blockmonsters.listeners;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import com.jishunamatata.blockmonsters.BlockInfo;
import com.jishunamatata.blockmonsters.ConfigManager;
import com.jishunamatata.blockmonsters.EntityManager;

public class BlockListener implements Listener {

	private ConfigManager configManager;
	private EntityManager entityManager;

	private Random random = new Random();

	public BlockListener(ConfigManager configManager, EntityManager entityManager) {
		this.configManager = configManager;
		this.entityManager = entityManager;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}

		Material material = event.getBlock().getType();

		if (this.configManager.isSolidOnly() && !material.isSolid()) {
			return;
		}

		BlockInfo blockInfo = this.configManager.getBlockInfo(material);

		if (blockInfo == null || this.random.nextFloat() >= blockInfo.getChance()) {
			return;
		}

		Location center = event.getBlock().getLocation().add(0.5, 0, 0.5);

		Zombie zombie = center.getWorld().spawn(center, Zombie.class);
		zombie.setBaby();
		zombie.setRemoveWhenFarAway(true); // True may be the default value

		EntityEquipment equipment = zombie.getEquipment();
		equipment.setHelmet(new ItemStack(material));
		equipment.setHelmetDropChance(0.0f);

		AttributeInstance healthAttribute = zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		healthAttribute.setBaseValue(blockInfo.getHealth());
		zombie.setHealth(healthAttribute.getBaseValue());

		entityManager.addEntry(zombie, event.getBlock().getDrops());
		event.setDropItems(false);
	}

}
