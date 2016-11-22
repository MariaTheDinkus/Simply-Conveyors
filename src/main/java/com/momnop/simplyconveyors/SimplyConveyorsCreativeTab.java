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

public class SimplyConveyorsCreativeTab extends CreativeTabs {

	List list;
	public static SimplyConveyorsCreativeTab INSTANCE = new SimplyConveyorsCreativeTab();

	public SimplyConveyorsCreativeTab() {
		super(ModInfo.MODID);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(SimplyConveyorsBlocks.blockSlowMovingPath);
	}

	@Override
	public Item getTabIconItem() {
		return getIconItemStack().getItem();
	}

	@Override
	public void displayAllRelevantItems(List list) {
		this.list = list;
		
		addItem(SimplyConveyorsItems.wrench);
		addItem(SimplyConveyorsItems.entityFilter);
		//addItem(SimplyConveyorsItems.busStopBook);
		//addItem(SimplyConveyorsItems.busTicket);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDropperMovingPath);
		addBlock(SimplyConveyorsBlocks.blockHoldingMovingPath);
		addBlock(SimplyConveyorsBlocks.blockGrabberMovingPath);
		addBlock(SimplyConveyorsBlocks.blockBlockMovingPath);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingVerticalPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingVerticalPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingVerticalPath);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingStairPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingStairPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingStairPath);
		
		//addBlock(SimplyConveyorsBlocks.busMachine);
		//addBlock(SimplyConveyorsBlocks.busStop);
	}

	private void addItem(Item item) {
		item.getSubItems(item, this, list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(stack.getItem(), this, list);
	}

}