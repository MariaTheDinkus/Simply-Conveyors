package com.zundrel.simplyconveyors.common.items;

import net.minecraft.item.Item;

import com.zundrel.simplyconveyors.SimplyConveyors;
import com.zundrel.simplyconveyors.common.info.ModInfo;

public class ItemBasic extends Item
{
	public ItemBasic(String unlocalizedName, int maxStackSize)
	{
		super();
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyors.tabGeneral);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(maxStackSize);
	}
	
	public void registerItemModel(Item i) {
		SimplyConveyors.proxy.registerItemRenderer(i, 0, this.getUnlocalizedName().substring(5));
	}
}