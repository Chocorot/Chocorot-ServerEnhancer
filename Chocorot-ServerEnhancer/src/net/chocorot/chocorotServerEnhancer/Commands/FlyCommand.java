package net.chocorot.chocorotServerEnhancer.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length > 0) {
			Player target = Bukkit.getPlayerExact(args[0]);
			setFly(target);
		}else {
			Player p = (Player) sender;
		setFly(p);
		}
		return false;
	}
	
	private void setFly(Player p) {
		if(p.getAllowFlight()) {
			p.setAllowFlight(false);
			p.sendMessage(ChatColor.GREEN + "Turned off flight!");
		}else {
			p.setAllowFlight(true);
			p.sendMessage(ChatColor.GREEN + "Turned on flight!");
		}
	}

}
