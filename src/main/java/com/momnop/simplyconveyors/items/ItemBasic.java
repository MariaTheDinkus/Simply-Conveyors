package com.momnop.simplyconveyors.items;

import mcjty.lib.compat.CompatItem;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class ItemBasic extends CompatItem
{
	public ItemBasic(String unlocalizedName, int maxStackSize)
	{
		super();
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyors.tabGeneral);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(maxStackSize);
		RenderRegistry.registry.add(this);
	}
}