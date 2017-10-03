package com.zundrel.simplyconveyors.common.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWorkerGloves extends ItemBasic {
	public ItemWorkerGloves(String unlocalizedName) {
		super(unlocalizedName, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world,
			List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.BLUE + "+2 Block Reach Distance While in Offhand");
		tooltip.add(TextFormatting.DARK_GRAY + "Does not affect attack reach distance.");
		tooltip.add(TextFormatting.DARK_GRAY + "Does not render in hand.");
	}
}
