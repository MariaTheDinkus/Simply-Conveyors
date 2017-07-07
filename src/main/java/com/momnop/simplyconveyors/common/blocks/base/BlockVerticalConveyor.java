package com.momnop.simplyconveyors.common.blocks.base;

import java.util.ArrayList;
import java.util.Random;

import mcjty.lib.tools.ItemStackList;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.helpers.RotationUtils;
import com.momnop.simplyconveyors.common.items.ItemConveyorResistanceBoots;

public class BlockVerticalConveyor extends BlockPoweredConveyor
{
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	
	public Block block;
	
	public BlockVerticalConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		
		this.block = this;
	}
	
	public BlockVerticalConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, Block droppedItem, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		
		this.block = droppedItem;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return RotationUtils.getRotatedAABB(new AxisAlignedBB(0, 0, 0, 1, 1, (0.99F)), state.getValue(FACING));
	}
	
	@Override
	public boolean canSpawnInBlock() {
		return false;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(block);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		EnumFacing facing = state.getValue(FACING);
		
		if (this.getSpeed() < 0) {
			entityIn.fallDistance = 0;
		}
		
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			ItemStackList armor = new ItemStackList(player.inventory.armorInventory, ItemStackTools.getEmptyStack());
			if(armor.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStackTools.getEmptyStack()
					&& armor.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots || player.capabilities.isFlying)
			{
				return;
			}
		}
		
		if (entityIn instanceof EntityItem) {
			EntityItem item = (EntityItem) entityIn;
			item.setAgeToCreativeDespawnTime();
		}
		
		if (!state.getValue(POWERED) && ConfigHandler.stopWhileSneaking && !entityIn.isSneaking() || !state.getValue(POWERED) && !ConfigHandler.stopWhileSneaking) {
			if (this.getSpeed() < 0 && worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() instanceof BlockFlatConveyor) {
				Vec3i directionVec = facing.getOpposite().getDirectionVec();
				entityIn.setPosition(entityIn.posX + (directionVec.getX() * 0.1F), entityIn.posY + (directionVec.getY() * 0.1F), entityIn.posZ + (directionVec.getZ() * 0.1F));
			} else {
				ConveyorHelper.pushEntityVertical(entityIn, pos, this.getSpeed(), facing, true, true);
			}
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(block);
	}
	
	@Override
	public void onWrenched(World world, IBlockState state, BlockPos pos,
			EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			world.setBlockState(pos, state.withProperty(FACING, state.getValue(FACING).rotateY()));
		} else if (player.isSneaking()) {
			if (this.getSpeed() == SimplyConveyorsBlocks.tier1Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_vertical_slow_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == SimplyConveyorsBlocks.tier2Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_vertical_intermediate_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == SimplyConveyorsBlocks.tier3Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_vertical_fast_down.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == -SimplyConveyorsBlocks.tier1Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_vertical_slow.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == -SimplyConveyorsBlocks.tier2Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_vertical_intermediate.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == -SimplyConveyorsBlocks.tier3Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_vertical_fast.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			}
		}
	}
}
