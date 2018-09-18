package org.guildcraft.cratespawner;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
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
	private final Map<String, String> keys = new HashMap<String, String>();

	private final String KEY_NAME_1 = "current";
	private final String KEY_NAME_2 = "current2";
	private final String KEY_NAME_3 = "current3";

	@Getter
	private static CrateSpawner instance;

	@Override
	public void onEnable() {
		instance = this;

		registerListeners();
		getConfig().options().copyDefaults(true);
		saveConfig();
		registerRewards();

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

	public String getReward() {
		return getConfig().getString("rewards." + getCurrentKey() + ".reward");
	}

	public void registerRewards() {
		for (String key : getConfig().getConfigurationSection("rewards").getKeys(false)) {
			rewards.put(getConfig().getString("rewards." + key + ".reward"),
					getConfig().getInt("rewards." + key + ".chance"));

			if (keys.get(KEY_NAME_1) == null)
				keys.put(KEY_NAME_1, key);
			else if (keys.get(KEY_NAME_2) == null)
				keys.put(KEY_NAME_2, key);
			else if (keys.get(KEY_NAME_3) == null)
				keys.put(KEY_NAME_3, key);
		}
	}

	public String getCurrentKey() {
		if (keys.get(KEY_NAME_1) != null)
			return keys.get(KEY_NAME_1);
		else if (keys.get(KEY_NAME_2) != null)
			return keys.get(KEY_NAME_2);
		else if (keys.get(KEY_NAME_3) != null)
			return keys.get(KEY_NAME_3);

		return "";
	}
}
