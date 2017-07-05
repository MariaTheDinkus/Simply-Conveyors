package com.momnop.simplyconveyors.common.inventory;

import mcjty.lib.compat.CompatSlot;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;

public class SlotTrack extends CompatSlot
{

	public SlotTrack(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStackTools.getEmptyStack() && stack.getItem() instanceof IModifier) {
			IModifier modifier = (IModifier) stack.getItem();
			if (modifier.getType() == EnumModifierType.TRACK) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
	
}
