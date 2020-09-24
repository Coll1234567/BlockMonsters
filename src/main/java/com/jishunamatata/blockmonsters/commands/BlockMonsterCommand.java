package com.jishunamatata.blockmonsters.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.jishunamatata.blockmonsters.ConfigManager;
import com.jishunamatata.blockmonsters.PluginStrings;

public class BlockMonsterCommand implements CommandExecutor, TabCompleter {

	private final ConfigManager configManager;
	private final List<String> arguments = Arrays.asList("reload");

	public BlockMonsterCommand(ConfigManager configManager) {
		this.configManager = configManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length < 1) {
			return false;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("blockmonsters.reload")) {
				sender.sendMessage(PluginStrings.ERROR_ICON + ChatColor.RED
						+ "You do not have permission to perform this command.");
				return true;
			}
			configManager.reloadConfig();
			sender.sendMessage(PluginStrings.SUCCESS_ICON + ChatColor.GREEN + "Configuration reloaded.");
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			List<String> argList = new ArrayList<String>();
			StringUtil.copyPartialMatches(args[0], arguments, argList);
			return argList;
		}
		return null;
	}

}
