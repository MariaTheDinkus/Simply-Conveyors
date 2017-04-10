package com.momnop.simplyconveyors.items.tracks;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

import com.momnop.simplyconveyors.api.EnumTrack;
import com.momnop.simplyconveyors.api.ItemTrack;

public class ItemWebbedTrack extends ItemTrack
{

	public ItemWebbedTrack(String unlocalizedName, EnumTrack enumTrack)
	{
		super(unlocalizedName, enumTrack);
	}
	
	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		if (!powered) {
			entityIn.motionX *= 0.33;
			entityIn.motionZ *= 0.33;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.GRAY + "One person's abandoned mine is another man's treasure.");
		tooltip.add(TextFormatting.BLUE + "Slows conveyor down to roughly 1/3 speed.");
		tooltip.add(TextFormatting.BLUE + "Can be placed in modular conveyors.");
	}
	
}
