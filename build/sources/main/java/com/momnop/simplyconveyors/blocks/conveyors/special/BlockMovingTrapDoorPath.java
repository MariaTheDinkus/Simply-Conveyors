package com.momnop.simplyconveyors.blocks.conveyors.special;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class BlockMovingTrapDoorPath extends BlockPoweredConveyor {
	
	private final double speed;
	
	public BlockMovingTrapDoorPath(double speed, Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
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
	public void addCollisionBoxToList(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB mask,
			List<AxisAlignedBB> list, Entity entityIn) {
		if (state.getValue(POWERED) == true) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + (15F / 16F), pos.getZ() + (1F / 16F)), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + (1F / 16F), pos.getY() + (15F / 16F), pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + (15F / 16F), pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + (15F / 16F), pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + (15F / 16F), pos.getX() + 1F, pos.getY() + (15F / 16F), pos.getZ() + 1F), list, mask);
		} else if (state.getValue(POWERED) == false) {
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + (15F / 16F), pos.getZ() + (1F / 16F)), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + (1F / 16F), pos.getY() + (15F / 16F), pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + (15F / 16F), pos.getY() + 0F, pos.getZ() + 0F, pos.getX() + 1F, pos.getY() + (15F / 16F), pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX() + 0F, pos.getY() + 0F, pos.getZ() + (15F / 16F), pos.getX() + 1F, pos.getY() + (15F / 16F), pos.getZ() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.getX(), pos.getY() + (14F / 16F), pos.getZ(), pos.getX() + 1F, pos.getY() + (15F / 16F), pos.getZ() + 1F), list, mask);
		}
	}
	
	public void addCollisionBox(AxisAlignedBB box, List list, AxisAlignedBB mask)
	{
        if (box != null && mask.intersectsWith(box))
        {
            list.add(box);
        }
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
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock,
			EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
				(float) (entity.posX - clickedBlock.getX()),
				(float) (entity.posY - clickedBlock.getY()),
				(float) (entity.posZ - clickedBlock.getZ()));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos,
			IBlockState blockState, Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (!entity.isSneaking() && !blockState.getValue(POWERED)) {
			ConveyorHelper.centerBasedOnFacing(speed, true, pos, entity, direction);
			
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
}