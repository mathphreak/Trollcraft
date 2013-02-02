package com.github.mathphreak.trollcraft;

import java.util.EnumSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TrollWorker implements Runnable {
	
	public static Effect[] noises = {
		Effect.BLAZE_SHOOT,
		Effect.BOW_FIRE,
		Effect.CLICK1,
		Effect.CLICK2,
		Effect.DOOR_TOGGLE,
		Effect.EXTINGUISH,
		Effect.GHAST_SHOOT,
		Effect.GHAST_SHRIEK,
		Effect.ZOMBIE_CHEW_IRON_DOOR,
		Effect.ZOMBIE_CHEW_WOODEN_DOOR,
		Effect.ZOMBIE_DESTROY_DOOR
	};
	
	public static PotionEffect[] potions = {
		new PotionEffect(PotionEffectType.CONFUSION, 800, 400, true)
	};

	@Override
	public void run() {
		for (TrollingStatus status : Trollcraft.statuses) {
			if (status.isInvalid()) continue;
			if (status.getDuration() < 0) {
				status.invalidate();
				Bukkit.getLogger().info("Finished trolling " + status.getUsername());
				continue;
			}
			if (status.shouldUpdate()) {
				status.update();
				int noisesLength = noises.length;
				EnumSet<Sound> sounds = EnumSet.allOf(Sound.class);
				int soundsLength = sounds.size();
				Player victim = Bukkit.getServer().getPlayer(status.getUsername());
				Random rand = new Random();
				int index = rand.nextInt(noisesLength + soundsLength + (status.isPotionable() ? 10 : 0));
				if (index < noisesLength) {
					Effect effect = noises[index];
					victim.playEffect(victim.getLocation(), effect, 0);
				} else if (index < noisesLength + soundsLength) {
					Sound[] dummy = {};
					Sound sound = sounds.toArray(dummy)[index - noisesLength];
					victim.playSound(victim.getLocation(), sound, 1, 1);
				} else {
					victim.addPotionEffect(potions[0]);
				}
			}
		}
	}

}
