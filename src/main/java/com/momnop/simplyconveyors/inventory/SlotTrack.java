package com.momnop.simplyconveyors.inventory;

import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.api.ItemTrack;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTrack extends Slot
{

	public SlotTrack(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemTrack) {
			return true;
		}
		return false;
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
	
}
