package net.swagserv.andrew2060.karmasystem;

import java.util.logging.Level;

import net.swagserv.andrew2060.karmasystem.listeners.EffectApplicationListener;
import net.swagserv.andrew2060.karmasystem.listeners.PVPListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import com.herocraftonline.heroes.Heroes;

public class Plugin extends JavaPlugin {
	Heroes heroes;
	LWC lwc;
	public long karmaDuration = 0;
	//TODO: temporarily permanent karma effects until proper implementation is done
	
	@Override
	public void onEnable() {
		if(!initiateIntegration()) {
			Bukkit.getLogger().log(Level.SEVERE, "You tried to use this plugin while missing one of its dependencies");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		Bukkit.getPluginManager().registerEvents(new EffectApplicationListener(heroes), this);
		Bukkit.getPluginManager().registerEvents(new PVPListener(heroes,this), this);
	}
	
	public boolean initiateIntegration() {
		heroes = (Heroes)Bukkit.getServer().getPluginManager().getPlugin("Heroes");
		if(heroes == null) {
			return false;
		}
		PluginManager pm = Bukkit.getServer().getPluginManager();
		LWCPlugin plugin=(LWCPlugin)pm.getPlugin("LWC");
		if ((null == plugin)||( ! pm.isPluginEnabled("LWC"))) {
			return false;
		}else{
			lwc = LWC.getInstance();
		}
		return false;
	}

}
