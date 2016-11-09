package com.momnop.simplyconveyors.items;

import net.minecraft.item.Item;

public class ItemWrench extends Item
{
    public ItemWrench(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        setMaxStackSize(1);
    }
}