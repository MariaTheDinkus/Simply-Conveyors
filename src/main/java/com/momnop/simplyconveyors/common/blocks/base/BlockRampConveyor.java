package com.momnop.simplyconveyors.common.blocks.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcjty.lib.CompatLayer;
import mcjty.lib.tools.InventoryTools;
import mcjty.lib.tools.ItemStackList;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.helpers.RotationUtils;
import com.momnop.simplyconveyors.common.items.ItemConveyorResistanceBoots;

public class BlockRampConveyor extends BlockPoweredConveyor {

	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public boolean up;
	
	public BlockRampConveyor(String unlocalizedName, double speed, Material material,
			float hardness, SoundType type, boolean up) {
		super(unlocalizedName, speed, material, hardness, type, null);
		setDefaultState(this.getDefaultState().withProperty(POWERED, false));
		this.up = up;
	}
	
	@Override
	public void clAddCollisionBoxToList(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		EnumFacing facing = state.getValue(FACING).getOpposite();
		
		ArrayList<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();
		for (int i = 0; i < 31; i++) {
			AxisAlignedBB axisBB = new AxisAlignedBB(0, 1 / 32f + ((i - 1) * (1 / 32f)), 1 / 32f + ((i - 1) * (1 / 32f)), 1, (i + 1) * (1 / 32f), (i + 1) * (1 / 32f));
			axisBB = RotationUtils.getRotatedAABB(axisBB, facing);
			list.add(axisBB);
		}
		
		for (AxisAlignedBB axisBB : list) {
			if (entityBox.intersectsWith(axisBB.offset(pos))) {
				collidingBoxes.add(axisBB.offset(pos));
			}
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		EnumFacing facing = state.getValue(FACING);
		
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			List<ItemStack> playerInventory = InventoryTools.getContainerItemStacks(player.inventoryContainer);
			if(!player.onGround || playerInventory.get(8) != ItemStackTools.getEmptyStack()
					&& playerInventory.get(8).getItem() instanceof ItemConveyorResistanceBoots || player.capabilities.isFlying)
			{
				return;
			}
		}
		
		if (entityIn instanceof EntityItem) {
			EntityItem item = (EntityItem) entityIn;
			item.setAgeToCreativeDespawnTime();
		}
			
		if (!state.getValue(POWERED) && ConfigHandler.stopWhileSneaking && !entityIn.isSneaking() || !state.getValue(POWERED) && !ConfigHandler.stopWhileSneaking) {
			if (this.up) {
				ConveyorHelper.pushEntity(entityIn, pos, 0.125F, facing, true);
			} else {
				ConveyorHelper.pushEntity(entityIn, pos, 0.125F, facing.getOpposite(), true);
			}
		}
	}

	@Override
	public void onWrenched(World world, IBlockState state, BlockPos pos,
			EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			world.setBlockState(pos, state.withProperty(FACING, state.getValue(FACING).rotateY()));
		} else if (player.isSneaking()) {
			if (up == true) {
				if (this.getSpeed() == SimplyConveyorsBlocks.tier1Speed) {
					world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_ramp_slow_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
				} else if (this.getSpeed() == SimplyConveyorsBlocks.tier2Speed) {
					world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_ramp_intermediate_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
				} else if (this.getSpeed() == SimplyConveyorsBlocks.tier3Speed) {
					world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_ramp_fast_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
				}
			} else if (up == false) {
				if (this.getSpeed() == SimplyConveyorsBlocks.tier1Speed) {
					world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_slow.getDefaultState().withProperty(FACING, state.getValue(FACING)));
				} else if (this.getSpeed() == SimplyConveyorsBlocks.tier2Speed) {
					world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_intermediate.getDefaultState().withProperty(FACING, state.getValue(FACING)));
				} else if (this.getSpeed() == SimplyConveyorsBlocks.tier3Speed) {
					world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_fast.getDefaultState().withProperty(FACING, state.getValue(FACING)));
				}
			}
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (this.getSpeed() == SimplyConveyorsBlocks.tier1Speed) {
			return Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_slow);
		} else if (this.getSpeed() == SimplyConveyorsBlocks.tier2Speed) {
			return Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_intermediate);
		} else if (this.getSpeed() == SimplyConveyorsBlocks.tier3Speed) {
			return Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_fast);
		}
		return Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_slow);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target,
			World world, BlockPos pos, EntityPlayer player) {
		if (this.getSpeed() == SimplyConveyorsBlocks.tier1Speed) {
			return new ItemStack(SimplyConveyorsBlocks.conveyor_slow);
		} else if (this.getSpeed() == SimplyConveyorsBlocks.tier2Speed) {
			return new ItemStack(SimplyConveyorsBlocks.conveyor_intermediate);
		} else if (this.getSpeed() == SimplyConveyorsBlocks.tier3Speed) {
			return new ItemStack(SimplyConveyorsBlocks.conveyor_fast);
		}
		return new ItemStack(SimplyConveyorsBlocks.conveyor_slow);
	}
	
}
