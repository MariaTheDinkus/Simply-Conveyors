package com.momnop.simplyconveyors;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

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
	public ItemStack getTabIconItem() {
		return getIconItemStack();
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		this.list = list;
		
		addItem(SimplyConveyorsItems.wrench);
		addItem(SimplyConveyorsItems.entityFilter);
		addItem(SimplyConveyorsItems.conveyorResistanceBoots);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingPath);
		
		//addBlock(SimplyConveyorsBlocks.blockSlowSpongeMovingPath);
		//addBlock(SimplyConveyorsBlocks.blockFastSpongeMovingPath);
		//addBlock(SimplyConveyorsBlocks.blockFastestSpongeMovingPath);
		
		addBlock(SimplyConveyorsBlocks.blockSlowSpikeMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastSpikeMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastestSpikeMovingPath);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingVerticalPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingVerticalPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingVerticalPath);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingBackwardsPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingBackwardsPath);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingStairPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingStairPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingStairPath);
	}

	private void addItem(Item item) {
		item.getSubItems(item, this, (NonNullList<ItemStack>) list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(stack.getItem(), this, (NonNullList<ItemStack>) list);
	}

}