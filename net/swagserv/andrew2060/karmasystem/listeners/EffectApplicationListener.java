package net.swagserv.andrew2060.karmasystem.listeners;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import net.swagserv.andrew2060.karmasystem.KarmaBasePlugin;
import net.swagserv.andrew2060.karmasystem.api.events.KarmaEffectLoadEvent;
import net.swagserv.andrew2060.karmasystem.effects.KarmaEffect;
import net.swagserv.andrew2060.karmasystem.util.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class EffectApplicationListener implements Listener {
	private Heroes heroes;
	private FileManager fileMan;
	/**
	 * Listener for all cases where karma effects may need to be loaded from file for updating
	 * @param heroes
	 */
	public EffectApplicationListener(Heroes heroes) {
		this.heroes = heroes;
		this.fileMan = ((KarmaBasePlugin)Bukkit.getPluginManager().getPlugin("KarmaBasePlugin")).getFileManager();
	}


	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		if(fileMan.hasFile(h)) {
			//Load the effects from file
			List<KarmaEffect> karmaeffect = fileMan.getEffects(h);
			KarmaEffectLoadEvent loadEvent = new KarmaEffectLoadEvent(h, karmaeffect);
			loadEvent.callEvent();
			//Not sure if I want to allow this event to be cancelled, but for now going to leave the option there.
			if(!loadEvent.isCancelled()) {
				Iterator<KarmaEffect> effectIterator = karmaeffect.iterator();
				while(effectIterator.hasNext()) {
					h.addEffect(effectIterator.next());
				}
			}
			return;
		} else {
			Bukkit.getLogger().log(Level.INFO, "Creating karma information for new hero " + h.getName());
		}
	}
}
