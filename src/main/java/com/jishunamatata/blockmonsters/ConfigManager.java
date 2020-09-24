package com.jishunamatata.blockmonsters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteStreams;

public class ConfigManager {

	private final Map<Material, BlockInfo> materialMap = new EnumMap<Material, BlockInfo>(Material.class);
	private boolean solidOnly;

	private final Plugin plugin;

	public ConfigManager(Plugin plugin) {
		this.plugin = plugin;
		loadConfig();
	}

	public void loadConfig() {
		File file = loadResource(this.plugin, "config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		this.solidOnly = config.getBoolean("solid-only", true);

		double baseChance = (float) config.getDouble("spawn-chance");
		double baseHealth = config.getDouble("mob-health");

		for (Material material : Material.values()) {
			if (material.isBlock() && !material.isAir()) {
				double chance = baseChance;
				double health = baseHealth;

				String path = "overrides." + material.name();
				if (config.isSet(path)) {
					chance = config.getDouble(path + ".spawn-chance", baseChance);
					health = config.getDouble(path + ".mob-health", baseHealth);
				}

				materialMap.put(material, new BlockInfo(chance, health));
			}
		}
	}

	public void reloadConfig() {
		this.materialMap.clear();
		loadConfig();
	}

	public boolean isSolidOnly() {
		return solidOnly;
	}

	public BlockInfo getBlockInfo(Material material) {
		return materialMap.get(material);
	}

	private File loadResource(Plugin plugin, String resource) {
		File folder = plugin.getDataFolder();
		File resourceFile = new File(folder, resource);
		if (!resourceFile.exists()) {
			try {
				resourceFile.createNewFile();
				try (InputStream in = plugin.getResource(resource);
						OutputStream out = new FileOutputStream(resourceFile)) {
					ByteStreams.copy(in, out);
				}

			} catch (Exception e) {
				Bukkit.getLogger().severe("Error copying file " + resource);
			}
		}
		return resourceFile;
	}
}
