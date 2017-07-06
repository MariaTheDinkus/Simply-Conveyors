package com.momnop.simplyconveyors.common.items.modules;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.info.ModInfo;
import com.momnop.simplyconveyors.common.items.ItemBasic;

public class ItemHoldingModule extends ItemBasic implements IModifier
{

	public ItemHoldingModule(String unlocalizedName)
	{
		super(unlocalizedName, 64);
	}
	
	@Override
	public String getDescription() {
		return "Holds entities in place while powered.";
	}
	
	@Override
	public EnumModifierType getType() {
		return EnumModifierType.MODULE;
	}
	
	@Override
	public boolean isConductive() {
		return true;
	}
	
	@Override
	public String getModID() {
		return ModInfo.MOD_ID;
	}
	
	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		if (powered && ConfigHandler.stopWhileSneaking && !entityIn.isSneaking() || powered && !ConfigHandler.stopWhileSneaking) {
			entityIn.motionX = 0;
			entityIn.motionZ = 0;
			
			ConveyorHelper.centerEntity(entityIn, tile.getPos());
		}
	}
	
}
