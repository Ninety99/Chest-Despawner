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
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You can't do that!");
			return true;
		}

		Player player = (Player) sender;

		if (!(player.hasPermission("spawncrate.setradius"))) {
			player.sendMessage(ChatColor.RED + "You do not have permissions to execute this command.");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("crateradius")) {
			if (args.length == 0) {
				if (args[0].matches("\\d+")) {
					int radius = Integer.parseInt(args[0]);
					plugin.getConfig().set("radius", radius);
					player.sendMessage(ChatColor.GREEN + "Succesfully set radius.");
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "You must specify a number!");
					return true;
				}
			} else if (args.length > 1) {
				player.sendMessage(ChatColor.RED + "Correct Usage: /crateradius <radius>");
				return true;
			}
		}
		return true;
	}
}