package com.momnop.simplyconveyors.inventory;

import com.momnop.simplyconveyors.api.ItemModule;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModule extends Slot
{

	public SlotModule(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemModule) {
			ItemModule upgrade = (ItemModule) stack.getItem();
			
			for (int i = 0; i < 3; i++) {
				if (this.inventory.getStackInSlot(i) != ItemStack.EMPTY && this.inventory.getStackInSlot(i).getItem() instanceof ItemModule) {
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
	
}
