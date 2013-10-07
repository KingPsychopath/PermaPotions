package me.Ryan6338.PermaPotions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	private Main main;
	
	//Constructor to get instance of main
	public PlayerListener(Main instance) {
		main = instance;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		main.playerJoined(e.getPlayer());
	}
	
	@EventHandler
	public void onPotionSplash(PotionSplashEvent e) {
		for (Entity ent : e.getAffectedEntities()) {
			if (ent instanceof Player) {
				main.playerHitByPotion((Player) ent, e.getPotion());
			}
		}
	}
	
}
