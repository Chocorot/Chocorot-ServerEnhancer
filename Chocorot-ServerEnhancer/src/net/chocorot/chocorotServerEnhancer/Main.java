package net.chocorot.chocorotServerEnhancer;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.chocorot.chocorotServerEnhancer.Commands.Debug;
import net.chocorot.chocorotServerEnhancer.Commands.FlyCommand;
import net.chocorot.chocorotServerEnhancer.Commands.NickCommand;
import net.chocorot.chocorotServerEnhancer.Commands.PingCommand;
import net.chocorot.chocorotServerEnhancer.Commands.RankCommand;
import net.chocorot.chocorotServerEnhancer.Commands.SettingsCommand;
import net.chocorot.chocorotServerEnhancer.Listener.DisableAchievementListener;
import net.chocorot.chocorotServerEnhancer.Listener.DisableBedListener;
import net.chocorot.chocorotServerEnhancer.Listener.DisableDeathListener;
import net.chocorot.chocorotServerEnhancer.Listener.FlyOnJoinListener;
import net.chocorot.chocorotServerEnhancer.Listener.JoinMessageListener;
import net.chocorot.chocorotServerEnhancer.Listener.NickRestoreListener;
import net.chocorot.chocorotServerEnhancer.Utils.Config;
import net.chocorot.chocorotServerEnhancer.Utils.NickRecord;
import net.milkbowl.vault.chat.Chat;

public class Main extends JavaPlugin {
	private File configFile, nickRecordFile;
    private YamlConfiguration config, nickRecord;

	public static Main plugin;
	private static Chat chat = null;

	@Override
	public void onEnable() {
		plugin = this;
		setupChat();

		// Listeners
		getServer().getPluginManager().registerEvents(new DisableAchievementListener(), this);
		getServer().getPluginManager().registerEvents(new DisableBedListener(), this);
		getServer().getPluginManager().registerEvents(new FlyOnJoinListener(), this);
		getServer().getPluginManager().registerEvents(new JoinMessageListener(), this);
		getServer().getPluginManager().registerEvents(new DisableDeathListener(), this);
		getServer().getPluginManager().registerEvents(new NickRestoreListener(), this);

		// Commands
		this.getCommand("cse").setExecutor(new SettingsCommand());
		this.getCommand("fly").setExecutor(new FlyCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("nick").setExecutor(new NickCommand());
		this.getCommand("chdebug").setExecutor(new Debug());

		// Config
		/*config.addDefault("disableAchievement", true);
		config.addDefault("disableBed", true);
		config.addDefault("disableDeath", true);
		config.options().copyDefaults(true);
		saveConfig();*/
		setupConfig();

	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	public static Chat getChat() {
		return chat;
	}
	
	private void setupConfig() {
		// Create a new file instance for your config
        configFile = new File(getDataFolder(), "config.yml");
        nickRecordFile = new File(getDataFolder(), "nick.yml");

        // Check if the file exists, if not, create it
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!nickRecordFile.exists()) {
        	nickRecordFile.getParentFile().mkdirs();
            saveResource("nick.yml", false);
        }

        // Load the config
        config = loadConfig(configFile);
        nickRecord = loadConfig(nickRecordFile);

        // You can now access the config using the 'config' variable
        // For example, config.getString("some_key");

		// Pass the config to another class or use a getter method
        new Config().initialize(config);
        new NickRecord().initialize(nickRecord);
	}

	private YamlConfiguration loadConfig(File configFile) {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource(configFile.getName(), false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }
	public void saveConfig(YamlConfiguration config, File configFile) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@Override
    public void onDisable() {
        // Save the configurations before the plugin shuts down
        saveConfig(config, configFile);
        saveConfig(nickRecord, nickRecordFile);
    }

	
}
