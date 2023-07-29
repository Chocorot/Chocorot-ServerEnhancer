package net.chocorot.chocorotServerEnhancer.Utils;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private static YamlConfiguration config;

    // Method to initialize the configuration from the main plugin class
    public void initialize(YamlConfiguration pluginConfig) {
        config = pluginConfig;
    }

    // Method to get a specific configuration value
    public static void set(String key, String value) {
		config.set(key, value);
	}
    
    public static void set(String key, boolean value) {
		config.set(key, value);
	}
    
    public static String getString(String key) {
		return config.getString(key);
	}
}
