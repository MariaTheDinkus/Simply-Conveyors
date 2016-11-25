package com.momnop.simplyconveyors.blocks.conveyors.special;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsSpecialCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class BlockMovingBackwardsHoldingPath extends BlockPoweredConveyor {
	
	public BlockMovingBackwardsHoldingPath(Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsSpecialCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        setHarvestLevel("pickaxe", 0);
		useNeighborBrightness = true;
		
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return SimplyConveyorsBlocks.UPSIDE_DOWN_CONVEYOR_AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }
    
    @Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos,
			IBlockState blockState, Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (!blockState.getValue(POWERED)) {
			ConveyorHelper.centerBasedOnFacing(false, pos, entity, EnumFacing.SOUTH);
			ConveyorHelper.centerBasedOnFacing(false, pos, entity, EnumFacing.WEST);
			
			entity.motionY += 0.4F;
			if (entity.motionY > 0.4F) {
				entity.motionY = 0.4F;
			}
			
			if (entity instanceof EntityItem) {
				final EntityItem item = (EntityItem) entity;
				item.setAgeToCreativeDespawnTime();
			}
		}
	}
}