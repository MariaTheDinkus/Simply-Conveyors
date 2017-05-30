package com.momnop.simplyconveyors.blocks.roads;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.momnop.simplyconveyors.blocks.base.BlockSpeed;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class BlockConnecting extends BlockSpeed
{

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool NONE = PropertyBool.create("none");

	public BlockConnecting(String unlocalizedName, Material materialIn, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, materialIn, hardness, type, tab);
		setDefaultState(this.getDefaultState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST, NONE });
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = this.getDefaultState();
		return state;
	}

	public boolean isAdjacentBlockOfMyTypeOrOpaque(IBlockAccess world, BlockPos position, EnumFacing facing)
	{

		assert null != world : "world cannot be null";
		assert null != position : "position cannot be null";
		assert null != this : "type cannot be null";

		BlockPos newPosition = position.offset(facing);
		IBlockState blockState = world.getBlockState(newPosition);
		Block block = (null == blockState) ? null : blockState.getBlock();

		if(this == block)
		{
			return true;
		}
		return false;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position)
	{

		state = state
				.withProperty(EAST, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.EAST))
				.withProperty(NORTH, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.NORTH))
				.withProperty(SOUTH, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.SOUTH))
				.withProperty(WEST, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.WEST))
				.withProperty(
						NONE,
						!this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.EAST) && !this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.NORTH)
								&& !this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.SOUTH) && !this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.WEST));

		return state;
	}
}
