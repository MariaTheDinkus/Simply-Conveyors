package com.momnop.simplyconveyors.items.modules;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import com.momnop.simplyconveyors.api.EnumModule;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class ItemHoldingModule extends ItemModule
{

	public ItemHoldingModule(String unlocalizedName, EnumModule enumModule)
	{
		super(unlocalizedName, enumModule);
	}
	
	@Override
	public boolean isCompatible(ItemModule upgrade)
	{
		incompatibles.clear();
		if (!incompatibles.contains("Nothing")) {
			incompatibles.add("Nothing");
		}
		return true;
	}
	
	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		if (powered && !entityIn.isSneaking()) {
			entityIn.motionX = 0;
			entityIn.motionZ = 0;
			
			ConveyorHelper.centerEntity(entityIn, tile.getPos());
		}
	}
	
}
