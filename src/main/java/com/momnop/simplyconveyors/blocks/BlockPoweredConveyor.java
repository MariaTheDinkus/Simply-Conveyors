package com.momnop.simplyconveyors.blocks;

import java.util.Random;

import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingTrapDoorPath;

public class BlockPoweredConveyor extends CompatBlock {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	protected BlockPoweredConveyor(Material materialIn) {
		super(materialIn);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.neighborChanged(state, worldIn, pos, worldIn.getBlockState(pos).getBlock());
	}
	
	/**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		boolean powered = false;
		if (String.valueOf(meta).length() == 2) {
			powered = true;
		} else {
			powered = false;
		}
		
		int metaNew = 0;
		if (String.valueOf(meta).length() == 1) {
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(0)));
		} else {
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(1)));
		}
		
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(metaNew)).withProperty(POWERED, powered);
    }

	@Override
	public int getMetaFromState(IBlockState state) {
		int powered = 0;
		if (state.getValue(POWERED) == false) {
			powered = 0;
		} else {
			powered = 1;
		}
		
		return Integer.parseInt(powered + "" + state.getValue(FACING).getIndex());
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED });
	}
	
	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		if (placer.isSneaking()) {
			return this.getDefaultState().withProperty(FACING,
					placer.getHorizontalFacing());
		} else {
			return this.getDefaultState().withProperty(FACING,
					placer.getHorizontalFacing().getOpposite());
		}
	}
	
	/**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
	@Override
    public void clOnNeighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (!worldIn.isRemote)
        {
        	IBlockState blockState = worldIn.getBlockState(pos);
            if (blockState.getValue(POWERED) && !worldIn.isBlockPowered(pos))
            {
                worldIn.scheduleUpdate(pos, this, 4);
            }
            else if (!blockState.getValue(POWERED) && worldIn.isBlockPowered(pos))
            {
            	worldIn.setBlockState(pos, blockState.withProperty(POWERED, true), 2);
            	if (state.getBlock() instanceof BlockMovingTrapDoorPath) {
            		worldIn.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1, 1);
            	}
            }
        }
    }

	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
        	IBlockState blockState = worldIn.getBlockState(pos);
            if (state.getValue(POWERED) && !worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, blockState.withProperty(POWERED, false), 2);
                if (state.getBlock() instanceof BlockMovingTrapDoorPath) {
            		worldIn.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1, 1);
            	}
            }
        }
    }

}