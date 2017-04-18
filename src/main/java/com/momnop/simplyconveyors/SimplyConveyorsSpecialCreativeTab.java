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

public class SimplyConveyorsSpecialCreativeTab extends CreativeTabs
{

	List list;
	public static SimplyConveyorsSpecialCreativeTab INSTANCE = new SimplyConveyorsSpecialCreativeTab();

	public SimplyConveyorsSpecialCreativeTab()
	{
		super(ModInfo.MOD_ID + "_special");
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(SimplyConveyorsBlocks.conveyor_vertical_slow);
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
		
		addBlock(SimplyConveyorsBlocks.conveyor_vertical_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_vertical_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_vertical_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_stair_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_stair_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_stair_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_inverse_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_inverse_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_inverse_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_inverse_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_inverse_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_advanced_inverse_fast);
		
		addBlock(SimplyConveyorsBlocks.conveyor_modular_inverse_slow);
		addBlock(SimplyConveyorsBlocks.conveyor_modular_inverse_intermediate);
		addBlock(SimplyConveyorsBlocks.conveyor_modular_inverse_fast);
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