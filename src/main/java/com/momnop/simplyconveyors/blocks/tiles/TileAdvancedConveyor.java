package com.momnop.simplyconveyors.blocks.tiles;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileAdvancedConveyor extends TileEntity
{
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	IItemHandler insertionHandler = new ConveyorInventoryHandler(this);

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) insertionHandler;
		return super.getCapability(capability, facing);
	}

	public static class ConveyorInventoryHandler implements IItemHandlerModifiable
	{
		TileAdvancedConveyor conveyor;

		public ConveyorInventoryHandler(TileAdvancedConveyor conveyor)
		{
			this.conveyor = conveyor;
		}

		@Override
		public int getSlots()
		{
			return 1;
		}

		@Override
		public int getSlotLimit(int slot)
		{
			return 64;
		}

		@Override
		public ItemStack getStackInSlot(int slot)
		{
			List<EntityItem> list = conveyor.getWorld().getEntitiesWithinAABB(
					EntityItem.class,
					new AxisAlignedBB(conveyor.getPos().getX(), conveyor.getPos().getY(), conveyor.getPos().getZ(), conveyor.getPos().getX() + 1, conveyor.getPos().getY() + 1, conveyor.getPos()
							.getZ() + 1));
			if(!list.isEmpty())
			{
				for(EntityItem item : list)
				{
					if(item.getEntityItem() != ItemStack.EMPTY)
					{
						return item.getEntityItem();
					}
				}
			}
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			if(!simulate)
			{
				EntityItem entity = new EntityItem(conveyor.getWorld(), conveyor.getPos().getX() + .5, conveyor.getPos().getY() + .1875, conveyor.getPos().getZ() + .5, stack.copy());
				entity.motionX = 0;
				entity.motionY = 0;
				entity.motionZ = 0;
				conveyor.getWorld().spawnEntity(entity);
			}
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			List<EntityItem> list = conveyor.getWorld().getEntitiesWithinAABB(
					EntityItem.class,
					new AxisAlignedBB(conveyor.getPos().getX(), conveyor.getPos().getY(), conveyor.getPos().getZ(), conveyor.getPos().getX() + 1, conveyor.getPos().getY() + 1, conveyor.getPos()
							.getZ() + 1));
			if(!list.isEmpty() && !conveyor.getWorld().isRemote)
			{
				for(EntityItem item : list)
				{
					if(item.getEntityItem() != ItemStack.EMPTY)
					{
						ItemStack stack = item.getEntityItem().copy();
						stack.setCount(amount);
						if (!simulate && item.getEntityItem().getCount() != 1) {
							item.getEntityItem().setCount(item.getEntityItem().getCount() - amount);
						} else if (!simulate) {
							item.setDead();
						}
						return stack;
					}
				}
			}
			return ItemStack.EMPTY;
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack)
		{
		}
	}
}
