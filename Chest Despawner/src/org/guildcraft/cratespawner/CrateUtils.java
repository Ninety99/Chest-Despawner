package org.guildcraft.cratespawner;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Listener;

import lombok.Getter;

public class CrateUtils implements Listener {

	@Getter
	private static final Map<String, Integer> rewards = new HashMap<String, Integer>();

	@Getter
	private static final Map<String, String> keys = new HashMap<String, String>();

	private static final String KEY_NAME_1 = "current_1";
	private static final String KEY_NAME_2 = "current_2";
	private static final String KEY_NAME_3 = "current_3";

	public static String getReward() {
		return CrateSpawner.getInstance().getConfig().getString("rewards." + getCurrentKey() + ".reward");
	}

	public static void registerRewards() {
		for (String key : CrateSpawner.getInstance().getConfig().getConfigurationSection("rewards").getKeys(false)) {
			rewards.put(CrateSpawner.getInstance().getConfig().getString("rewards." + key + ".reward"),
					CrateSpawner.getInstance().getConfig().getInt("rewards." + key + ".chance"));

			if (keys.get(KEY_NAME_1) == null)
				keys.put(KEY_NAME_1, key);
			else if (keys.get(KEY_NAME_2) == null)
				keys.put(KEY_NAME_2, key);
			else if (keys.get(KEY_NAME_3) == null)
				keys.put(KEY_NAME_3, key);
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
}
