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

	ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		executorService.schedule(() -> setMessage(p), 100, TimeUnit.MILLISECONDS);

		// Set player's display name for some reason
		p.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getChat().getPlayerPrefix(p) + p.getName()));
	}

	private void sendMessage(Player p, String message) {
		World w = p.getWorld();

		// Send message to everyone
		for (Player player : w.getPlayers()) {
			player.sendMessage(message);
		}
	}

	private void setMessage(Player p) {
		String name = p.getDisplayName();

		// Join message format
		String message = name + ChatColor.GOLD + " joined the lobby!";

		// Delay and run sendMessage()
		executorService.schedule(() -> sendMessage(p, message), 100, TimeUnit.MILLISECONDS);
	}

}
