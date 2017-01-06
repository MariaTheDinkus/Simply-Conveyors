package com.momnop.simplyconveyors.helpers;

import javax.annotation.Nullable;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class ConveyorHelper
{
	public static double getEntityPosFromFacing(boolean inverse, BlockPos pos, Entity entity, EnumFacing enumFacing)
	{
		if(enumFacing == EnumFacing.SOUTH && inverse == false || enumFacing == EnumFacing.NORTH && inverse == false || enumFacing == EnumFacing.WEST && inverse == true
				|| enumFacing == EnumFacing.EAST && inverse == true)
		{
			return entity.posX;
		}
		else if(enumFacing == EnumFacing.SOUTH && inverse == true || enumFacing == EnumFacing.NORTH && inverse == true || enumFacing == EnumFacing.WEST && inverse == false
				|| enumFacing == EnumFacing.EAST && inverse == false)
		{
			return entity.posZ;
		}
		return 0;
	}

	public static void centerBasedOnFacing(boolean additive, BlockPos pos, Entity entity, EnumFacing enumFacing)
	{
		if(additive == true)
		{
			if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posX)
			{
				if(entity.posX > pos.getX() + .55)
				{
					entity.motionX += -0.1F;
				}
				else if(entity.posX < pos.getX() + .45)
				{
					entity.motionX += 0.1F;
				}
				else
				{
					entity.motionX = 0;
				}
			}
			else if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posZ)
			{
				if(entity.posZ > pos.getZ() + .55)
				{
					entity.motionZ += -0.1F;
				}
				else if(entity.posZ < pos.getZ() + .45)
				{
					entity.motionZ += 0.1F;
				}
				else
				{
					entity.motionZ = 0;
				}
			}
		}
		else if(additive == false)
		{
			if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posX)
			{
				if(entity.posX > pos.getX() + .55)
				{
					entity.motionX = -0.1F;
				}
				else if(entity.posX < pos.getX() + .45)
				{
					entity.motionX = 0.1F;
				}
				else
				{
					entity.motionX = 0;
				}
			}
			else if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posZ)
			{
				if(entity.posZ > pos.getZ() + .55)
				{
					entity.motionZ = -0.1F;
				}
				else if(entity.posZ < pos.getZ() + .45)
				{
					entity.motionZ = 0.1F;
				}
				else
				{
					entity.motionZ = 0;
				}
			}
		}
	}

	public static void centerBasedOnFacing(double speed, boolean additive, BlockPos pos, Entity entity, EnumFacing enumFacing)
	{
		if(additive == true)
		{
			if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posX)
			{
				if(entity.posX > pos.getX() + .55)
				{
					entity.motionX += -speed;
				}
				else if(entity.posX < pos.getX() + .45)
				{
					entity.motionX += speed;
				}
				else
				{
					entity.motionX = 0;
				}
			}
			else if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posZ)
			{
				if(entity.posZ > pos.getZ() + .55)
				{
					entity.motionZ += -speed;
				}
				else if(entity.posZ < pos.getZ() + .45)
				{
					entity.motionZ += speed;
				}
				else
				{
					entity.motionZ = 0;
				}
			}
		}
		else if(additive == false)
		{
			if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posX)
			{
				if(entity.posX > pos.getX() + .55)
				{
					entity.motionX = -speed;
				}
				else if(entity.posX < pos.getX() + .45)
				{
					entity.motionX = speed;
				}
				else
				{
					entity.motionX = 0;
				}
			}
			else if(getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.posZ)
			{
				if(entity.posZ > pos.getZ() + .55)
				{
					entity.motionZ = -speed;
				}
				else if(entity.posZ < pos.getZ() + .45)
				{
					entity.motionZ = speed;
				}
				else
				{
					entity.motionZ = 0;
				}
			}
		}
	}

	public static void lockSpeed(boolean z, double speed, Entity entity, EnumFacing direction)
	{
		if(z == true)
		{
			if(speed * direction.getFrontOffsetZ() > 0)
			{
				if(entity.motionZ > speed)
				{
					entity.motionZ = speed;
				}
			}
			else if(speed * direction.getFrontOffsetZ() < 0)
			{
				if(entity.motionZ < -speed)
				{
					entity.motionZ = -speed;
				}
			}
		}
		else if(z == false)
		{
			if(speed * direction.getFrontOffsetX() > 0)
			{
				if(entity.motionX > speed)
				{
					entity.motionX = speed;
				}
			}
			else if(speed * direction.getFrontOffsetX() < 0)
			{
				if(entity.motionX < -speed)
				{
					entity.motionX = -speed;
				}
			}
		}
	}

	public static boolean canInsertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side)
	{
		if(stack != ItemStack.EMPTY && inventory != null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side))
		{
			IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			ItemStack temp = ItemHandlerHelper.insertItem(handler, stack.copy(), true);
			if(temp == ItemStack.EMPTY || temp.getCount() < stack.getCount())
				return true;
		}
		return false;
	}

	public static ItemStack insertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side)
	{
		if(stack != ItemStack.EMPTY && inventory != null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side))
		{
			IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			ItemStack temp = ItemHandlerHelper.insertItem(handler, stack.copy(), true);
			if(temp == ItemStack.EMPTY || temp.getCount() < stack.getCount())
				return ItemHandlerHelper.insertItem(handler, stack, false);
		}
		return stack;
	}

	public static ItemStack insertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side, boolean simulate)
	{
		if(inventory != null && stack != ItemStack.EMPTY && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side))
		{
			IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			return ItemHandlerHelper.insertItem(handler, stack.copy(), simulate);
		}
		return stack;
	}

	private static boolean canCombine(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() != stack2.getItem() ? false : (stack1.getMetadata() != stack2.getMetadata() ? false : (stack1.getCount() > stack1.getMaxStackSize() ? false : ItemStack
				.areItemStackTagsEqual(stack1, stack2)));
	}

	/**
	 * Can this hopper insert the specified item from the specified slot on the
	 * specified side?
	 */
	private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side)
	{
		return !inventoryIn.isItemValidForSlot(index, stack) ? false : !(inventoryIn instanceof ISidedInventory) || ((ISidedInventory) inventoryIn).canInsertItem(index, stack, side);
	}

	/**
	 * Insert the specified stack to the specified inventory and return any
	 * leftover items
	 */
	private static ItemStack insertStack(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side)
	{
		ItemStack itemstack = inventoryIn.getStackInSlot(index);

		if(canInsertItemInSlot(inventoryIn, stack, index, side))
		{
			boolean flag = false;

			if(itemstack == ItemStack.EMPTY)
			{
				// Forge: BUGFIX: Again, make things respect max stack sizes.
				int max = Math.min(stack.getMaxStackSize(), inventoryIn.getInventoryStackLimit());
				if(max >= stack.getCount())
				{
					inventoryIn.setInventorySlotContents(index, stack);
					stack = ItemStack.EMPTY;
				}
				else
				{
					inventoryIn.setInventorySlotContents(index, stack.splitStack(max));
				}
				flag = true;
			}
			else if(canCombine(itemstack, stack))
			{
				// Forge: BUGFIX: Again, make things respect max stack sizes.
				int max = Math.min(stack.getMaxStackSize(), inventoryIn.getInventoryStackLimit());
				if(max > itemstack.getCount())
				{
					int i = max - itemstack.getCount();
					int j = Math.min(stack.getCount(), i);
					stack.setCount(stack.getCount() - j);
					stack.setCount(itemstack.getCount() + j);
					flag = j > 0;
				}
			}

			if(flag)
			{
				if(inventoryIn instanceof TileEntityHopper)
				{
					TileEntityHopper tileentityhopper = (TileEntityHopper) inventoryIn;

					if(tileentityhopper.mayTransfer())
					{
						tileentityhopper.setTransferCooldown(8);
					}

					inventoryIn.markDirty();
				}

				inventoryIn.markDirty();
			}
		}

		return stack;
	}

	/**
	 * Attempts to place the passed stack in the inventory, using as many slots
	 * as required. Returns leftover items
	 */
	public static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, ItemStack stack, @Nullable EnumFacing side)
	{
		if(inventoryIn instanceof ISidedInventory && side != null)
		{
			ISidedInventory isidedinventory = (ISidedInventory) inventoryIn;
			int[] aint = isidedinventory.getSlotsForFace(side);

			for(int k = 0; k < aint.length && stack != ItemStack.EMPTY && stack.getCount() > 0; ++k)
			{
				stack = insertStack(inventoryIn, stack, aint[k], side);
			}
		}
		else
		{
			int i = inventoryIn.getSizeInventory();

			for(int j = 0; j < i && stack != ItemStack.EMPTY && stack.getCount() > 0; ++j)
			{
				stack = insertStack(inventoryIn, stack, j, side);
			}
		}

		if(stack != ItemStack.EMPTY && stack.getCount() == 0)
		{
			stack = ItemStack.EMPTY;
		}

		return stack;
	}

	public static boolean putDropInInventoryAllSlots(IInventory p_145898_0_, EntityItem itemIn)
	{
		boolean flag = false;

		if(itemIn == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = itemIn.getEntityItem().copy();
			ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemstack, (EnumFacing) null);

			if(itemstack1 != ItemStack.EMPTY && itemstack1.getCount() != 0)
			{
				itemIn.setEntityItemStack(itemstack1);
			}
			else
			{
				flag = true;
				itemIn.setDead();
			}

			return flag;
		}
	}
}
