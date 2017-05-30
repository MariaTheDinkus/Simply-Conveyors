package com.momnop.simplyconveyors.inventory;

import mcjty.lib.compat.CompatSlot;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.momnop.simplyconveyors.api.ItemModule;

public class SlotModule extends CompatSlot
{

	public SlotModule(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStackTools.getEmptyStack() && stack.getItem() instanceof ItemModule) {
			ItemModule upgrade = (ItemModule) stack.getItem();
			
			for (int i = 0; i < 3; i++) {
				if (this.inventory.getStackInSlot(i) != ItemStackTools.getEmptyStack() && this.inventory.getStackInSlot(i).getItem() instanceof ItemModule) {
					ItemModule otherSlot = (ItemModule) this.inventory.getStackInSlot(i).getItem();
					if (otherSlot == stack.getItem()) {
						return false;
					}
					if (!otherSlot.isCompatible(upgrade) || !upgrade.isCompatible(otherSlot)) {
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
