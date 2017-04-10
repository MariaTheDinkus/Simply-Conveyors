package com.momnop.simplyconveyors.blocks.base;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPoweredConveyor extends BlockConveyor
{
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	public BlockPoweredConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		setDefaultState(this.getDefaultState().withProperty(POWERED, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED });
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean powered = false;
		if(String.valueOf(meta).length() == 2)
		{
			powered = true;
		}
		else
		{
			powered = false;
		}

		int metaNew = 0;
		if(String.valueOf(meta).length() == 1)
		{
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(0)));
		}
		else
		{
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(1)));
		}

		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(metaNew)).withProperty(POWERED, powered);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int powered = 0;
		if(state.getValue(POWERED) == false)
		{
			powered = 0;
		}
		else
		{
			powered = 1;
		}

		return Integer.parseInt(powered + "" + state.getValue(FACING).getIndex());
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
