package net.swagserv.andrew2060.karmasystem.effects;

import net.swagserv.andrew2060.karmasystem.util.KarmaType;

import com.herocraftonline.heroes.characters.effects.Effect;
import com.herocraftonline.heroes.characters.skill.Skill;

public class KarmaEffect extends Effect {
	public long lastRefreshTime;
	public int karmaEffectStacks;
	public long expirationTimeMillis;
	/**
	 * Karma Effect - Contains a "stack" of the effect that can be attached to a player/later retrieved
	 * 
	 * @param skill
	 * @param lastRefreshTIme The time in milliseconds when this effect was applied
	 * @param durationMillis The time before expirey - can be 0 for no expirey
	 * @param type The type of karma effect: can be anything as long as usage is consistent
	 */
	public KarmaEffect(Skill skill, long lastRefreshTime, long durationMillis, KarmaType type) {
		this(skill, lastRefreshTime,durationMillis,type.node());
	}
	/**
	 * Karma Effect - Contains a "stack" of the effect that can be attached to a player/later retrieved - alternative constructor for cases outside of default enums
	 * 
	 * @param skill
	 * @param lastRefreshTIme The time in milliseconds when this effect was applied
	 * @param durationMillis The time before expirey - can be 0 for no expirey
	 * @param typeName Name of the effect specifically for instances outside of those defined in 
	 */
	public KarmaEffect(Skill skill, long lastRefreshTime, long durationMillis, String typeName) {
		this(skill,lastRefreshTime,durationMillis,typeName,0);
	}
	/**
	 * Karma Effect - Contains a "stack" of the effect that can be attached to a player/later retrieved - alternative constructor for an ability to specify an alternative default value
	 * 
	 * @param skill
	 * @param lastRefreshTIme The time in milliseconds when this effect was applied
	 * @param durationMillis The time before expirey - can be 0 for no expirey
	 * @param typeName Name of the effect specifically outs
	 * @param karmaEffectStacks
	 */
	public KarmaEffect(Skill skill, long lastRefreshTime, long durationMillis, String typeName, int karmaEffectStacks) {
		super(skill, typeName);
		this.expirationTimeMillis = durationMillis;
		this.lastRefreshTime = lastRefreshTime;
		this.karmaEffectStacks = karmaEffectStacks;
	}

	/**
	 * Modifies stack by i when function is called
	 * 
	 * @param: expirationTimeMillis The amount of time before this effect expires: i.e. karma penalties are lost - takes 0 for no expiry
	 * @param: amount The amount to increment the stack by
	 */
	public void modifyStack(int amount) {
		//Normal Operation: Either there is no expiry time or expiry time is within
		if((expirationTimeMillis == 0) || (lastRefreshTime + expirationTimeMillis) <= System.currentTimeMillis()) {
			karmaEffectStacks += amount;
			lastRefreshTime = System.currentTimeMillis();
		}
		//The effect has expired since the last time we incremented the stack
		else {
			karmaEffectStacks = 1;
		}
	}
	/**
	 * Sets stack to an explicit amount
	 * 
	 * @param amount Amount to set the stack to
	 */
	public void setStacks(int amount) {
		karmaEffectStacks = amount;
	}
}
