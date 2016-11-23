package com.momnop.simplyconveyors;

import java.util.List;
import java.util.Random;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SimplyConveyorsSpecialCreativeTab extends CreativeTabs {

	List list;
	public static SimplyConveyorsSpecialCreativeTab INSTANCE = new SimplyConveyorsSpecialCreativeTab();

	public SimplyConveyorsSpecialCreativeTab() {
		super(ModInfo.MODID + "_special");
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(SimplyConveyorsBlocks.blockBlockMovingPath);
	}

	@Override
	public Item getTabIconItem() {
		return getIconItemStack().getItem();
	}

	@Override
	public void displayAllRelevantItems(List list) {
		this.list = list;
		
		addBlock(SimplyConveyorsBlocks.blockBlockMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDetectorMovingPath);
		addBlock(SimplyConveyorsBlocks.blockGrabberMovingPath);
		addBlock(SimplyConveyorsBlocks.blockHoldingMovingPath);
		addBlock(SimplyConveyorsBlocks.blockTransporterMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDropperMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDetectorMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockDropperMovingBackwardsPath);
		//addBlock(SimplyConveyorsBlocks.blockTransporterMovingBackwardsPath);
	}

	private void addItem(Item item) {
		item.getSubItems(item, this, list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(stack.getItem(), this, list);
	}

}