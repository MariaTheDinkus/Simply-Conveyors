package com.zundrel.simplyconveyors.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.zundrel.simplyconveyors.api.enums.EnumModifierType;
import com.zundrel.simplyconveyors.api.interfaces.IModifier;

public class SlotModule extends Slot
{

	public SlotModule(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStack.EMPTY && stack.getItem() instanceof IModifier) {
			IModifier modifier = (IModifier) stack.getItem();
			for (int i = 0; i < inventory.getSizeInventory() - 1; i++) {
				if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
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
