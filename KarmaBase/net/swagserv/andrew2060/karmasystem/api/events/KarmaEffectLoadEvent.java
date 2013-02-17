package net.swagserv.andrew2060.karmasystem.api.events;

import java.util.List;

import net.swagserv.andrew2060.karmasystem.effects.KarmaEffect;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.herocraftonline.heroes.characters.Hero;

public class KarmaEffectLoadEvent extends Event {
	
	private List<KarmaEffect> effects;
	private Hero h;
	private boolean cancelled;
	/**
	 * This event is thrown whenever the Karma effects are loaded from file. This event can be cancelled. More functionality
	 * will be added in the future.
	 * 
	 * @param hero The hero being loaded
	 * @param effects The effects being loaded
	 */
	public KarmaEffectLoadEvent(Hero h, List<KarmaEffect> effects) {
		this.effects = effects;
		this.h = h;
		this.cancelled = false;
	}
	private static final HandlerList handlers = new HandlerList();
	/**
	 * Calls the event (included mainly for simplicity purposes)
	 */
	public void callEvent(){
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
	/**
	 * Gets a list of the effects being loaded. This list can then be manipulated
	 * (i.e. adjusting the Karma value on load)
	 * 
	 * @return List<KarmaEffect> List of effects loaded from file (supposedly all karma effects unless someone else hooked into the API to add their own effects)
	 */
	public List<KarmaEffect> getEffects() {
		return effects;
	}
	/**
	 * Gets the hero associated with this event
	 * @return Hero The hero associated with this event
	 */
	public Hero getHero() {
		return h;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	

}
