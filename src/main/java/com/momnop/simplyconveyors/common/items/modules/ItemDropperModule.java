package com.momnop.simplyconveyors.common.items.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.items.ItemBasic;

public class ItemDropperModule extends ItemBasic implements IModifier
{
	public ItemDropperModule(String unlocalizedName)
	{
		super(unlocalizedName, 64);
	}
	
	@Override
	public String getDescription() {
		return "Drops items into inventories below (above if inverse) it.";
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
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		if (!entityIn.getEntityWorld().isRemote && entityIn instanceof EntityItem && !powered) {
			EntityItem item = (EntityItem) entityIn;
			
			ConveyorHelper.insert(facing, conveyorType, tile.getPos(), entityIn);
		}
	}
}
