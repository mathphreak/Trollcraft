package com.github.mathphreak.trollcraft;

import org.bukkit.Bukkit;

public class TrollingStatus {
	private String username;
	private int duration;
	private boolean potionable;
	private long lastUpdated = 0;
	private boolean invalid = false;
	public TrollingStatus(String username, int duration, boolean potionable) {
		super();
		this.username = username;
		this.duration = duration;
		this.potionable = potionable;
	}
	public String getUsername() {
		return username;
	}
	public int getDuration() {
		return duration;
	}
	public boolean isPotionable() {
		return potionable;
	}
	public void update() {
		duration -= 1;
		lastUpdated = Bukkit.getPlayer(username).getWorld().getFullTime();
	}
	public void invalidate() {
		this.invalid = true;
	}
	public boolean shouldUpdate() {
		return !this.invalid && Bukkit.getPlayer(username).getWorld().getFullTime() - lastUpdated > duration;
	}
	public boolean isInvalid() {
		return invalid;
	}
}
