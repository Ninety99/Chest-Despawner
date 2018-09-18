package org.guildcraft.cratespawner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.guildcraft.cratespawner.commands.CrateRadiusCommand;
import org.guildcraft.cratespawner.commands.SpawnCrateCommand;

import lombok.Getter;

public class CrateSpawner extends JavaPlugin {

	@Getter
	private static CrateSpawner instance;

	@Override
	public void onEnable() {
		instance = this;

		registerListeners();
		getConfig().options().copyDefaults(true);
		saveConfig();
		CrateUtils.registerRewardsWithChance();

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
		pm.registerEvents(new CrateUtils(), this);
	}

	public void registerAllRewards() {

	}
}