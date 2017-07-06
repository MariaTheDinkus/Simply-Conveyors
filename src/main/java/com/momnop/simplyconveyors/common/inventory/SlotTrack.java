package com.momnop.simplyconveyors.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;

public class SlotTrack extends Slot
{

	public SlotTrack(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStack.EMPTY && stack.getItem() instanceof IModifier) {
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
