package net.swagserv.andrew2060.karmasystem.listeners;

import net.swagserv.andrew2060.karmasystem.Plugin;
import net.swagserv.andrew2060.karmasystem.effects.KarmaEffect;
import net.swagserv.andrew2060.karmasystem.util.KarmaType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class PVPListener implements Listener {
	private Heroes heroes;
	private Plugin plugin;
	public PVPListener(Heroes heroes, Plugin plugin) {
		this.heroes = heroes;
		this.plugin = plugin;
	}
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		Player k = p.getKiller();
		if(k == null) {
			//Not killed by a player, not our problem to deal with.
			return;
		}
		Hero h = heroes.getCharacterManager().getHero(k);
		if(!h.hasEffect(KarmaType.PVP.name())) {
			h.addEffect(new KarmaEffect(null, System.currentTimeMillis(), plugin.karmaDuration , KarmaType.PVP));
			return;
		} else {
			KarmaEffect pvpEffect = (KarmaEffect) h.getEffect(KarmaType.PVP.node());
			pvpEffect.incrementStack();
		}
	}
}
