package com.momnop.simplyconveyors.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ItemWorkerGloves extends ItemBasic {
	public ItemWorkerGloves(String unlocalizedName) {
		super(unlocalizedName, 1);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.BLUE + "+2 Block Reach Distance While in Offhand");
		tooltip.add(TextFormatting.DARK_GRAY + "Does not affect attack reach distance.");
		tooltip.add(TextFormatting.DARK_GRAY + "Does not render in hand.");
	}
}
