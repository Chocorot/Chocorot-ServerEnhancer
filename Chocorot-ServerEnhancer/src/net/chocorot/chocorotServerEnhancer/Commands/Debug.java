package net.chocorot.chocorotServerEnhancer.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.chocorot.chocorotServerEnhancer.Main;
import net.chocorot.chocorotServerEnhancer.Utils.NickRecord;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class Debug implements CommandExecutor {

	LuckPerms lp = LuckPermsProvider.get();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		sender.sendMessage("Display name: " + p.getDisplayName());
		sender.sendMessage("Custom name: " + p.getCustomName());
		sender.sendMessage("Playerlist name: " + p.getPlayerListName());
		sender.sendMessage("Name: " + p.getName());
		String prefix = Main.getChat().getPlayerPrefix(p);
		sender.sendMessage("Prefix: " + prefix);
		
		String UUID = p.getUniqueId().toString();
		sender.sendMessage("Is Nicked: " + NickRecord.contains("nick." + UUID));
		return false;
	}

}
