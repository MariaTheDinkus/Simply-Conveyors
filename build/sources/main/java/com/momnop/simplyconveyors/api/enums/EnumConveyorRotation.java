package com.momnop.simplyconveyors.api.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumConveyorRotation implements IStringSerializable {
	NORMAL("normal"),
	INVERSE("inverse"),
	VERTICAL("vertical");
	
	private final String name;

	private EnumConveyorRotation(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return null;
	}
}
