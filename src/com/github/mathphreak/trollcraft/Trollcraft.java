package com.github.mathphreak.trollcraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Trollcraft extends JavaPlugin {
	
	public static ArrayList<TrollingStatus> statuses = new ArrayList<TrollingStatus>();
	private int task;
	private CommandSender lastSender;
	
	private void troll(String username, int initialDuration, boolean usePotions) {
		Player victim = getServer().getPlayer(username);
		if (victim.hasPermission("trollcraft.immune")) {
			lastSender.sendMessage(username + " is immune to trolling!");
			return;
		}
		TrollingStatus status = new TrollingStatus(username, initialDuration, usePotions);
		statuses.add(status);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getName().equalsIgnoreCase("troll")) {
			if (args.length > 0 || args.length < 4) {
				if (sender.hasPermission(command.getPermission())) {
					lastSender = sender;
					String trollee = args[0];
					String potentialDuration = "100";
					String potentialUsePotions = "no";
					if (args.length > 1) {
						potentialDuration = args[1];
					}
					if (args.length > 2) {
						potentialUsePotions = args[2];
					}
					int duration = 100;
					try {
						duration = Integer.parseInt(potentialDuration);
					} catch (NumberFormatException e) {
						potentialUsePotions = potentialDuration;
					}
					boolean usePotions = false;
					if (potentialUsePotions.equalsIgnoreCase("yes")) {
						usePotions = true;
					}
					sender.sendMessage("Trolling " + trollee + " every " + duration + " ticks with" + (usePotions ? "" : "out") + " potions");
					troll(trollee, duration, usePotions);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTask(task);
	}

	@Override
	public void onEnable() {
		task = getServer().getScheduler().scheduleSyncRepeatingTask(this, new TrollWorker(), 20, 5);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		// TODO Auto-generated method stub
		return super.onTabComplete(sender, command, alias, args);
	}
	
}
