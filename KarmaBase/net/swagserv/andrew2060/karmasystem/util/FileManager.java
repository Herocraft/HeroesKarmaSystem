package net.swagserv.andrew2060.karmasystem.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import net.swagserv.andrew2060.karmasystem.KarmaBasePlugin;
import net.swagserv.andrew2060.karmasystem.api.events.KarmaEffectSaveEvent;
import net.swagserv.andrew2060.karmasystem.effects.KarmaEffect;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.effects.Effect;


public class FileManager {
	private KarmaBasePlugin plugin;	
	public FileManager(KarmaBasePlugin plugin) {
		this.plugin = plugin;
	}
	/**
	 * Checks if a given hero has a savefile associated with him
	 * 
	 * @param hero
	 * @return
	 */
	public boolean hasFile(Hero hero) {
		File storageFile = new File(constructDataFolder(hero), hero.getPlayer().getName().toUpperCase() + ".prop");
		if(storageFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
	private File constructDataFolder(Hero h) {
		char firstLetter = (h.getName().toUpperCase().toCharArray())[0];
		File constructed = new File(plugin.PLUGINDATAFOLDER + File.separator + "Data" + File.separator + firstLetter);
		return constructed;
	}
	/**
	 * Gets the list of karma effects associated with a character from file
	 * @param hero
	 * @return
	 */
	public List<KarmaEffect> getEffects(Hero hero) {
		File dataFolder = constructDataFolder(hero);
		if(!dataFolder.exists()) {
			dataFolder.mkdirs();
		}
		File storageFile = new File(dataFolder, hero.getPlayer().getName().toUpperCase() + ".prop");
		try {
			FileInputStream fstream = new FileInputStream(storageFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			List<KarmaEffect> effectList = new ArrayList<KarmaEffect>();
			String inputStream;
			while ((inputStream = br.readLine()) != null) {
				/*
				 * Storage Format
				 * 
				 * Inside HERONAME.prop
				 * EffectName1:CurrentKarmaValue:lastRefreshTime:expirationDuration:
				 */
				String[] effectData =  inputStream.split(":");
				KarmaEffect toAdd = new KarmaEffect(null,Long.parseLong(effectData[2]),Long.parseLong(effectData[3]),effectData[0],Integer.parseInt(effectData[1]));
				effectList.add(toAdd);
			}
			br.close();
			in.close();
			fstream.close();
			return effectList;
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Some naughty plugin just tried to load the karma files without checking for their existance first ;)");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Saves karma effects to file - must be prefixed by "Karma-" in the effect name
	 * @param h	Hero to save
	 */
	public void saveEffects(Hero h) {
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
			toSave = saveEvent.getEffects();
			return;
		}
		Iterator<KarmaEffect> saveIterate = toSave.iterator();
		File storageFile = new File(constructDataFolder(h), h.getPlayer().getName().toUpperCase() + ".prop");
		if(storageFile.exists()) {
			storageFile.delete();
		}
		try {
			storageFile.createNewFile();
			while(saveIterate.hasNext()) {
				KarmaEffect effect = saveIterate.next();
				FileWriter fileWriter = new FileWriter(storageFile,true);
				String writeFile;
				/*
				 * Storage Format
				 * 
				 * Inside HERONAME.prop
				 * EffectName1:CurrentKarmaValue:lastRefreshTime:expirationTime
				 */
				writeFile = effect.getName() 
						+ ":" + effect.karmaEffectStacks 
						+ ":" + effect.lastRefreshTime
						+ ":" + effect.expirationTimeMillis;
				fileWriter.write(writeFile);
				fileWriter.close();
			}
		} catch (IOException e) {
			System.out.println("Error Writing Effects to File");
			e.printStackTrace();
		}

	}

}
