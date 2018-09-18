package org.guildcraft.cratespawner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import lombok.Getter;

public class CrateUtils implements Listener {

	@Getter
	private static final List<String> rewardList = new ArrayList<String>();

	@Getter
	private static final Map<String, Integer> rewardsToChance = new HashMap<String, Integer>();

	@Getter
	private static final Map<String, String> keys = new HashMap<String, String>();

	private static final String KEY_NAME_1 = "current_1";
	private static final String KEY_NAME_2 = "current_2";
	private static final String KEY_NAME_3 = "current_3";

	public static String getReward() {
		return CrateSpawner.getInstance().getConfig().getString("rewards." + getCurrentKey() + ".reward");
	}

	public static void registerRewardsWithChance() {
		for (String key : CrateSpawner.getInstance().getConfig().getConfigurationSection("rewards").getKeys(false)) {
			rewardsToChance.put(CrateSpawner.getInstance().getConfig().getString("rewards." + key + ".reward"),
					CrateSpawner.getInstance().getConfig().getInt("rewards." + key + ".chance"));

			if (keys.get(KEY_NAME_1) == null)
				keys.put(KEY_NAME_1, key);
			else if (keys.get(KEY_NAME_2) == null)
				keys.put(KEY_NAME_2, key);
			else if (keys.get(KEY_NAME_3) == null)
				keys.put(KEY_NAME_3, key);

			if (rewardList.contains(key))
				continue;

			rewardList.add(key);
		}
	}

	public static String getCurrentKey() {
		if (keys.get(KEY_NAME_1) != null)
			return keys.get(KEY_NAME_1);
		else if (keys.get(KEY_NAME_2) != null)
			return keys.get(KEY_NAME_2);
		else if (keys.get(KEY_NAME_3) != null)
			return keys.get(KEY_NAME_3);

		return "";
	}

	public static void giveRewards(Player player) {
		// will change after testing everything
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), getReward());
		player.sendMessage(
				ChatColor.GREEN + "Congratulations! You have found the crate. Your reward was: " + getReward());
	}
}