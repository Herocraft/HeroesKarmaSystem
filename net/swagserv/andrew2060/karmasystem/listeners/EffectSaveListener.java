package net.swagserv.andrew2060.karmasystem.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.swagserv.andrew2060.karmasystem.KarmaBasePlugin;
import net.swagserv.andrew2060.karmasystem.api.events.KarmaEffectSaveEvent;
import net.swagserv.andrew2060.karmasystem.effects.KarmaEffect;
import net.swagserv.andrew2060.karmasystem.util.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.effects.Effect;

public class EffectSaveListener implements Listener {
	private Heroes heroes;
	private FileManager fileMan;
	/**
	 * Listener for all cases where karma effects may need to be saved to file
	 * @param heroes
	 */
	public EffectSaveListener(Heroes heroes) {
		this.heroes = heroes;
		this.fileMan = ((KarmaBasePlugin)Bukkit.getPluginManager().getPlugin("KarmaBasePlugin")).getFileManager();
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLeaveQuit(PlayerQuitEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		Iterator<Effect> effectListIterator = h.getEffects().iterator();
		List<KarmaEffect> toSave = new ArrayList<KarmaEffect>();
		while(effectListIterator.hasNext()) {
			Effect effect = effectListIterator.next();
			if(!effect.getName().contains("Karma-")) {
				continue;
			} else {
				toSave.add((KarmaEffect)effect);
			}
		}
		KarmaEffectSaveEvent saveEvent = new KarmaEffectSaveEvent(toSave,h);
		saveEvent.callEvent();
		if(!saveEvent.isCancelled()) {
			fileMan.saveEffects(saveEvent.getEffects(),h);
		}
	}
}
