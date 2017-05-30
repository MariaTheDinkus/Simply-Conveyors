package com.momnop.simplyconveyors.blocks.modular;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.api.ItemTrack;
import com.momnop.simplyconveyors.blocks.base.BlockFlatConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileModularConveyor;

public class BlockFlatModularConveyor extends BlockFlatConveyor implements ITileEntityProvider
{

	public BlockFlatModularConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileModularConveyor();
	}

	@Override
	public boolean clOnBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (heldItem != null && !playerIn.isSneaking()) {
			TileModularConveyor conveyor = (TileModularConveyor) worldIn.getTileEntity(pos);
			if (heldItem.getItem() instanceof ItemModule) {
				for (int i = 0; i < 3; i++) {
					if (conveyor.getStackInSlot(i) == ItemStackTools.getEmptyStack()) {
						for (int i2 = 0; i2 < 3; i2++) {
							if (conveyor.getStackInSlot(i2) != ItemStackTools.getEmptyStack() && conveyor.getStackInSlot(i2).getItem() instanceof ItemModule) {
								ItemModule moduleInHand = (ItemModule) heldItem.getItem();
								ItemModule moduleInSlot = (ItemModule) conveyor.getStackInSlot(i2).getItem();
								
								if (!moduleInHand.isCompatible(moduleInSlot) || moduleInHand == moduleInSlot) {
									return false;
								}
							}
						}
						
						conveyor.setInventorySlotContents(i, new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()));
						
						ItemStackTools.setStackSize(heldItem, ItemStackTools.getStackSize(heldItem) - 1);
						break;
					}
				}
			}
			
			if (heldItem.getItem() instanceof ItemTrack) {
				if (conveyor.getStackInSlot(3) == ItemStackTools.getEmptyStack()) {
					for (int i2 = 0; i2 < 3; i2++) {
						if (conveyor.getStackInSlot(i2) != ItemStackTools.getEmptyStack() && conveyor.getStackInSlot(i2).getItem() instanceof ItemModule) {
							ItemModule moduleInHand = (ItemModule) heldItem.getItem();
							ItemModule moduleInSlot = (ItemModule) conveyor.getStackInSlot(i2).getItem();
							
							if (!moduleInHand.isCompatible(moduleInSlot) || moduleInHand == moduleInSlot) {
								return false;
							}
						}
					}
					
					conveyor.setInventorySlotContents(3, new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()));
					
					ItemStackTools.setStackSize(heldItem, ItemStackTools.getStackSize(heldItem) - 1);
				}
			}
		}
		
		if (!worldIn.isRemote && playerIn.isSneaking()) {
			playerIn.openGui(SimplyConveyors.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if(tileentity instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
		}
		super.breakBlock(worldIn, pos, state);
	}
}
