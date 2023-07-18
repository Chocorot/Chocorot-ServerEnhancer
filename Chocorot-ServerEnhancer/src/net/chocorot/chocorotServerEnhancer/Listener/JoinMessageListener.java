package net.chocorot.chocorotServerEnhancer.Listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.chocorot.chocorotServerEnhancer.Main;

public class JoinMessageListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		Player p = e.getPlayer();
		String n = p.getName();
		String prefix = cc(Main.getChat().getPlayerPrefix(p));

		// Join message format
		String message = prefix + n + ChatColor.GOLD + " joined the lobby!";

		// Delay and run sendMessage()
		executorService.schedule(() -> sendMessage(p, message), 100, TimeUnit.MILLISECONDS);

	}

	private void sendMessage(Player p, String message) {
		World w = p.getWorld();

		// Send message to everyone
		for (Player player : w.getPlayers()) {
			player.sendMessage(message);
		}
	}

	private String cc(String txt) {
		return ChatColor.translateAlternateColorCodes('&', txt);
	}

}
