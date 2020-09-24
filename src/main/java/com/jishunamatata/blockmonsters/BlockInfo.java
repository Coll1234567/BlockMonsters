package com.jishunamatata.blockmonsters;

public class BlockInfo {

	private double chance;
	private double health;

	public BlockInfo(double chance, double health) {
		this.chance = chance;
		this.health = health;
	}

	public double getChance() {
		return chance;
	}

	public double getHealth() {
		return health;
	}

}
