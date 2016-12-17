package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.entity.EntityBlock;

public class TileEntityBlockMovingPath extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (!this.getWorld().isAirBlock(this.getPos().up()) && !this.getWorld().isBlockPowered(this.getPos())) {
			EntityBlock entityBlock = null;
			if (this.getWorld().getTileEntity(this.getPos().up()) != null) {
				TileEntity tile = this.getWorld().getTileEntity(this.getPos().up());
				entityBlock = new EntityBlock(this.getWorld(), this.getPos().getX() + 0.5, this.getPos().getY() + (1F / 16F), (this.getPos().getZ() + 0.5), this.getWorld().getBlockState(this.getPos().up()), tile.writeToNBT(new NBTTagCompound()));
			} else {
				entityBlock = new EntityBlock(this.getWorld(), this.getPos().getX() + 0.5, this.getPos().getY() + (1F / 16F), (this.getPos().getZ() + 0.5), this.getWorld().getBlockState(this.getPos().up()), new NBTTagCompound());
			}
			
			if (this.getWorld().isRemote == false) {
				this.getWorld().spawnEntity(entityBlock);
			}
			
			if (this.getWorld().getTileEntity(this.getPos().up()) != null) {
				this.getWorld().getTileEntity(this.getPos().up()).readFromNBT(new NBTTagCompound());
			}
			
			this.getWorld().setBlockToAir(this.getPos().up());
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos,
			IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
	
}
