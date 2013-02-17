package net.swagserv.andrew2060.karmasystem;

import java.io.File;
import java.util.logging.Level;

import net.swagserv.andrew2060.karmasystem.listeners.EffectApplicationListener;
import net.swagserv.andrew2060.karmasystem.util.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.heroes.Heroes;

public class KarmaBasePlugin extends JavaPlugin {
	FileManager fileMan;
	Heroes heroes;
	public File PLUGINBASEFOLDER = new File("plugins" + File.separator + "KarmaPlugin");
	public File PLUGINDATAFOLDER = new File(PLUGINBASEFOLDER + File.separator + "Data");
	public long karmaDuration;
	//TODO: temporarily permanent karma effects until proper implementation is done
	
	@Override
	public void onEnable() {
		if(!initiateIntegration()) {
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		fileMan = new FileManager(this);
		Bukkit.getPluginManager().registerEvents(new EffectApplicationListener(heroes), this);
		karmaDuration = getConfig().getLong("DefaultKarmaDuration");
	}
	/**
	 * Gets the file manager associated with the i/o operations of this plugin
	 * @return FileManager
	 */
	public FileManager getFileManager() {
		return fileMan;
	}
	public boolean initiateIntegration() {
		heroes = (Heroes)Bukkit.getServer().getPluginManager().getPlugin("Heroes");
		if(heroes == null) {
			Bukkit.getLogger().log(Level.SEVERE, "Dependency not found: Heroes");
			return false;
		}
		return true;
	}

}
