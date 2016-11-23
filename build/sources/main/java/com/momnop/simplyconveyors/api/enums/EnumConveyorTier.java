package com.momnop.simplyconveyors.api.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumConveyorTier implements IStringSerializable {
	IRON("iron"),
	GOLD("gold"),
	DIAMOND("diamond"),
	QUARTZ("quartz");
	
	private final String name;
	
	private EnumConveyorTier(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
