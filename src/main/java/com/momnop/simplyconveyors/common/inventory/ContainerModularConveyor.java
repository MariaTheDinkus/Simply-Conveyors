package com.momnop.simplyconveyors.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.momnop.simplyconveyors.common.blocks.tiles.TileModularConveyor;

public class ContainerModularConveyor extends Container
{
	private TileModularConveyor tileEntity;
	
	public IInventory inventory;
	
    public ContainerModularConveyor(InventoryPlayer playerInventory, TileModularConveyor tile)
    {
        super();
        
        this.tileEntity = tile;
        
        if (tile instanceof IInventory) {
        	this.inventory = (IInventory) tile;
        } else {
        	this.inventory = null;
        }

        this.addSlotToContainer(new SlotModule(this.inventory, 0, 56, 28));
        this.addSlotToContainer(new SlotModule(this.inventory, 1, 80, 28));
        this.addSlotToContainer(new SlotModule(this.inventory, 2, 104, 28));
        
        this.addSlotToContainer(new SlotTrack(this.inventory, 3, 80, 52));

        addPlayerSlots(playerInventory);
    }
    
    @Override
    protected Slot addSlotToContainer(Slot slotIn) {
    	// TODO Auto-generated method stub
    	return super.addSlotToContainer(slotIn);
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
    	return ((TileModularConveyor) tileEntity).isUsableByPlayer(player);
    }
}
