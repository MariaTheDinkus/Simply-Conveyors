package com.momnop.simplyconveyors.blocks.advanced;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.base.BlockFlatConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileAdvancedConveyor;

public class BlockFlatAdvancedConveyor extends BlockFlatConveyor implements ITileEntityProvider
{
	public BlockFlatAdvancedConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileAdvancedConveyor();
	}
}
