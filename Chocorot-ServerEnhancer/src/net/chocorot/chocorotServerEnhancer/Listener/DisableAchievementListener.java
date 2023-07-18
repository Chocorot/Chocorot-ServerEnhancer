package net.chocorot.chocorotServerEnhancer.Listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import net.chocorot.chocorotServerEnhancer.Main;

public class DisableAchievementListener implements Listener{
	FileConfiguration config = Main.plugin.getConfig();
	
	@EventHandler
    public void onAchivementGet(PlayerAchievementAwardedEvent e) {
		if (config.getBoolean("disableAchievement")) {
			e.setCancelled(true);
		}
    	
    	
    }

}
