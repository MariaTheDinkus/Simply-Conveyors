package com.momnop.simplyconveyors.api;

import net.minecraft.util.IStringSerializable;

public enum EnumTrack implements IStringSerializable
{
	NOTHING("none"),
	SPONGE("sponge"),
	WEBBED("webbed");
	
	private final String name;

    private EnumTrack(String name)
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