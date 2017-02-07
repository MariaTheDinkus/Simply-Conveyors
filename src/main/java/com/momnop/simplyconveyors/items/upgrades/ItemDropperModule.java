package com.momnop.simplyconveyors.items.upgrades;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class ItemDropperModule extends ItemModule
{

	public ItemDropperModule(String unlocalizedName)
	{
		super(unlocalizedName);
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
		if (entityIn instanceof EntityItem && !powered) {
			EntityItem item = (EntityItem) entityIn;
			
			ConveyorHelper.insert(facing, conveyorType, tile.getPos(), entityIn);
		}
	}
	
}
