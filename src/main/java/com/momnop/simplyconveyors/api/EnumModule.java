package com.momnop.simplyconveyors.api;

import net.minecraft.util.IStringSerializable;

public enum EnumModule implements IStringSerializable
{
	NOTHING("none"),
	DROPPER("dropper"),
	HOLDING("holding"),
	SPIKEIRON("spikeiron"),
	SPIKEGOLD("spikegold"),
	SPIKEDIAMOND("spikediamond"),
	TRANSPORTER("transporter");
	
	private final String name;

    private EnumModule(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

    public String getName()
    {
        return this.name;
    }
}
