package com.momnop.simplyconveyors.items;

import net.minecraft.item.Item;

public class ItemFluidOrb extends Item
{
    public ItemFluidOrb(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        setMaxStackSize(1);
    }
}