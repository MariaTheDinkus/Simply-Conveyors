package com.momnop.simplyconveyors.blocks.base;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;

public class BlockFlatConveyor extends BlockPoweredConveyor
{
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	
	public double speed;
	
	public BlockFlatConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, material, hardness, type, tab);
		setDefaultState(this.getDefaultState().withProperty(LEFT, false).withProperty(RIGHT, false));
		
		this.speed = speed;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED, LEFT, RIGHT });
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		IBlockState newState = state;
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock() instanceof BlockFlatConveyor) {
			newState = newState.withProperty(LEFT, true);
		} else {
			newState = newState.withProperty(LEFT, false);
		}
		
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock() instanceof BlockFlatConveyor) {
			newState = newState.withProperty(RIGHT, true);
		} else {
			newState = newState.withProperty(RIGHT, false);
		}
		
		return newState;
	}
	
	@Override
	public boolean canSpawnInBlock()
	{
		return true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1);
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState)
	{
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		EnumFacing facing = state.getValue(FACING);
		
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if(player.inventory.player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStack.EMPTY
					&& player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots || player.capabilities.isFlying)
			{
				return;
			}
		}
		
		if (entityIn instanceof EntityItem) {
			EntityItem item = (EntityItem) entityIn;
			item.setAgeToCreativeDespawnTime();
		}
		
		if (!state.getValue(POWERED) && !entityIn.isSneaking()) {
			if (entityIn instanceof EntityItem) {
				Block block = worldIn.getBlockState(pos.add(state.getValue(FACING).getDirectionVec())).getBlock();
				if(block instanceof BlockVerticalConveyor)
				{
					entityIn.setPosition(entityIn.posX, entityIn.posY + 0.25F, entityIn.posZ);
				}
			}
			
			ConveyorHelper.pushEntity(entityIn, pos, speed, facing, true);
		}
	}
}
