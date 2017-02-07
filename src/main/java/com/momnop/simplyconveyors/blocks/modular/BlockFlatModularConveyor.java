package com.momnop.simplyconveyors.blocks.modular;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.blocks.base.BlockFlatConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileModularConveyor;

public class BlockFlatModularConveyor extends BlockFlatConveyor implements ITileEntityProvider
{
	public BlockFlatModularConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileModularConveyor();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileModularConveyor)
		{
			if(playerIn.isSneaking())
			{
				if(!worldIn.isRemote)
				{
					playerIn.openGui(SimplyConveyors.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
				return true;
			}
		}
		return false;
	}
}
