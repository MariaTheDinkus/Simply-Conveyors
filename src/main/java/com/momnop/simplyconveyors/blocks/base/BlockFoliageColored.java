package com.momnop.simplyconveyors.blocks.base;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class BlockFoliageColored extends BlockNormal
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
