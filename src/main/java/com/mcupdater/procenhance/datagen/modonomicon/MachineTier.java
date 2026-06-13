package com.mcupdater.procenhance.datagen.modonomicon;

public enum MachineTier {
	BASIC("t1","Basic"),
	INTERMEDIATE("t2","Intermediate"),
	ADVANCED("t3","Advanced"),
	INDUSTRIAL("t4","Industrial");

	private final String prefix;
	private final String tier;

	private MachineTier(String tier, String prefix) {
		this.tier = tier;
		this.prefix = prefix;

	}

	public String getTier() {
		return this.tier;
	}

	public String getPrefix() {
		return this.prefix;
	}
};

