package org.guildcraft.cratespawner;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.guildcraft.cratespawner.commands.CrateRadiusCommand;
import org.guildcraft.cratespawner.commands.SpawnCrateCommand;
import org.guildcraft.cratespawner.listeners.ChestOpenEvent;

import lombok.Getter;

public class CrateSpawner extends JavaPlugin {

	@Getter
	private final Map<String, Integer> rewards = new HashMap<String, Integer>();

	@Getter
	private static CrateSpawner instance;

	@Override
	public void onEnable() {
		instance = this;

		registerListeners();
		getConfig().options().copyDefaults(true);
		saveConfig();

		Bukkit.getLogger().info("CrateSpawner has been enabled!");
	}

	@Override
	public void onDisable() {
		saveConfig();
		Bukkit.getLogger().info("CrateSpawner has been disabled!");
	}

	public void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ChestOpenEvent(instance), this);
		pm.registerEvents(new SpawnCrateCommand(instance), this);
		pm.registerEvents(new CrateRadiusCommand(instance), this);
	}

	public String reward() {
		ConfigurationSection section = getConfig().getConfigurationSection("rewards");
		for (String key : section.getKeys(true)) {
			rewards.put(getConfig().getString("rewards." + key + ".command"),
					getConfig().getInt("rewards." + key + ".chance"));
		}

		return "";
	}
}
