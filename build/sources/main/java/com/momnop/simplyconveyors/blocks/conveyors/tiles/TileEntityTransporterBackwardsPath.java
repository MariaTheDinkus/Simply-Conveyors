package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsTransporterPath;

public class TileEntityTransporterBackwardsPath extends TileEntity implements ITickable {

	@Override
	public void update() {
		markDirty();
		
		if (blockType instanceof BlockMovingBackwardsTransporterPath && this.getWorld().getTotalWorldTime() % 20 == 0) {
			BlockMovingBackwardsTransporterPath blockTransporter = (BlockMovingBackwardsTransporterPath) blockType;
			TileEntity tile = this.getWorld().getTileEntity(this.getPos().down().add(this.getWorld().getBlockState(this.getPos()).getValue(blockTransporter.FACING).getDirectionVec()));
			if (tile != null && tile instanceof IInventory && !this.getWorld().getBlockState(this.getPos()).getValue(blockTransporter.POWERED)) {
				IInventory inventory = (IInventory) tile;
				for (int i = 0; i < inventory.getSizeInventory(); i++) {
					if (inventory.getStackInSlot(i) != null) {
						if (this.getWorld().isRemote == false) {
							EntityItem entityItem = new EntityItem(this.worldObj, this.getPos().getX() + 0.5F, this.getPos().getY() + 0.4, this.getPos().getZ() + 0.5F, inventory.getStackInSlot(i));
							this.getWorld().spawnEntityInWorld(entityItem);
							entityItem.motionX = 0;
							entityItem.motionY = 0;
							entityItem.motionZ = 0;
							entityItem.setPosition(this.getPos().getX() + 0.5F, this.getPos().getY() - 0.4, this.getPos().getZ() + 0.5F);
							inventory.setInventorySlotContents(i, null);
							break;
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos,
			IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}
