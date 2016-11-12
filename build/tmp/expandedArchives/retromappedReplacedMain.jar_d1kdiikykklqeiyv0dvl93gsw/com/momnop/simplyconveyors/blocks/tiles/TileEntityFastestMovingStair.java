package com.momnop.simplyconveyors.blocks.tiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import com.momnop.simplyconveyors.blocks.BlockMovingFastestStairPath;

public class TileEntityFastestMovingStair extends TileEntity implements ITickable {
	
	@Override
    public void func_73660_a()
    {
			BlockMovingFastestStairPath block = (BlockMovingFastestStairPath) field_145854_h;
            List entities = this.field_145850_b.func_72872_a(Entity.class, new AxisAlignedBB(
            		field_174879_c.func_177958_n(), field_174879_c.func_177956_o() + 1, field_174879_c.func_177952_p(), field_174879_c.func_177958_n() + 1, field_174879_c.func_177956_o() + 1.1F, field_174879_c.func_177952_p() + 1));
            for (Object obj : entities)
               {
               if (obj instanceof Entity && !this.field_145850_b.func_175640_z(field_174879_c)) {
            	   Entity entity = (Entity)obj;
                if (entity != null && entity.field_70122_E && !entity.func_70090_H())
                {
                	entity.field_70138_W = 0.6F;
                	if (this.field_145850_b.func_180495_p(this.field_174879_c).func_177229_b(block.field_185512_D) == EnumFacing.EAST) {
            			entity.field_70179_y += 0.65F;
            			if (entity.field_70179_y > 0.65F) {
            				entity.field_70179_y = 0.65F;
            			}
            		} else if (this.field_145850_b.func_180495_p(this.field_174879_c).func_177229_b(block.field_185512_D) == EnumFacing.SOUTH) {
            			entity.field_70159_w += -0.65F;
            			if (entity.field_70159_w < -0.65F) {
            				entity.field_70159_w = -0.65F;
            			}
            		} else if (this.field_145850_b.func_180495_p(this.field_174879_c).func_177229_b(block.field_185512_D) == EnumFacing.NORTH) {
            			entity.field_70159_w += 0.65F;
            			if (entity.field_70159_w > 0.65F) {
            				entity.field_70159_w = 0.65F;
            			}
            		} else if (this.field_145850_b.func_180495_p(this.field_174879_c).func_177229_b(block.field_185512_D) == EnumFacing.WEST) {
            			entity.field_70179_y += -0.65F;
            			if (entity.field_70179_y < -0.65F) {
            				entity.field_70179_y = -0.65F;
            			}
            		}
                }
            }
        }
    }
}
