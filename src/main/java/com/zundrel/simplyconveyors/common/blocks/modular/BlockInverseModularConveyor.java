package com.zundrel.simplyconveyors.common.blocks.modular;

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

import com.zundrel.simplyconveyors.SimplyConveyors;
import com.zundrel.simplyconveyors.api.interfaces.IModifier;
import com.zundrel.simplyconveyors.common.blocks.base.BlockInverseConveyor;
import com.zundrel.simplyconveyors.common.blocks.tiles.TileModularConveyor;

public class BlockInverseModularConveyor extends BlockInverseConveyor
{
	public BlockInverseModularConveyor(String unlocalizedName, double speed, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(unlocalizedName, speed, material, hardness, type, tab);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileModularConveyor();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (heldItem != null && !playerIn.isSneaking()) {
			TileModularConveyor conveyor = (TileModularConveyor) worldIn.getTileEntity(pos);
			if (heldItem.getItem() instanceof IModifier) {
				IModifier modifier = (IModifier) heldItem.getItem();
				for (int i = 0; i < 4; i++) {
					if (conveyor.getStackInSlot(i) == ItemStack.EMPTY) {
						conveyor.setInventorySlotContents(i, new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()));
						
						heldItem.shrink(1);
						break;
					}
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

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
        }
        super.breakBlock(worldIn, pos, state);
	}
}
