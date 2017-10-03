package com.zundrel.simplyconveyors.api.enums;

public enum EnumModifierType {
	MODULE,
	TRACK;
	
	public static EnumModifierType getType(int i) {
		if (i == 0) {
			return MODULE;
		} else if (i == 1) {
			return TRACK;
		}
		return null;
	}
	
	public static int getId(EnumModifierType modifier) {
		if (modifier == MODULE) {
			return 0;
		} else if (modifier == TRACK) {
			return 1;
		}
		return -1;
	}
}
