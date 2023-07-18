package net.chocorot.chocorotServerEnhancer.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		if(p.getAllowFlight()) {
			p.setAllowFlight(false);
			p.sendMessage(ChatColor.GREEN + "Turned off flight!");
		}else {
			p.setAllowFlight(true);
			p.sendMessage(ChatColor.GREEN + "Turned on flight!");
		}
		
		return false;
	}

}
