package com.momnop.simplyconveyors.blocks.conveyors;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredFacing;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityFastMovingStair;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntitySlowMovingStair;
import com.momnop.simplyconveyors.info.ModInfo;

public class BlockMovingFastStairPath extends BlockPoweredFacing implements
		ITileEntityProvider {
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	public BlockMovingFastStairPath(Material material, String unlocalizedName) {
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
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
			super.addCollisionBoxToList(state, worldIn, pos, mask, list, entityIn);
		} else if (state.getValue(FACING) == EnumFacing.WEST) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 0.5F, pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 1F, pos.getZ() + 0.5F), list, mask);
			super.addCollisionBoxToList(state, worldIn, pos, mask, list, entityIn);
		} else if (state.getValue(FACING) == EnumFacing.NORTH) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 0.5F, pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0.5F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 1F, pos.getZ() + 1F), list, mask);
			super.addCollisionBoxToList(state, worldIn, pos, mask, list, entityIn);
		}  else if (state.getValue(FACING) == EnumFacing.EAST) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + 0.5F, pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0.5F, pos.getX() + 1F, pos.getY() + 1F, pos.getZ() + 1F), list, mask);
			super.addCollisionBoxToList(state, worldIn, pos, mask, list, entityIn);
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

	/**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, POWERED);
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
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
		return new TileEntityFastMovingStair();
	}
}