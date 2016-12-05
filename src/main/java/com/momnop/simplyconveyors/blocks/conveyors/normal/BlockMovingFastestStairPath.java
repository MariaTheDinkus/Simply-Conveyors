package com.momnop.simplyconveyors.blocks.conveyors.normal;

import java.util.List;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityFastestMovingStair;

public class BlockMovingFastestStairPath extends BlockPoweredConveyor implements
		ITileEntityProvider {
	
	public BlockMovingFastestStairPath(Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		setHarvestLevel("pickaxe", 0);
		
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return new AxisAlignedBB(pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0, pos.getX() + 1.0, pos.getY() + 0.5F, pos.getZ() + 1.0);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB mask,
			List<AxisAlignedBB> list, Entity entityIn) {
		if (state.getValue(FACING) == EnumFacing.SOUTH) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 0.5F, pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 0.5F, pos.getY() + 1F, pos.getZ() + 1F), list, mask);
		} else if (state.getValue(FACING) == EnumFacing.WEST) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 0.5F, pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 1F, pos.getZ() + 0.5F), list, mask);
		} else if (state.getValue(FACING) == EnumFacing.NORTH) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 0.5F, pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0.5F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 1F, pos.getZ() + 1F), list, mask);
		}  else if (state.getValue(FACING) == EnumFacing.EAST) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 0.5F, pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0.5F, pos.getX() + 1F, pos.getY() + 1F, pos.getZ() + 1F), list, mask);
		}
	}
	
	public void addCollisionBox(AxisAlignedBB box, List list, AxisAlignedBB mask)
	{
        if (box != null && mask.intersectsWith(box))
        {
            list.add(box);
        }
	}

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer, EnumHand hand) {
		if (placer.isSneaking()) {
			return this.getDefaultState().withProperty(FACING,
					placer.getHorizontalFacing().rotateY());
		} else {
			return this.getDefaultState().withProperty(FACING,
					placer.getHorizontalFacing().getOpposite().rotateY());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFastestMovingStair();
	}
}