package com.momnop.simplyconveyors.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.blocks.tiles.TileModularConveyor;

public class ContainerModularConveyor extends Container
{
	private TileModularConveyor tileEntity;
	
	public IInventory inventory;
	
    public ContainerModularConveyor(InventoryPlayer playerInventory, TileModularConveyor tile)
    {
        super();
        
        this.tileEntity = tile;
        
        this.inventory = (IInventory) tile;

        this.addSlotToContainer(new SlotModule(this.inventory, 0, 56, 35));
        this.addSlotToContainer(new SlotModule(this.inventory, 1, 80, 35));
        this.addSlotToContainer(new SlotModule(this.inventory, 2, 104, 35));

        addPlayerSlots(playerInventory);
    }
    
    public void addPlayerSlots(InventoryPlayer playerInventory)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    	return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
    	return tileEntity.isUsableByPlayer(player);
    }
}
