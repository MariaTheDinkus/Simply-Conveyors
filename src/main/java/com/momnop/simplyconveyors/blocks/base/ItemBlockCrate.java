package com.momnop.simplyconveyors.blocks.base;

import mcjty.lib.compat.CompatItemBlock;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;

public class ItemBlockCrate extends CompatItemBlock
{

	public ItemBlockCrate(Block block)
	{
		super(block);
	}
	
	@Override
	protected ActionResult<ItemStack> clOnItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		
		if (!worldIn.isRemote && !playerIn.isSneaking() && stack != null && stack.getItem() instanceof ItemBlock && Block.getBlockFromItem(stack.getItem()) instanceof BlockUpgradeCrate) {
			BlockUpgradeCrate crate = (BlockUpgradeCrate) Block.getBlockFromItem(stack.getItem());
			
			EntityPlayer player = (EntityPlayer) playerIn;
			
			boolean isSpecial = crate.isSpecial();
			Item item = crate.getItem();
			Block block = crate.getBlock();
			
			if(isSpecial && item != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(item, 1));
				
				player.inventory.addItemStackToInventory(new ItemStack(SimplyConveyorsBlocks.conveyor_modular_intermediate, 1));
			}
			else if(!isSpecial && block != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(block, 1));
			}
			else if(!isSpecial && item != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(item, 1));
			}
			
			player.setHeldItem(hand, new ItemStack(stack.getItem(), ItemStackTools.getStackSize(stack) - 1, stack.getMetadata()));
			
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		} else if (!worldIn.isRemote && playerIn.isSneaking() && stack != null && stack.getItem() instanceof ItemBlock && Block.getBlockFromItem(stack.getItem()) instanceof BlockUpgradeCrate) {
			BlockUpgradeCrate crate = (BlockUpgradeCrate) Block.getBlockFromItem(stack.getItem());
			
			EntityPlayer player = (EntityPlayer) playerIn;
			
			boolean isSpecial = crate.isSpecial();
			Item item = crate.getItem();
			Block block = crate.getBlock();
			
			if(isSpecial && item != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(item, ItemStackTools.getStackSize(stack)));
				
				player.inventory.addItemStackToInventory(new ItemStack(SimplyConveyorsBlocks.conveyor_modular_intermediate, ItemStackTools.getStackSize(stack)));
			}
			else if(!isSpecial && block != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(block, ItemStackTools.getStackSize(stack)));
			}
			else if(!isSpecial && item != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(item, ItemStackTools.getStackSize(stack)));
			}
			
			player.setHeldItem(hand, ItemStackTools.getEmptyStack());
			
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
}
