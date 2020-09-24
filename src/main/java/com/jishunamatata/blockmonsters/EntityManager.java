package com.jishunamatata.blockmonsters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EntityManager {

	private Map<UUID, Collection<ItemStack>> monsterDrops = new HashMap<>();

	public void addEntry(LivingEntity entity, Collection<ItemStack> items) {
		monsterDrops.put(entity.getUniqueId(), items);
	}

	@Nullable
	public Collection<ItemStack> getDrops(LivingEntity entity) {
		return getDrops(entity.getUniqueId());
	}

	@Nullable
	public Collection<ItemStack> getDrops(UUID uuid) {
		return monsterDrops.get(uuid);
	}

	public void onShutdown() {
		for (Entry<UUID, Collection<ItemStack>> entry : monsterDrops.entrySet()) {
			Entity entity = Bukkit.getEntity(entry.getKey());

			if (entity instanceof LivingEntity && ((LivingEntity) entity).getRemoveWhenFarAway()) {
				Collection<ItemStack> items = entry.getValue();

				items.forEach((item) -> entity.getWorld().dropItemNaturally(entity.getLocation(), item));
				entity.remove();
			}

		}
	}

}
