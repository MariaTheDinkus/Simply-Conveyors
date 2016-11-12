package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingFastestStairPath;

public class TileEntityFastestMovingStair extends TileEntity implements ITickable {
	
	@Override
    public void update()
    {
			BlockMovingFastestStairPath block = (BlockMovingFastestStairPath) blockType;
            List entities = this.worldObj.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(
            		pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 1.1F, pos.getZ() + 1));
            for (Object obj : entities)
               {
               if (obj instanceof Entity && !this.worldObj.isBlockPowered(pos)) {
            	   Entity entity = (Entity)obj;
                if (entity != null && entity.onGround && !entity.isInWater())
                {
                	entity.stepHeight = 0.6F;
                	if (this.worldObj.getBlockState(this.pos).getValue(block.FACING) == EnumFacing.EAST) {
            			entity.motionZ += 0.65F;
            			if (entity.motionZ > 0.65F) {
            				entity.motionZ = 0.65F;
            			}
            		} else if (this.worldObj.getBlockState(this.pos).getValue(block.FACING) == EnumFacing.SOUTH) {
            			entity.motionX += -0.65F;
            			if (entity.motionX < -0.65F) {
            				entity.motionX = -0.65F;
            			}
            		} else if (this.worldObj.getBlockState(this.pos).getValue(block.FACING) == EnumFacing.NORTH) {
            			entity.motionX += 0.65F;
            			if (entity.motionX > 0.65F) {
            				entity.motionX = 0.65F;
            			}
            		} else if (this.worldObj.getBlockState(this.pos).getValue(block.FACING) == EnumFacing.WEST) {
            			entity.motionZ += -0.65F;
            			if (entity.motionZ < -0.65F) {
            				entity.motionZ = -0.65F;
            			}
            		}
                }
            }
        }
    }
}
