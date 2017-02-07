package com.momnop.simplyconveyors;

import java.util.List;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SimplyConveyorsCreativeTab extends CreativeTabs
{

	List list;
	public static SimplyConveyorsCreativeTab INSTANCE = new SimplyConveyorsCreativeTab();

	public SimplyConveyorsCreativeTab()
	{
		super(ModInfo.MOD_ID);
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(SimplyConveyorsBlocks.conveyor_slow);
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

		addItem(SimplyConveyorsItems.wrench);
		
		addItem(SimplyConveyorsItems.entityFilter);
		
		addItem(SimplyConveyorsItems.conveyorResistanceBoots);
		
		addItem(SimplyConveyorsItems.roller);
		
		addItem(SimplyConveyorsItems.dropper_module);
		
		addItem(SimplyConveyorsItems.sponge_module);
		
		addBlock(SimplyConveyorsBlocks.conveyor_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_vertical_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_vertical_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_vertical_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_inverse_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_inverse_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_inverse_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_inverse_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_inverse_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_inverse_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_modular);
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