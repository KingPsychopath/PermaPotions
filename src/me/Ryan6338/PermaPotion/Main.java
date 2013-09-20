package me.Ryan6338.PermaPotion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		
		//Starts the task that checks a player's potion effects are correct
		resetPotionEffects.runTaskTimer(this, 0L, 20L);
	}
	
	public void playerJoined(Player p) {
		
		//Adds a player's potion effects when they join
		for (PotionEffect effectType : getPotionEffects(p))
			p.addPotionEffect(effectType, true);
	}
	
	//Used to get a list of the potioneffects for the specified player
	public List<PotionEffect> getPotionEffects (Player p) {
		List<PotionEffect> potionEffects = new ArrayList<PotionEffect>();
		
		for (PermissionAttachmentInfo i : p.getEffectivePermissions()) {
			String perm = i.getPermission();
			
			//Checks if it is a PermaPotion permission conserve usage
			if (perm.startsWith("permapotion.")) {
				String effectName;
				
				int str = getStrength(perm);
				int index1, index2;
				
				index1 = perm.indexOf('.');
				index2 = perm.lastIndexOf('.');
				
				/*Substrings permission to get the name of the effect
				 * ie. permapotion.speed.2 would substring to speed
				 * note: permissions are lowercase
				 */
				if (index1 == index2) {
					effectName = perm.substring(index1 + 1);
				} else {
					effectName = perm.substring(index1 + 1, index2);
				}
				
				switch (effectName) {
				case "absorption":
					potionEffects.add(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, str));
					break;
					
				case "blindness":
					potionEffects.add(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, str));
					break;
					
				case "confusion":
					potionEffects.add(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, str));
					break;
					
				case "damage_resistance":
					potionEffects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, str));
					break;
					
				case "fast_digging":
					potionEffects.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, str));
					break;
					
				case "fire_resistance":
					potionEffects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, str));
					break;
				
				case "harm":
					potionEffects.add(new PotionEffect(PotionEffectType.HARM, Integer.MAX_VALUE, str));
					break;
					
				case "heal":
					potionEffects.add(new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, str));
					break;
					
				case "health_boost":
					potionEffects.add(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, str));
					break;
					
				case "hunger":
					potionEffects.add(new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, str));
					break;
					
				case "increase_damage":
					potionEffects.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, str));
					break;
					
				case "invisibility":
					potionEffects.add(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, str));
					break;
					
				case "jump":
					potionEffects.add(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, str));
					break;
					
				case "night_vision":
					potionEffects.add(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, str));
					break;
					
				case "poison":
					potionEffects.add(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, str));
					break;
					
				case "regeneration":
					potionEffects.add(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, str));
					break;
					
				case "saturation":
					potionEffects.add(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, str));
					break;
					
				case "slow":
					potionEffects.add(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, str));
					break;
					
				case "slow_digging":
					potionEffects.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, str));
					break;
					
				case "speed":
					potionEffects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, str));
					break;
				
				case "water_breathing":
					potionEffects.add(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, str));
					break;
					
				case "weakness":
					potionEffects.add(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, str));
					break;
				
				case "wither":
					potionEffects.add(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, str));
					break;
				}
			}
		}
		
		return potionEffects;
	}
	
	//Gets the strength of the potion effect
	private int getStrength (String perm) {
		int str;
		
		//Uses try/catch to tell whether it is a number
		try {
			str = Integer.parseInt(perm.substring(perm.lastIndexOf('.') + 1)) - 1;
		} catch (Exception e) {
			str = 0;
		}
		if (str < 0 || str > 19) {
			str = 0;
		}
		return str;
	}
	
	public void playerHitByPotion(Player p, ThrownPotion pot) {
		for (PotionEffect effect : pot.getEffects()) {
			for (PotionEffect playerEffect : p.getActivePotionEffects()) {
				
				//Only adds effect if it is bigger than the effect given by permapotion
				if (playerEffect.getType().equals(effect.getType()) && effect.getAmplifier() > playerEffect.getAmplifier())
					p.removePotionEffect(effect.getType());
			}
			
		}
	}

	BukkitRunnable resetPotionEffects = new BukkitRunnable() {
		@Override
		public void run() {
			Player[] player = getServer().getOnlinePlayers();
			
			for (Player p : player) {
				List<PotionEffect> potionEffects = getPotionEffects(p);
				List<PotionEffectType> potionEffectTypes = new ArrayList<PotionEffectType>();
				
				/*Adds types of potion effects so they don't get removed.
				 * Not having this will result in only 1 effect
				 */
				for (PotionEffect effect : potionEffects)
					potionEffectTypes.add(effect.getType());
				
				for (PotionEffect effect : potionEffects) {
					
					for (PotionEffect playerEffect : p.getActivePotionEffects()) {
						
						/*Uses the effect types list to check whether the effect should be removed
						 * from the player. Only effects that aren't on the list can be removed
						 */
						
						if (!potionEffectTypes.contains(playerEffect.getType()))
							if (playerEffect.getDuration() > Integer.MAX_VALUE - 10000 ||
									playerEffect.getAmplifier() < effect.getAmplifier())
									p.removePotionEffect(playerEffect.getType());
					}
					
					p.addPotionEffect(effect);
				}
			}
		}
	};
	
}
