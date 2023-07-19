package net.chocorot.chocorotServerEnhancer.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;

public class RankCommand implements CommandExecutor {
	public Player target;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		LuckPerms luckPerms1 = LuckPermsProvider.get();
		if (args.length > 0) { // Check if the command have more than 1 arguments
			
			sender.sendMessage("Test");
			this.target = Bukkit.getPlayerExact(args[0]);
			
			// Check if the target online
			if (this.target == null) {
				sender.sendMessage(ChatColor.RED + "That player is not online!");
				return true;
			}
			
			try {
				Group group = luckPerms1.getGroupManager().getGroup(args[1]);
				
				// Check if the rank exist
				if (group == null) {
					sender.sendMessage(ChatColor.RED + "That rank doesn't exist!");
					return true;
				}
				
				// Run command to set rank
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
						"lp user " + args[0] + " parent set " + args[1]);
				
				// Output message
				sender.sendMessage(ChatColor.GREEN + "Successfully set " + ChatColor.YELLOW + args[0]
						+ ChatColor.GREEN + "'s rank to " + ChatColor.YELLOW + args[1] + "!");
				target.sendMessage(
						ChatColor.GREEN + "Successfully set your rank to " + ChatColor.YELLOW + args[1] + "!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Wrong arguments! Usage: /rank <player> <rank>");
		}

		return false;
	}
}
