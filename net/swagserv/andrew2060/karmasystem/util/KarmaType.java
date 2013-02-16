package net.swagserv.andrew2060.karmasystem.util;

public enum KarmaType {
	PVP("PvPEffect"), 
	PVE("PvEEffect"), 
	LWC("LWCEffect");
	
	private final String effectName;
	KarmaType(String effectName) {
		this.effectName = effectName;
	}
	public String node() {
		return ("Karma-" +effectName);
	}
	public String customType(String arg) {
		return ("Karma-"+arg);
	}
}

