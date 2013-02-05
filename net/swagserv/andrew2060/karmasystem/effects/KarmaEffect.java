package net.swagserv.andrew2060.karmasystem.effects;

import net.swagserv.andrew2060.karmasystem.util.KarmaType;

import com.herocraftonline.heroes.characters.effects.Effect;
import com.herocraftonline.heroes.characters.skill.Skill;

public class KarmaEffect extends Effect {
	long lastRefreshTime;
	int karmaEffectStacks;
	long expirationTimeMillis;
	/**
	 * Karma Effect - Contains a "stack" of the effect that can be attached to a player/later retrieved
	 * 
	 * @param skill
	 * @param creationTime The time in milliseconds when this effect was applied
	 * @param durationMillis The time before expirey - can be 0 for no expirey
	 * @param type The type of karma effect: can be anything as long as usage is consistent
	 */
	public KarmaEffect(Skill skill, long creationTime, long durationMillis, KarmaType type) {
		super(skill, type.node());
		this.expirationTimeMillis = durationMillis;
		this.lastRefreshTime = creationTime;
		this.karmaEffectStacks = 1;
	}
	/**
	 * Increments stack by 1 when function is called
	 */
	public void incrementStack() {
		//Normal Operation: Either there is no expiry time or expiry time is within
		if((expirationTimeMillis == 0) || (lastRefreshTime + expirationTimeMillis) <= System.currentTimeMillis()) {
			karmaEffectStacks++;
		}
		//The effect has expired since the last time we incremented the stack
		else {
			karmaEffectStacks = 1;
		}
	}
	/**
	 * Decrements stack by 1 when function is called
	 * 
	 * @param: expirationTimeMillis The amount of time before this effect expires: i.e. karma penalties are lost - takes 0 for no expiry
	 */
	public void decrementStack(long expirationTimeMillis) {
		//Normal Operation: Either there is no expiry time or expiry time is within
		if((expirationTimeMillis == 0) || (lastRefreshTime + expirationTimeMillis) <= System.currentTimeMillis()) {
			karmaEffectStacks--;
		}
		//The effect has expired since the last time we incremented the stack
		else {
			karmaEffectStacks = 0;
		}
	}
}
