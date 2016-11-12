package com.momnop.simplyconveyors.items;

import net.minecraft.item.Item;

public class ItemWrench extends Item
{
    public ItemWrench(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        func_77655_b(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        func_77625_d(1);
    }
}
