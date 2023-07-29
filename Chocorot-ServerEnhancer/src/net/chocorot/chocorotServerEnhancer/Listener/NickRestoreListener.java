package net.chocorot.chocorotServerEnhancer.Listener;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.chocorot.chocorotServerEnhancer.Utils.NickRecord;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import xyz.haoshoku.nick.api.NickAPI;

public class NickRestoreListener implements Listener {

	LuckPerms lp = LuckPermsProvider.get();
	ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		UUID UUID = player.getUniqueId();
		String name = NickRecord.getString("nick." + UUID + ".name");
		String rank = NickRecord.getString("nick." + UUID + ".rank");
		if (isNick(player)) {
			setNick(player, name, rank);
		}
	}

	private boolean isNick(Player player) {
		String UUID = player.getUniqueId().toString();
		return NickRecord.contains("nick." + UUID);
	}

	private void setNick(Player player, String name, String rank) {
		String prefix = null;
		switch (rank.toLowerCase()) {
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
		case "default":
			prefix = ChatColor.GRAY + "";
			break;
		default:
			break;
		}

		final String pre = prefix;
		NickAPI.nick(player, name);
		player.setDisplayName(pre + name);
		NickAPI.setSkin(player, name);
		NickAPI.setUniqueId(player, name);
		NickAPI.setGameProfileName(player, name);
		executorService.schedule(() -> setRank(player, pre), 150, TimeUnit.MILLISECONDS);
		NickAPI.refreshPlayer(player);

	}

	public void setRank(Player player, String rank) {
		User user = lp.getPlayerAdapter(Player.class).getUser(player);
		PrefixNode node = PrefixNode.builder(rank, 100).build();
		node.toBuilder().withContext("none", "none").build();
		user.data().clear(NodeType.PREFIX.predicate(mn -> mn.getKey().equals("prefix")));
		// add the new node
		user.data().add(node);

		// save!
		lp.getUserManager().saveUser(user);
	}
}
