package com.momnop.simplyconveyors.common.blocks.advanced;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.common.blocks.base.BlockFlatConveyor;
import com.momnop.simplyconveyors.common.blocks.tiles.TileAdvancedConveyor;

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
	
	@Override
	public void onWrenched(World world, IBlockState state, BlockPos pos,
			EntityPlayer player, EnumHand hand) {
		world.setBlockState(pos, state.withProperty(FACING, state.getValue(FACING).rotateY()));
	}
}
