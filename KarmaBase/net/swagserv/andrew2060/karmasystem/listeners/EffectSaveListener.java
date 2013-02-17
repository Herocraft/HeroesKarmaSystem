package net.swagserv.andrew2060.karmasystem.listeners;

import net.swagserv.andrew2060.karmasystem.KarmaBasePlugin;
import net.swagserv.andrew2060.karmasystem.util.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

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
		fileMan.saveEffects(h);
	}
}
