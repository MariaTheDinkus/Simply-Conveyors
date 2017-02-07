package com.momnop.simplyconveyors.items;

import net.minecraft.item.Item;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class ItemBasic extends Item
{
	public ItemBasic(String unlocalizedName, int maxStackSize)
	{
		super();
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(maxStackSize);
		SimplyConveyorsItems.register(this);
		RenderRegistry.registry.add(this);
	}
}