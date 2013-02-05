package net.swagserv.andrew2060.karmasystem.util;

public enum KarmaType {
	PVP("KarmaPvPEffect"), 
	PVE("KarmaPvEEffect"), 
	LWC("KarmaLWCEffect");
	
	private final String effectName;
	KarmaType(String effectName) {
		this.effectName = effectName;
	}
	public String node() {
		return effectName;
	}
}

