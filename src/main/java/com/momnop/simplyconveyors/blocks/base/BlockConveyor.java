package com.momnop.simplyconveyors.blocks.base;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockConveyor extends BlockNormal
{
	private double speed = 0;
	
	public BlockConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, material, hardness, type, tab);
		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
		this.speed = speed;
		this.setHardness(1.5F);
	}
	
	public double getSpeed() {
		return speed;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}
	
	@Override
	protected IBlockState clGetStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if (!placer.isSneaking()) {
			return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
		} else {
			return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		}
	}
}
