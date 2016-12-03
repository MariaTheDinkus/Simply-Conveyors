package com.momnop.simplyconveyors.blocks.conveyors.normal;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;

public class BlockMovingVerticalPath extends BlockPoweredConveyor {
	
	private final double speed;
	
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
		if (state.getValue(FACING) == EnumFacing.NORTH) {
			return new AxisAlignedBB(0, 0, 0.1F / 16F, 1F, 1F, 1F);
		} else if (state.getValue(FACING) == EnumFacing.EAST) {
			return new AxisAlignedBB(0F, 0F, 0F, 15.9F / 16F, 1F, 1F);
		} else if (state.getValue(FACING) == EnumFacing.SOUTH) {
			return new AxisAlignedBB(0F, 0F, 0F, 1F, 1F, 15.9F / 16F);
		} else if (state.getValue(FACING) == EnumFacing.WEST) {
			return new AxisAlignedBB(0.1F / 16F, 0F, 0F, 1F, 1F, 1F);
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

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState blockState,
			Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.inventory.player.inventory.armorInventory[EntityEquipmentSlot.FEET.getIndex()] != ItemStackTools.getEmptyStack() && player.inventory.armorInventory[EntityEquipmentSlot.FEET.getIndex()].getItem() instanceof ItemConveyorResistanceBoots) {
				return;
			}
		}
		
		if (!entity.isSneaking() && !blockState.getValue(POWERED)) {
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);
		
			entity.motionY += this.getSpeed();
			if (entity.motionY > this.getSpeed()) {
				entity.motionY = this.getSpeed();
			}
			
			if (direction == EnumFacing.EAST) {
				entity.motionX += 0.1F;
				ConveyorHelper.lockSpeed(false, 0.1F, entity, EnumFacing.EAST);
			}
			
			if (direction == EnumFacing.WEST) {
				entity.motionX += -0.1F;
				ConveyorHelper.lockSpeed(false, 0.1F, entity, EnumFacing.WEST);
			}
			
			if (direction == EnumFacing.SOUTH) {
				entity.motionZ += 0.1F;
				ConveyorHelper.lockSpeed(true, 0.1F, entity, EnumFacing.SOUTH);
			}
			
			if (direction == EnumFacing.NORTH) {
				entity.motionZ += -0.1F;
				ConveyorHelper.lockSpeed(true, 0.1F, entity, EnumFacing.NORTH);
			}
		}
	}
}