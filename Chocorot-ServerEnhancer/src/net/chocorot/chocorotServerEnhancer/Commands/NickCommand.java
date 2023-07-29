package net.chocorot.chocorotServerEnhancer.Commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.chocorot.chocorotServerEnhancer.Main;
import net.chocorot.chocorotServerEnhancer.Utils.NickRecord;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import xyz.haoshoku.nick.api.NickAPI;

public class NickCommand implements CommandExecutor {

	LuckPerms lp = LuckPermsProvider.get();
	ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		Collection<String> possibleGroups = new ArrayList<String>();
		possibleGroups.add("owner");
		possibleGroups.add("admin");
		possibleGroups.add("youtuber");
		possibleGroups.add("mvp++");
		possibleGroups.add("mvp+");
		possibleGroups.add("mvp");
		possibleGroups.add("vip+");
		possibleGroups.add("vip");

		if (args.length == 0) {
			wrongArgument(player);
			return true;
		}

		switch (args[0]) {
		case "reset":
			resetNick(player);
			resetRecord(player);
			break;

		default:
			setNick(player, args);
			saveToRecord(player, args);
			break;
		}

		return true;
	}

	private void resetNick(Player player) {
		String name = player.getName();
		String prefix = ChatColor.translateAlternateColorCodes('&', Main.getChat().getPlayerPrefix(player));
		removeRank(player);
		NickAPI.resetNick(player);
		NickAPI.resetSkin(player);
		NickAPI.resetUniqueId(player);
		NickAPI.resetGameProfileName(player);
		player.setDisplayName(prefix + name);
		NickAPI.refreshPlayer(player);
		player.sendMessage(ChatColor.DARK_RED + "Successfully reset nick");
	}

	private void wrongArgument(Player player) {
		player.sendMessage(ChatColor.YELLOW + "/nick reset");
		player.sendMessage(ChatColor.YELLOW + "/nick <Name> <Rank>");
	}

	private void setNick(Player player, String args[]) {
		String prefix = null;
		if (args.length == 2) {
			switch (args[1].toLowerCase()) {
			case "vip":
				prefix = ChatColor.GREEN + "[VIP] ";
				break;
			case "vip+":
				prefix = ChatColor.GREEN + "[VIP" + ChatColor.GOLD + "+" + ChatColor.GREEN + "] ";
				break;
			case "mvp":
				prefix = ChatColor.AQUA + "[MVP] ";
				break;
			case "mvp+":
				prefix = ChatColor.AQUA + "[MVP" + ChatColor.GOLD + "+" + ChatColor.AQUA + "] ";
				break;
			case "mvp++":
				prefix = ChatColor.GOLD + "[MVP++] ";
				break;
			default:
				break;
			}
		}else {
			prefix = ChatColor.GRAY + "";
		}
		
		final String pre = prefix;
		String name = args[0];
		NickAPI.nick(player, name);
		player.setDisplayName(pre + name);
		NickAPI.setSkin(player, name);
		NickAPI.setUniqueId(player, name);
		NickAPI.setGameProfileName(player, name);
		executorService.schedule(() -> setRank(player, pre), 150, TimeUnit.MILLISECONDS);
		NickAPI.refreshPlayer(player);
		player.sendMessage(ChatColor.DARK_GREEN + "Successfully set the nickname to " + ChatColor.YELLOW + name);

	}

	private void setRank(Player player, String rank) {
		User user = lp.getPlayerAdapter(Player.class).getUser(player);
		PrefixNode node = PrefixNode.builder(rank, 100).build();
		node.toBuilder().withContext("none", "none").build();
		user.data().clear(NodeType.PREFIX.predicate(mn -> mn.getKey().equals("prefix")));
		// add the new node
		user.data().add(node);

		// save!
		lp.getUserManager().saveUser(user);
	}

	private void removeRank(Player player) {
		User user = lp.getPlayerAdapter(Player.class).getUser(player);
		user.data().clear(NodeType.PREFIX.predicate());

		// save!
		lp.getUserManager().saveUser(user);
	}

	private void saveToRecord(Player player, String[] args) {
		UUID UUID = player.getUniqueId();
		String name = args[0],rank;
		if(args.length == 2) {
			rank = args[1];
		}else {
			rank = "default";
		}
		NickRecord.set("nick." + UUID + ".name", name);
		NickRecord.set("nick." + UUID + ".rank", rank);
		NickRecord.saveConfig();
	}
	
	private void resetRecord(Player player) {
		UUID UUID = player.getUniqueId();
		NickRecord.set("nick." + UUID, null);
		NickRecord.saveConfig();
	}

}
