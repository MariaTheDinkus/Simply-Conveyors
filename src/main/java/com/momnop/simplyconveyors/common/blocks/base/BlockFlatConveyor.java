package com.momnop.simplyconveyors.common.blocks.base;

import java.util.List;

import mcjty.lib.CompatLayer;
import mcjty.lib.tools.InventoryTools;
import mcjty.lib.tools.ItemStackList;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.items.ItemConveyorResistanceBoots;

public class BlockFlatConveyor extends BlockPoweredConveyor
{
	public static final PropertyBool BACK = PropertyBool.create("back");
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	
	public BlockFlatConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		setDefaultState(this.getDefaultState().withProperty(LEFT, false).withProperty(RIGHT, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED, BACK, LEFT, RIGHT });
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		IBlockState newState = state;
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() != Blocks.AIR) {
			newState = newState.withProperty(BACK, true);
		} else if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()).down()).getBlock() instanceof BlockRampConveyor) {
			newState = newState.withProperty(BACK, true);
		} else {
			newState = newState.withProperty(BACK, false);
		}
		
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock() instanceof BlockFlatConveyor) {
			newState = newState.withProperty(LEFT, true);
		} else if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW()).down()).getBlock() instanceof BlockRampConveyor) {
			newState = newState.withProperty(LEFT, true);
		} else {
			newState = newState.withProperty(LEFT, false);
		}
		
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock() instanceof BlockFlatConveyor) {
			newState = newState.withProperty(RIGHT, true);
		} else if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY()).down()).getBlock() instanceof BlockRampConveyor) {
			newState = newState.withProperty(RIGHT, true);
		} else {
			newState = newState.withProperty(RIGHT, false);
		}
		
		return newState;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0, 0, 0, 1, (0.25F / 16F), 1);
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
			if (entityIn instanceof EntityItem || entityIn instanceof EntityXPOrb) {
				Block block = worldIn.getBlockState(pos.add(state.getValue(FACING).getDirectionVec())).getBlock();
				if(block instanceof BlockVerticalConveyor || block instanceof BlockStairConveyor)
				{
					entityIn.setPosition(entityIn.posX, entityIn.posY + 0.3F, entityIn.posZ);
				}
			}
			
			ConveyorHelper.pushEntity(entityIn, pos, getSpeed(), facing, true);
		}
	}
	
	@Override
	public void onWrenched(World world, IBlockState state, BlockPos pos,
			EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			world.setBlockState(pos, state.withProperty(FACING, state.getValue(FACING).rotateY()));
		} else if (player.isSneaking()) {
			if (this.getSpeed() == SimplyConveyorsBlocks.tier1Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_ramp_slow_up.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == SimplyConveyorsBlocks.tier2Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_ramp_intermediate_up.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			} else if (this.getSpeed() == SimplyConveyorsBlocks.tier3Speed) {
				world.setBlockState(pos, SimplyConveyorsBlocks.conveyor_ramp_fast_up.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			}
		}
	}
}
