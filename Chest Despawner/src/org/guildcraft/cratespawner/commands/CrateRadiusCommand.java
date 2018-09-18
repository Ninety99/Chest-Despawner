package org.guildcraft.cratespawner.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.guildcraft.cratespawner.CrateSpawner;

public class CrateRadiusCommand implements Listener, CommandExecutor {

	private final CrateSpawner plugin;

	public CrateRadiusCommand(CrateSpawner plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("crateradius")) {
				if (player.hasPermission("spawncrate.setradius"))
					if (args.length == 0) {
						if (args[0].matches("\\d+")) {
							int radius = Integer.parseInt(args[0]);
							plugin.getConfig().set("radius", radius);
						} else {
							sender.sendMessage(ChatColor.RED + "You must specify a number!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Correct Usage: /crateradius <radius>");
					}
			}
		}
		return true;
	}
}
