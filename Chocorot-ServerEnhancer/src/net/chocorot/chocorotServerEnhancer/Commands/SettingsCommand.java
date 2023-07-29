package net.chocorot.chocorotServerEnhancer.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import net.chocorot.chocorotServerEnhancer.Main;
import net.chocorot.chocorotServerEnhancer.Utils.Config;

public class SettingsCommand implements TabExecutor {

	ChatColor RED = ChatColor.RED;
	ChatColor GREEN = ChatColor.GREEN;
	String dA = "disableAchievement";
	String dB = "disableBed";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0) {

			// Achievement
			if (args[0].equals("achievement")) {
				// Get Achievement
				if (args.length == 1) {
					sender.sendMessage("Disabled Achievement = " + Config.getString("dA"));
				} else {
					// Set Achievement
					switch (args[1]) {
					case "true":
						Config.set("dA", false);
						sender.sendMessage(GREEN + "Achievement enabled!");

						break;

					case "false":
						Config.set("dA", true);
						sender.sendMessage(RED + "Achievement disabled!");

						break;
					default:
						sender.sendMessage(RED + "Syntax Error!");
						break;
					}
				}

			}

			// Bed
			if (args[0].equals("bed")) {
				// Get Bed
				if (args.length == 1) {
					sender.sendMessage("Disabled Bed = " + Config.getString("dB"));
				} else {
					// Set Bed
					switch (args[1]) {
					case "true":
						Config.set("dB", false);
						sender.sendMessage(GREEN + "Bed enabled!");

						break;

					case "false":
						Config.set("dB", true);
						sender.sendMessage(RED + "Bed disabled!");

						break;
					default:
						sender.sendMessage(RED + "Syntax Error!");
						break;
					}
				}

			}
			// Save config
			Main.plugin.saveConfig();

		} else {
			// Return Error
			sender.sendMessage(RED + "/cse <setting> <value>");
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			List<String> settings = new ArrayList<>();
			String[] s = { "achievement", "bed" };
			for (int i = 0; i < s.length; i++) {
				settings.add(s[i]);
			}
			return settings;
		}
		if (args.length == 2) {
			List<String> value = new ArrayList<>();
			String[] v = { "true", "false" };
			for (int i = 0; i < v.length; i++) {
				value.add(v[i]);
			}
			return value;
		}
		return null;
	}

}
