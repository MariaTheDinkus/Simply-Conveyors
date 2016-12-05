package com.momnop.simplyconveyors.items;

import net.minecraft.item.Item;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;

public class ItemWrench extends Item
{
    public ItemWrench(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        setMaxStackSize(1);
    }
}