package net.chocorot.chocorotServerEnhancer.Listener;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.chocorot.chocorotServerEnhancer.Main;

public class DisableBedListener implements Listener{
	FileConfiguration config = Main.plugin.getConfig();
	
	@EventHandler
    public void onBedClick(PlayerInteractEvent e) {
		if (config.getBoolean("disableBed")) {
			try {
				Material bed = e.getClickedBlock().getType();
				Action action = e.getAction();
				if (bed == Material.BED_BLOCK && action == Action.RIGHT_CLICK_AIR) {
			    	e.setCancelled(true);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
    }
	
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent e) {
		e.setCancelled(true);
		e.getPlayer().setBedSpawnLocation(null, false);
	}
	
	
}
