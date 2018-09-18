package org.guildcraft.cratespawner.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.guildcraft.cratespawner.CrateSpawner;
import org.guildcraft.cratespawner.commands.SpawnCrateCommand;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class ChestOpenEvent implements Listener {

	private final CrateSpawner plugin;

	public ChestOpenEvent(CrateSpawner plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!event.getAction().name().endsWith("CLICK_BLOCK"))
			return;

		Block block = event.getClickedBlock();

		if (!SpawnCrateCommand.crateBlock.containsKey(block))
			return;

		Player player = event.getPlayer();
		Location loc = block.getLocation();

		if (!SpawnCrateCommand.crateBlock.containsValue(loc))
			return;

		block.setType(Material.AIR);

		String msg = ChatColor.translateAlternateColorCodes('&',
				plugin.getConfig().getString("foundMessage").replace("{player}", player.getName()).replace("{location}",
						"" + loc.getBlockX() + " " + loc.getBlockZ()));

		Bukkit.broadcastMessage(msg);

		loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.INK_SACK));
		
		for (Hologram hologram : HologramsAPI.getHolograms(plugin)) {
			Location chestHoloLoc = loc.clone().add(0, 2, 0);
			Location hologramLoc = hologram.getLocation();
			
			if (chestHoloLoc.equals(hologramLoc)) {
				hologram.delete();
				break;
			}
		}
	}
}
