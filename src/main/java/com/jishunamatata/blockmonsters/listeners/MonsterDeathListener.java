package com.jishunamatata.blockmonsters.listeners;

import java.util.Collection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.jishunamatata.blockmonsters.EntityManager;

public class MonsterDeathListener implements Listener {

	private EntityManager entityManager;

	public MonsterDeathListener(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		Collection<ItemStack> items = entityManager.getDrops(event.getEntity());
		if (items != null) {
			event.getDrops().clear();
			event.getDrops().addAll(items);
		}
	}

}
