package com.momnop.simplyconveyors.items.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

import com.momnop.simplyconveyors.api.EnumModule;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class ItemDropperModule extends ItemModule
{

	public ItemDropperModule(String unlocalizedName, EnumModule enumModule)
	{
		super(unlocalizedName, enumModule);
	}
	
	@Override
	public boolean isCompatible(ItemModule upgrade)
	{
		incompatibles.clear();
		if (!incompatibles.contains(TextFormatting.BLUE + "Transporter Module")) {
			incompatibles.add(TextFormatting.BLUE + "Transporter Module");
		}
		
		if (upgrade instanceof ItemTransporterModule) {
			return false;
		}
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
