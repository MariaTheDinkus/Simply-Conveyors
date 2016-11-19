package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import com.momnop.simplyconveyors.entity.EntityBlock;

public class TileEntityBlockMovingPath extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (!this.getWorld().isAirBlock(this.getPos().up()) && this.getWorld().getTileEntity(this.getPos().up()) == null) {
			EntityBlock entityBlock = new EntityBlock(this.getWorld(), this.getPos().getX() + 0.5, this.getPos().getY() + 1, this.getPos().getZ() + 0.5, this.getWorld().getBlockState(this.getPos().up()));
			this.getWorld().setBlockToAir(this.getPos().up());
//			if (this.getWorld().isRemote == false) {
				this.getWorld().spawnEntityInWorld(entityBlock);
//			}
		}
	}
	
}
