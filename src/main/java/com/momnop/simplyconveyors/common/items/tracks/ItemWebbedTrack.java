package com.momnop.simplyconveyors.common.items.tracks;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;
import com.momnop.simplyconveyors.common.info.ModInfo;
import com.momnop.simplyconveyors.common.items.ItemBasic;

public class ItemWebbedTrack extends ItemBasic implements IModifier
{

	public ItemWebbedTrack(String unlocalizedName)
	{
		super(unlocalizedName, 64);
	}
	
	@Override
	public String getDescription() {
		return "Slows conveyor down to roughly 1/3 speed.";
	}
	
	@Override
	public EnumModifierType getType() {
		return EnumModifierType.TRACK;
	}
	
	@Override
	public boolean isConductive() {
		return false;
	}
	
	@Override
	public String getModID() {
		return ModInfo.MOD_ID;
	}
	
	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		entityIn.motionX *= 0.33;
		entityIn.motionZ *= 0.33;
	}
	
}
