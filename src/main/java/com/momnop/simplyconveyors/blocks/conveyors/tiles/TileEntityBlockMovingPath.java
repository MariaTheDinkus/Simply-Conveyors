package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import com.momnop.simplyconveyors.entity.EntityBlock;

public class TileEntityBlockMovingPath extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (!this.getWorld().isAirBlock(this.getPos().up()) && !this.getWorld().isBlockPowered(this.getPos())) {
			EntityBlock entityBlock = null;
			if (this.getWorld().getTileEntity(this.getPos().up()) != null) {
				TileEntity tile = this.getWorld().getTileEntity(this.getPos().up());
				entityBlock = new EntityBlock(this.getWorld(), this.getPos().getX() + 0.5, this.getPos().getY() + 1, this.getPos().getZ() + 0.5, this.getWorld().getBlockState(this.getPos().up()), tile.writeToNBT(new NBTTagCompound()));
			} else {
				entityBlock = new EntityBlock(this.getWorld(), this.getPos().getX() + 0.5, this.getPos().getY() + 1, this.getPos().getZ() + 0.5, this.getWorld().getBlockState(this.getPos().up()), new NBTTagCompound());
			}
			
			if (this.getWorld().isRemote == false) {
				this.getWorld().spawnEntityInWorld(entityBlock);
			}
			
			if (this.getWorld().getTileEntity(this.getPos().up()) != null) {
				this.getWorld().getTileEntity(this.getPos().up()).readFromNBT(new NBTTagCompound());
			}
			
			this.getWorld().setBlockToAir(this.getPos().up());
		}
	}
	
}
