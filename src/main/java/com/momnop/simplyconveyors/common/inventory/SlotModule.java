package com.momnop.simplyconveyors.common.inventory;

import mcjty.lib.compat.CompatSlot;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;

public class SlotModule extends CompatSlot
{

	public SlotModule(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStackTools.getEmptyStack() && stack.getItem() instanceof IModifier) {
			IModifier modifier = (IModifier) stack.getItem();
			for (int i = 0; i < inventory.getSizeInventory() - 1; i++) {
				if (inventory.getStackInSlot(i) != ItemStackTools.getEmptyStack()) {
					Item item = inventory.getStackInSlot(i).getItem();
					Class itemClass = stack.getItem().getClass();
					if (modifier.getType() == EnumModifierType.MODULE && item.getClass().equals(stack.getItem().getClass())) {
						return false;
					}
				}
			}
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
