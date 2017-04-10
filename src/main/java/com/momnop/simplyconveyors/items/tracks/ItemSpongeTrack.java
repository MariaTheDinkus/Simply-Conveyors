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

public class ItemSpongeTrack extends ItemTrack
{

	public ItemSpongeTrack(String unlocalizedName, EnumTrack enumTrack)
	{
		super(unlocalizedName, enumTrack);
	}
	
	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.GRAY + "In the ocean you may find, sponge somewhere hard to find...");
		tooltip.add(TextFormatting.BLUE + "Removes fall damage for entities.");
		tooltip.add(TextFormatting.BLUE + "Can be placed in modular conveyors.");
	}
	
}
