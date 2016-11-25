package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingSlowStairPath;

public class TileEntitySlowMovingStair extends TileEntity implements ITickable {
	
	@Override
    public void update()
    {
			BlockMovingSlowStairPath block = (BlockMovingSlowStairPath) blockType;
            List entities = this.getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(
            		pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 1.1F, pos.getZ() + 1));
            for (Object obj : entities)
               {
               if (obj instanceof Entity && !this.getWorld().getBlockState(this.getPos()).getValue(block.POWERED)) {
            	   Entity entity = (Entity)obj;
                if (entity != null && entity.onGround && !entity.isInWater())
                {
                	entity.stepHeight = 0.6F;
                	if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.EAST) {
            			entity.motionZ += 0.2F;
            			if (entity.motionZ > 0.2F) {
            				entity.motionZ = 0.2F;
            			}
            		} else if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.SOUTH) {
            			entity.motionX += -0.2F;
            			if (entity.motionX < -0.2F) {
            				entity.motionX = -0.2F;
            			}
            		} else if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.NORTH) {
            			entity.motionX += 0.2F;
            			if (entity.motionX > 0.2F) {
            				entity.motionX = 0.2F;
            			}
            		} else if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.WEST) {
            			entity.motionZ += -0.2F;
            			if (entity.motionZ < -0.2F) {
            				entity.motionZ = -0.2F;
            			}
            		}
                }
            }
        }
    }
}
