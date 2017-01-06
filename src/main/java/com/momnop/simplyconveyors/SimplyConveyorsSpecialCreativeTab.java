package com.momnop.simplyconveyors;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.info.ModInfo;

public class SimplyConveyorsSpecialCreativeTab extends CreativeTabs
{

	List list;
	public static SimplyConveyorsSpecialCreativeTab INSTANCE = new SimplyConveyorsSpecialCreativeTab();

	public SimplyConveyorsSpecialCreativeTab()
	{
		super(ModInfo.MODID + "_special");
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(SimplyConveyorsBlocks.blockBlockMovingPath);
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return getIconItemStack();
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list)
	{
		this.list = list;

		addBlock(SimplyConveyorsBlocks.blockBlockMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDetectorMovingPath);
		addBlock(SimplyConveyorsBlocks.blockGrabberMovingPath);
		addBlock(SimplyConveyorsBlocks.blockHoldingMovingPath);
		addBlock(SimplyConveyorsBlocks.blockTransporterMovingPath);
		addBlock(SimplyConveyorsBlocks.blockTrapDoorMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDropperMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDetectorMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockDropperMovingBackwardsPath);
		// addBlock(SimplyConveyorsBlocks.blockTransporterMovingBackwardsPath);
	}

	private void addItem(Item item)
	{
		item.getSubItems(item, this, (NonNullList<ItemStack>) list);
	}

	private void addBlock(Block block)
	{
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(stack.getItem(), this, (NonNullList<ItemStack>) list);
	}

}