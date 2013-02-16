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
import net.swagserv.andrew2060.karmasystem.effects.KarmaEffect;

import com.herocraftonline.heroes.characters.Hero;


public class FileManager {
	private KarmaBasePlugin plugin;	
	public FileManager(KarmaBasePlugin plugin) {
		this.plugin = plugin;
	}

	public boolean hasFile(Hero h) {
		File storageFile = new File(constructDataFolder(h), h.getPlayer().getName().toUpperCase() + ".prop");
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

	public List<KarmaEffect> getEffects(Hero h) {
		File storageFile = new File(constructDataFolder(h), h.getPlayer().getName().toUpperCase() + ".prop");
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

	public void saveEffects(List<KarmaEffect> toSave, Hero h) {
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
