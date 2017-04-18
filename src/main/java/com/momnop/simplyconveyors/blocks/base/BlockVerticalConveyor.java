package com.momnop.simplyconveyors.blocks.base;

import java.util.Random;

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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.handlers.ConfigHandler;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;
import com.momnop.simplyconveyors.utils.RotationUtils;

public class BlockVerticalConveyor extends BlockPoweredConveyor
{
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	
	public Item item;
	public Block block;
	
	public BlockVerticalConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		
		this.item = Item.getItemFromBlock(this);
		this.block = this;
	}
	
	public BlockVerticalConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, Block droppedItem, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
		
		this.item = Item.getItemFromBlock(droppedItem);
		this.block = droppedItem;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return RotationUtils.getRotatedAABB(new AxisAlignedBB(0, 0, 0, 1, 1, (0.99F)), state.getValue(FACING));
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
		return item;
	}
}
