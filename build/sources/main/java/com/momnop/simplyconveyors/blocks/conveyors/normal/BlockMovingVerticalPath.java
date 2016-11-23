package com.momnop.simplyconveyors.blocks.conveyors.normal;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class BlockMovingVerticalPath extends BlockPoweredConveyor {
	
	private final double speed;
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockMovingVerticalPath(double speed, Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		if (state.getValue(FACING) == EnumFacing.SOUTH) {
			return new AxisAlignedBB(0F, 0F, 0F, 1F, 1F, 15F / 16F);
		} else if (state.getValue(FACING) == EnumFacing.WEST) {
			return new AxisAlignedBB(1F / 16F, 0F, 0F, 1F, 1F, 1F);
		} else if (state.getValue(FACING) == EnumFacing.NORTH) {
			return new AxisAlignedBB(0F, 0F, 1F / 16F, 1F, 1F, 1F);
		} else if (state.getValue(FACING) == EnumFacing.EAST) {
			return new AxisAlignedBB(0F, 0F, 0F, 15F / 16F, 1F, 1F);
		}
		return null;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
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

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState blockState,
			Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (!entity.isSneaking() && !blockState.getValue(POWERED)) {
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);
		
			entity.motionY += this.getSpeed();
			if (entity.motionY > this.getSpeed()) {
				entity.motionY = this.getSpeed();
			}
			
			if (direction == EnumFacing.EAST) {
				entity.motionX = 0.1F;
			}
			
			if (direction == EnumFacing.WEST) {
				entity.motionX = -0.1F;
			}
			
			if (direction == EnumFacing.SOUTH) {
				entity.motionZ = 0.1F;
			}
			
			if (direction == EnumFacing.NORTH) {
				entity.motionZ = -0.1F;
			}
		}
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
					placer.getHorizontalFacing());
		} else {
			return this.getDefaultState().withProperty(FACING,
					placer.getHorizontalFacing().getOpposite());
		}
	}
}