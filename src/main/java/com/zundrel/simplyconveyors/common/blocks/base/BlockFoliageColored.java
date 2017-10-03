package com.zundrel.simplyconveyors.common.blocks.base;

import com.zundrel.simplyconveyors.SimplyConveyors;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;

public class BlockFoliageColored extends BlockBasic
{
	public BlockFoliageColored(String unlocalizedName, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, material, hardness, type, tab);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}
