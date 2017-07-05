package com.momnop.simplyconveyors.common.items;

import mcjty.lib.compat.CompatItem;
import net.minecraft.item.Item;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.common.info.ModInfo;

public class ItemBasic extends CompatItem
{
	public ItemBasic(String unlocalizedName, int maxStackSize)
	{
		super();
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyors.tabGeneral);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(maxStackSize);
		SimplyConveyorsItems.register(this);
	}
	
	public void registerItemModel(Item i) {
		SimplyConveyors.proxy.registerItemRenderer(i, 0, this.getUnlocalizedName().substring(5));
	}
}