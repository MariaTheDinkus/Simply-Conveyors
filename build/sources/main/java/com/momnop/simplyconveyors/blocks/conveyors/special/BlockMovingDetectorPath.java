package com.momnop.simplyconveyors.blocks.conveyors.special;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsSpecialCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityGrabberPath;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class BlockMovingDetectorPath extends BlockPoweredConveyor implements ITileEntityProvider {
	
	private final double speed;
	
	public BlockMovingDetectorPath(double speed, Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsSpecialCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString()
				.replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		setHarvestLevel("pickaxe", 0);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return SimplyConveyorsBlocks.CONVEYOR_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos,
			Block blockIn) {
		
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		if (blockState.getValue(POWERED) == true && side != blockState.getValue(FACING) && side != blockState.getValue(FACING).getOpposite()) {
			return 15;
		}
        return 0;
    }

	@Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		if (blockState.getValue(POWERED) == true && side != blockState.getValue(FACING) && side != blockState.getValue(FACING).getOpposite()) {
			return 15;
		}
        return 0;
    }
	
	private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing)
    {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
    }
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (((Boolean)state.getValue(POWERED)).booleanValue())
            {
            	worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
                this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock,
			EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
				(float) (entity.posX - clickedBlock.getX()),
				(float) (entity.posY - clickedBlock.getY()),
				(float) (entity.posZ - clickedBlock.getZ()));
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
	public void onEntityCollidedWithBlock(World world, BlockPos pos,
			IBlockState blockState, Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (!entity.isSneaking()) {
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);
			
            entity.motionX += this.getSpeed() * direction.getFrontOffsetX();
            ConveyorHelper.lockSpeed(false, this.getSpeed(), entity, direction);
			
			entity.motionZ += this.getSpeed() * direction.getFrontOffsetZ();
			ConveyorHelper.lockSpeed(true, this.getSpeed(), entity, direction);

			if (entity instanceof EntityItem) {
				final EntityItem item = (EntityItem) entity;
				item.setAgeToCreativeDespawnTime();
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (playerIn.isSneaking()) {
			TileEntityDetectorPath grabber = (TileEntityDetectorPath) worldIn.getTileEntity(pos);
			try {
				if (!worldIn.isRemote) {
					playerIn.addChatMessage(new TextComponentString("Currently filtering: " + Class.forName(grabber.getEntityFilter()).getSimpleName()));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
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

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDetectorPath();
	}
}