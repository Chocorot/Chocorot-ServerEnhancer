package net.chocorot.chocorotServerEnhancer.Listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class FlyOnJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoinedWorld(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("chocorot.fly")) {
			setFly(p);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinedLobby(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		String w = p.getWorld().getName();
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		// Detect lobby join
		if (w.equals("lobby") && p.hasPermission("chocorot.fly")) {
			// Delay and run setFly()
			executorService.schedule(() -> setFly(p), 10, TimeUnit.MILLISECONDS);
		}
	}

	private void setFly(Player p) {
		p.setAllowFlight(true);
	}

}
