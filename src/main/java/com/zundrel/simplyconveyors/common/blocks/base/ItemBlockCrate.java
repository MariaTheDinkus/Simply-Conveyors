package com.zundrel.simplyconveyors.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.zundrel.simplyconveyors.common.blocks.SimplyConveyorsBlocks;

public class ItemBlockCrate extends ItemBlock
{

	public ItemBlockCrate(Block block)
	{
		super(block);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
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
			
			player.setHeldItem(hand, new ItemStack(stack.getItem(), stack.getCount() - 1, stack.getMetadata()));
			
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		} else if (!worldIn.isRemote && playerIn.isSneaking() && stack != null && stack.getItem() instanceof ItemBlock && Block.getBlockFromItem(stack.getItem()) instanceof BlockUpgradeCrate) {
			BlockUpgradeCrate crate = (BlockUpgradeCrate) Block.getBlockFromItem(stack.getItem());
			
			EntityPlayer player = (EntityPlayer) playerIn;
			
			boolean isSpecial = crate.isSpecial();
			Item item = crate.getItem();
			Block block = crate.getBlock();
			
			if(isSpecial && item != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(item, stack.getCount()));
				
				player.inventory.addItemStackToInventory(new ItemStack(SimplyConveyorsBlocks.conveyor_modular_intermediate, stack.getCount()));
			}
			else if(!isSpecial && block != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(block, stack.getCount()));
			}
			else if(!isSpecial && item != null)
			{
				player.inventory.addItemStackToInventory(new ItemStack(item, stack.getCount()));
			}
			
			player.setHeldItem(hand, ItemStack.EMPTY);
			
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
}
