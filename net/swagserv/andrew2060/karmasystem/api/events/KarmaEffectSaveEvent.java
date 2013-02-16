package net.swagserv.andrew2060.karmasystem.api.events;

import java.util.List;

import net.swagserv.andrew2060.karmasystem.effects.KarmaEffect;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.herocraftonline.heroes.characters.Hero;

public class KarmaEffectSaveEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	List<KarmaEffect> toSave;
	Hero h;
	boolean cancelled = false;
	/**
	 * Event called when karma effects are saved to file
	 * 
	 * @param effectsToSave A list of karma effects that are being saved
	 * @param savingHero The hero associated with these effects
	 */
	public KarmaEffectSaveEvent(List<KarmaEffect> effectsToSave, Hero savingHero) {
		this.toSave = effectsToSave;
		this.h = savingHero;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	/**
	 * Gets the list of karma effects that are being saved
	 * 
	 * @return		List of KarmaEffects
	 */
	public List<KarmaEffect> getEffects() {
		return toSave;
	}
	public Hero getHero() {
		return h;
	}
	/**
	 * @return 		The cancellation state of this event
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	/**
	 * Set the event cancellation state
	 * 
	 * @param cancel
	 */
	public void setCancelled(Boolean cancel) {
		cancelled = cancel;
		return;
	}
	/**
	 * Calls the event via Bukkit plugin manager
	 */
	public void callEvent() {
		Bukkit.getServer().getPluginManager().callEvent(this);
	}

}
