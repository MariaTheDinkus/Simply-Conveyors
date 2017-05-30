package com.momnop.simplyconveyors.inventory;

import mcjty.lib.compat.CompatSlot;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.momnop.simplyconveyors.api.ItemTrack;

public class SlotTrack extends CompatSlot
{

	public SlotTrack(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStackTools.getEmptyStack() && stack.getItem() instanceof ItemTrack) {
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
