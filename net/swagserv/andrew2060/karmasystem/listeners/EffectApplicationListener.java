package net.swagserv.andrew2060.karmasystem.listeners;

import net.swagserv.andrew2060.karmasystem.util.FileManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class EffectApplicationListener implements Listener {
	private Heroes heroes;
	/**
	 * Listener for all cases where karma effects may need to be loaded from file for updating
	 * @param heroes
	 */
	public EffectApplicationListener(Heroes heroes) {
		this.heroes = heroes;
	}


	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		if(FileManager.containsHero(h.getName())) {
			FileManager.createAndApplyEffects(h.getName());
		}
	}
}
