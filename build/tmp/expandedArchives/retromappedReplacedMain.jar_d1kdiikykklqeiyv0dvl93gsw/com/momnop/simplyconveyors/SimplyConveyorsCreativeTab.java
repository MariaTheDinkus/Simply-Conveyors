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
	public ItemStack func_151244_d() {
		return new ItemStack(SimplyConveyorsBlocks.blockSlowMovingPath);
	}

	@Override
	public Item func_78016_d() {
		return func_151244_d().func_77973_b();
	}

	@Override
	public void func_78018_a(List list) {
		this.list = list;
		
		addItem(SimplyConveyorsItems.wrench);
		
		addBlock(SimplyConveyorsBlocks.blockSlowMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastMovingPath);
		addBlock(SimplyConveyorsBlocks.blockFastestMovingPath);
		addBlock(SimplyConveyorsBlocks.blockDropperMovingPath);
		addBlock(SimplyConveyorsBlocks.blockHoldingMovingPath);
		
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
	}

	private void addItem(Item item) {
		item.func_150895_a(item, this, list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.func_149666_a(stack.func_77973_b(), this, list);
	}

}
