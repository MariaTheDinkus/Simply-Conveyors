package com.zundrel.simplyconveyors.common.items.tracks;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import com.zundrel.simplyconveyors.api.enums.EnumModifierType;
import com.zundrel.simplyconveyors.api.interfaces.IModifier;
import com.zundrel.simplyconveyors.common.info.ModInfo;
import com.zundrel.simplyconveyors.common.items.ItemBasic;

public class ItemSpongeTrack extends ItemBasic implements IModifier
{

	public ItemSpongeTrack(String unlocalizedName)
	{
		super(unlocalizedName, 64);
	}
	
	@Override
	public String getDescription() {
		return "Blocks all fall damage for living entities.";
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
		
	}
	
}
