package com.momnop.simplyconveyors.items;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;

import mcjty.lib.compat.CompatItem;

public class ItemWrench extends CompatItem
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