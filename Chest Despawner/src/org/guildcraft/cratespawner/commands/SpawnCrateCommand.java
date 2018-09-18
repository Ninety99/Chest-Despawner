package org.guildcraft.cratespawner.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.guildcraft.cratespawner.CrateSpawner;

public class SpawnCrateCommand implements Listener, CommandExecutor {

	public static final Map<Block, Location> crateBlock = new HashMap<Block, Location>();

	private final CrateSpawner plugin;

	public SpawnCrateCommand(CrateSpawner plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You cannot use this command!");
			return true;
		}
		Player player = (Player) sender;

		if (!(player.hasPermission("spawncrate.spawncrate"))) {
			player.sendMessage(ChatColor.RED + "You do not have permissions to use this command!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("spawncrate")) {
			if (args.length > 0) {
				player.sendMessage(ChatColor.RED + "Invalid command.");
				return true;
			}

			int x = ThreadLocalRandom.current().nextInt(-plugin.getConfig().getInt("radius"));
			int z = ThreadLocalRandom.current().nextInt(plugin.getConfig().getInt("radius"));
			int y = player.getWorld().getHighestBlockYAt(x, z);

			Location loc = new Location(player.getWorld(), x, y, z);

			String crate = plugin.getConfig().getString("crateType").toUpperCase();

			// Hologram hologram = HologramsAPI.createHologram(plugin, loc.clone().add(0, 2,
			// 0));

			// TextLine line = hologram.appendTextLine(ChatColor.GREEN + "Item Crate");

			loc.clone().add(0, 1, 0).getBlock().setType(Material.getMaterial(crate));

			Bukkit.broadcastMessage(
					ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("spawnMessage")));

			return true;
		}
		return true;
	}
}
