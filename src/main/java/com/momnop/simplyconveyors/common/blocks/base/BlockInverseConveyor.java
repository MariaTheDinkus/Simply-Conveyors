package com.momnop.simplyconveyors.common.blocks.base;

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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.items.ItemConveyorResistanceBoots;

public class BlockInverseConveyor extends BlockPoweredConveyor
{
	public static final PropertyBool BACK = PropertyBool.create("back");
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	
	public BlockInverseConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
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
		} else {
			newState = newState.withProperty(BACK, false);
		}
		
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock() instanceof BlockInverseConveyor) {
			newState = newState.withProperty(LEFT, true);
		} else {
			newState = newState.withProperty(LEFT, false);
		}
		
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock() instanceof BlockInverseConveyor) {
			newState = newState.withProperty(RIGHT, true);
		} else {
			newState = newState.withProperty(RIGHT, false);
		}
		
		return newState;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0, (15F / 16F), 0, 1, 1, 1);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0, (15.85F / 16F), 0, 1, 1, 1);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		EnumFacing facing = state.getValue(FACING);
		
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if(player.inventory.player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStack.EMPTY
					&& player.inventory.player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots || player.capabilities.isFlying)
			{
				return;
			}
		}
		
		if (entityIn instanceof EntityItem) {
			EntityItem item = (EntityItem) entityIn;
			item.setAgeToCreativeDespawnTime();
		}
		
		if (!state.getValue(POWERED) && ConfigHandler.stopWhileSneaking && !entityIn.isSneaking() || !state.getValue(POWERED) && !ConfigHandler.stopWhileSneaking) {
			Block block = worldIn.getBlockState(pos.add(state.getValue(FACING).getDirectionVec())).getBlock();
			
			ConveyorHelper.pushEntityVertical(entityIn, pos, 0.3F, facing, false, true);
			ConveyorHelper.pushEntity(entityIn, pos, getSpeed(), facing, false);
		}
	}
}
