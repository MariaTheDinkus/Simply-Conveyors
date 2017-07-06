package com.momnop.simplyconveyors.common.items.modules;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;
import com.momnop.simplyconveyors.api.interfaces.IUpdatePassive;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.info.ModInfo;
import com.momnop.simplyconveyors.common.items.ItemBasic;

public class ItemTransporterModule extends ItemBasic implements IModifier, IUpdatePassive
{

	public ItemTransporterModule(String unlocalizedName)
	{
		super(unlocalizedName, 64);
	}

	@Override
	public String getDescription() {
		return "Inserts items in front of it, and pulls items from behind it.";
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
		if(!entityIn.getEntityWorld().isRemote && entityIn instanceof EntityItem && !powered)
		{
			EntityItem item = (EntityItem) entityIn;

			ConveyorHelper.insertToFacing(facing, EnumFacing.NORTH, tile.getPos(), entityIn);
		}
	}

	@Override
	public void updatePassive(TileEntity tile, boolean powered,
			EnumFacing facing, EnumFacing conveyorType) {
		if(!tile.getWorld().isRemote && !powered && tile.getWorld().getTotalWorldTime() % 5 == 0)
		{
			TileEntity tile2 = tile.getWorld().getTileEntity(tile.getPos().add(facing.getOpposite().getDirectionVec()));
			if(tile2 != null && tile2 instanceof IInventory)
			{
				IInventory inventory = (IInventory) tile2;
				boolean empty = true;
				for (int i = 0; i < inventory.getSizeInventory(); i++) {
					if (inventory.getStackInSlot(i) != ItemStackTools.getEmptyStack()) {
						empty = false;
						break;
					}
				}
				if(!empty)
				{
					for(int i = 0; i < inventory.getSizeInventory(); i++)
					{
						if(inventory.getStackInSlot(i) != ItemStackTools.getEmptyStack())
						{
							EntityItem entityItem = new EntityItem(tile.getWorld(), tile.getPos().getX() + 0.5F, tile.getPos().getY() + (1F / 16F), tile.getPos().getZ() + 0.5F, new ItemStack(
									inventory.getStackInSlot(i).getItem(), 1, inventory.getStackInSlot(i).getMetadata()));
							if(entityItem.getEntityItem() != ItemStackTools.getEmptyStack())
							{
								tile.getWorld().spawnEntity(entityItem);
							}
							entityItem.motionX = 0;
							entityItem.motionY = 0;
							entityItem.motionZ = 0;
							entityItem.setPosition(tile.getPos().getX() + 0.5F, tile.getPos().getY() + (1F / 16F), tile.getPos().getZ() + 0.5F);
							inventory.decrStackSize(i, 1);
							break;
						}
					}
				}
			}
		}
	}

}
