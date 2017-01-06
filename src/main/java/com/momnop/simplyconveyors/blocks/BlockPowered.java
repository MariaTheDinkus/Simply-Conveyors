package com.momnop.simplyconveyors.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPowered extends Block
{

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	protected BlockPowered(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		this.neighborChanged(state, worldIn, pos, worldIn.getBlockState(pos).getBlock(), null);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_)
	{
		if(state.getValue(POWERED) && !worldIn.isBlockPowered(pos))
		{
			worldIn.scheduleUpdate(pos, this, 4);
		}
		else if(!state.getValue(POWERED) && worldIn.isBlockPowered(pos))
		{
			worldIn.setBlockState(pos, state.withProperty(POWERED, true), 2);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if(state.getValue(POWERED) && !worldIn.isBlockPowered(pos))
		{
			worldIn.setBlockState(pos, state.withProperty(POWERED, false), 2);
		}
	}

}
