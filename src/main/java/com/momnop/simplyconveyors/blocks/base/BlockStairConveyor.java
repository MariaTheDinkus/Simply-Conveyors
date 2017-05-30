package com.momnop.simplyconveyors.blocks.base;

import java.util.List;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.handlers.ConfigHandler;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.helpers.RotationUtils;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;

public class BlockStairConveyor extends BlockPoweredConveyor
{

	public BlockStairConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
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
			if(!player.onGround || player.inventory.player.inventory.armorItemInSlot(EntityEquipmentSlot.FEET.getIndex()) != ItemStackTools.getEmptyStack()
					&& player.inventory.player.inventory.armorItemInSlot(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots || player.capabilities.isFlying)
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
			ConveyorHelper.pushEntity(entityIn, pos, getSpeed(), facing, true);
		}
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState)
	{
		return false;
	}
}