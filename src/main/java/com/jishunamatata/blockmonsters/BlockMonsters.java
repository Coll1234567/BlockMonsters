package com.jishunamatata.blockmonsters;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.jishunamatata.blockmonsters.commands.BlockMonsterCommand;
import com.jishunamatata.blockmonsters.listeners.BlockListener;
import com.jishunamatata.blockmonsters.listeners.MonsterDeathListener;

public class BlockMonsters extends JavaPlugin {

	private static BlockMonsters plugin;

	private ConfigManager configManager = new ConfigManager(this);
	private EntityManager entityManager = new EntityManager();

	public void onEnable() {
		plugin = this;

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BlockListener(configManager, entityManager), this);
		pm.registerEvents(new MonsterDeathListener(entityManager), this);
		
		getCommand("blockmonsters").setExecutor(new BlockMonsterCommand(configManager));
	}

	public void onDisable() {
		entityManager.onShutdown();
	}
	
	public static BlockMonsters getInstance() {
		return plugin;
	}
}
