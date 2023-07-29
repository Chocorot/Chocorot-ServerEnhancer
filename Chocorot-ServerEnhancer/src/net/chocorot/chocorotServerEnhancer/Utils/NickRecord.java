package net.chocorot.chocorotServerEnhancer.Utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import net.chocorot.chocorotServerEnhancer.Main;

public class NickRecord {
	
	private static YamlConfiguration nickRecord;
	
	public void initialize(YamlConfiguration pluginConfig) {
        nickRecord = pluginConfig;
    }
	
	public static void set(String key, String value) {
		nickRecord.set(key, value);
	}
	
	public static String getString(String key) {
		return nickRecord.getString(key);
	}
	
	public static void saveConfig() {
		try {
			nickRecord.save(new File(Main.plugin.getDataFolder(), "nick.yml"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static boolean contains(String key) {
		return nickRecord.contains(key);
	}
}
