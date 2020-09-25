package com.jishunamatata.blockmonsters;

import org.bukkit.ChatColor;

public class BlockInfo {

	private double chance;
	private double health;
	private String name;

	public BlockInfo(double chance, double health, String name) {
		this.chance = chance;
		this.health = health;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
	}

	public double getChance() {
		return chance;
	}

	public double getHealth() {
		return health;
	}

	public String getName() {
		return name;
	}

}
