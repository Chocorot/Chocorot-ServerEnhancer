package net.chocorot.chocorotServerEnhancer.Commands;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			int ping = -1;
			try {
				Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
				ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
			ChatColor color = null;
			if (ping <= 50) {
				color =ChatColor.GREEN ;
			} else if (ping > 50 && ping <= 100) {
				color =ChatColor.YELLOW;
			}else if (ping > 100 && ping <= 300) {
				color =ChatColor.RED;
			}else {
				color =ChatColor.DARK_RED;
			}
			String msg = ChatColor.RED + "Your Ping is: " + color + ping;
			p.sendMessage(msg);
		}else {
			System.out.println("Pong!");
		}
		return false;
	}
}
