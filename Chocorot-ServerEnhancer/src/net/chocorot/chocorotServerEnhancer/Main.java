package net.chocorot.chocorotServerEnhancer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.chocorot.chocorotServerEnhancer.Commands.FlyCommand;
import net.chocorot.chocorotServerEnhancer.Commands.PingCommand;
import net.chocorot.chocorotServerEnhancer.Commands.SettingsCommand;
import net.chocorot.chocorotServerEnhancer.Listener.DisableAchievementListener;
import net.chocorot.chocorotServerEnhancer.Listener.DisableBedListener;
import net.chocorot.chocorotServerEnhancer.Listener.DisableDeathListener;
import net.chocorot.chocorotServerEnhancer.Listener.FlyOnJoinListener;
import net.chocorot.chocorotServerEnhancer.Listener.JoinMessageListener;
import net.milkbowl.vault.chat.Chat;

public class Main extends JavaPlugin {
	FileConfiguration config = getConfig();
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

		// Commands
		this.getCommand("cse").setExecutor(new SettingsCommand());
		this.getCommand("fly").setExecutor(new FlyCommand());
		this.getCommand("ping").setExecutor(new PingCommand());

		// Config
		config.addDefault("disableAchievement", true);
		config.addDefault("disableBed", true);
		config.addDefault("disableDeath", true);
		config.options().copyDefaults(true);
		saveConfig();
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	public static Chat getChat() {
		return chat;
	}
}
