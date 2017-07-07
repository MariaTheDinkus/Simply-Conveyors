package com.momnop.simplyconveyors.common.blocks.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcjty.lib.tools.ItemStackList;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.helpers.RotationUtils;
import com.momnop.simplyconveyors.common.items.ItemConveyorResistanceBoots;

public class BlockStairConveyor extends BlockPoweredConveyor
{

	public Block block;
	
	public BlockStairConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		
		this.block = this;
	}
	
	public BlockStairConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, Block block, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		
		this.block = block;
	}

	@Override
	public void clAddCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity entityIn)
	{
		AxisAlignedBB bottom = new AxisAlignedBB((0.1F / 16F), 0F, 0F, 1F, 0.5F, 1F);
		AxisAlignedBB top = new AxisAlignedBB(0.5F, 0F, 0F, 1F, (15.9F / 16F), 1F);
		
		top = RotationUtils.getRotatedAABB(top, state.getValue(FACING).rotateYCCW());
		
		addCollisionBox(bottom, pos, list, mask);
		addCollisionBox(top, pos, list, mask);
	}

	public void addCollisionBox(AxisAlignedBB box, BlockPos pos,
			List collidingBoxes, AxisAlignedBB entityBox) {
		if (box != null && entityBox.intersectsWith(box.offset(pos))) {
			boolean add = true;
			collidingBoxes.add(box.offset(pos));
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		EnumFacing facing = state.getValue(FACING);
		
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;

			ItemStackList armor = null;
			
			Object armorInventory = (Object) player.inventory.armorInventory;
			if (armorInventory instanceof ItemStack[]) {
				armor = new ItemStackList((List<ItemStack>) armorInventory, ItemStackTools.getEmptyStack());
			} else if (armorInventory instanceof NonNullList) {
				armor = new ItemStackList((NonNullList<ItemStack>) armorInventory, ItemStackTools.getEmptyStack());
			}
			
			if(!player.onGround || armor.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStackTools.getEmptyStack()
					&& armor.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots || player.capabilities.isFlying)
			{
				return;
			}
		}
		
		if (entityIn instanceof EntityItem) {
			EntityItem item = (EntityItem) entityIn;
			item.setAgeToCreativeDespawnTime();
			if (!entityIn.onGround) {
				entityIn.motionY = 0.1;
			}
		}
		
		if (!state.getValue(POWERED) && ConfigHandler.stopWhileSneaking && !entityIn.isSneaking() || !state.getValue(POWERED) && !ConfigHandler.stopWhileSneaking) {
			if (getSpeed() <= 0) {
				ConveyorHelper.pushEntity(entityIn, pos, getSpeed(), facing.getOpposite(), true);
			} else {
				ConveyorHelper.pushEntity(entityIn, pos, getSpeed(), facing, true);
			}
		}
	}
	
	@Override
	public boolean canSpawnInBlock() {
		return false;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(block);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target,
			World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(block);
	}
	
	@Override
	public void onWrenched(World world, IBlockState state, BlockPos pos,
			EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			world.setBlockState(pos, state.withProperty(FACING, state.getValue(FACING).rotateY()));
		} else if (player.isSneaking()) {
			if (this.getSpeed() == SimplyConveyorsBlocks.tier1Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_stair_slow_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == SimplyConveyorsBlocks.tier2Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_stair_intermediate_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == SimplyConveyorsBlocks.tier3Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_stair_fast_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == -SimplyConveyorsBlocks.tier1Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_stair_slow.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == -SimplyConveyorsBlocks.tier2Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_stair_intermediate.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == -SimplyConveyorsBlocks.tier3Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_stair_fast.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			}
		}
	}
}