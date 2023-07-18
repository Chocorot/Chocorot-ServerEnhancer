package net.chocorot.chocorotServerEnhancer.Commands;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCheatCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player player = (Player) arg0;
		World world = player.getWorld();
		Location l1,l2,l3,l4;
		l1 = new Location(world, -24.5, 57.1, 2.5);
		l2 = new Location(world, -47.5, 53.1, 19.5);
		l3 = new Location(world, -28.5, 53.1, 53.3);
		l4 = new Location(world, 22.5, 57.1, -9.5);
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		
		
		if (player.isOp()) {
			executorService.schedule(() -> tp(player,l1), 100, TimeUnit.MILLISECONDS);
			executorService.schedule(() -> tp(player,l2), 300, TimeUnit.MILLISECONDS);
			executorService.schedule(() -> tp(player,l3), 600, TimeUnit.MILLISECONDS);
			executorService.schedule(() -> tp(player,l4), 900, TimeUnit.MILLISECONDS);
			player.sendMessage("FUCK U");
		}
		return false;
	}
	
	private void tp(Player player, Location l) {
		
		player.teleport(l);
	}

	
	
}
