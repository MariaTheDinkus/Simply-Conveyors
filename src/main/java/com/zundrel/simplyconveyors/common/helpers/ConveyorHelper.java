package com.zundrel.simplyconveyors.common.helpers;

import javax.annotation.Nullable;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class ConveyorHelper
{
	public static void pushEntity(Entity entity, BlockPos pos, double speed, EnumFacing facing, boolean center)
	{
		entity.motionX += speed * facing.getFrontOffsetX();
		entity.motionZ += speed * facing.getFrontOffsetZ();

		if(speed * facing.getFrontOffsetX() > 0)
		{
			if(center)
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

			if(entity.motionX > speed)
			{
				entity.motionX = speed;
			}
		}
		else if(speed * facing.getFrontOffsetX() < 0)
		{
			if(center)
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

			if(entity.motionX < -speed)
			{
				entity.motionX = -speed;
			}
		}

		if(speed * facing.getFrontOffsetZ() > 0)
		{
			if(center)
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

			if(entity.motionZ > speed)
			{
				entity.motionZ = speed;
			}
		}
		else if(speed * facing.getFrontOffsetZ() < 0)
		{
			if(center)
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

			if(entity.motionZ < -speed)
			{
				entity.motionZ = -speed;
			}
		}
	}
	
	public static void centerEntity(Entity entity, BlockPos pos)
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

	public static void pushEntityVertical(Entity entity, BlockPos pos, double speed, EnumFacing facing, boolean vertical, boolean center)
	{
		entity.motionY = 0;

		entity.motionY += speed;

		if(vertical)
		{
			entity.motionX += 0.1F * facing.getFrontOffsetX();
			entity.motionZ += 0.1F * facing.getFrontOffsetZ();

			if(entity.motionX > 0.1F)
			{
				entity.motionX = 0.1F;
			}

			if(entity.motionZ > 0.1F)
			{
				entity.motionZ = 0.1F;
			}
		}

		if(speed > 0)
		{
			if(entity.motionY > speed)
			{
				entity.motionY = speed;
			}
		}
		else if(speed < 0)
		{
			if(entity.motionY < speed)
			{
				entity.motionY = speed;
			}
		}

		if(speed * facing.getFrontOffsetX() > 0)
		{
			if(center)
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

				if(entity.motionX > speed)
				{
					entity.motionX = speed;
				}
			}
		}
		else if(speed * facing.getFrontOffsetX() < 0)
		{
			if(center)
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

				if(entity.motionX < -speed)
				{
					entity.motionX = -speed;
				}
			}
		}

		if(speed * facing.getFrontOffsetZ() > 0)
		{
			if(center)
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

				if(entity.motionZ > speed)
				{
					entity.motionZ = speed;
				}
			}
		}
		else if(speed * facing.getFrontOffsetZ() < 0)
		{
			if(center)
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

				if(entity.motionZ < -speed)
				{
					entity.motionZ = -speed;
				}
			}
		}
	}
	
	public static void speedupPlayer(World world, Entity entity) {
		double velocity = Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);

		if (!(entity instanceof EntityPlayerSP))
			return;

		EntityPlayerSP player = (EntityPlayerSP) entity;

		if (Math.abs(player.movementInput.moveForward) < 0.75f && Math.abs(player.movementInput.moveStrafe) < 0.75f)
			return;

		if (player.movementInput.moveForward >= 0.75F) {
			player.moveRelative(0, 1, 0.075F, 1);
		}
		
		if (player.movementInput.moveStrafe >= 0.75F) {
			player.moveRelative(1, 0, 0.075F, 1);
		}
		
		if (player.movementInput.moveForward <= -0.75F) {
			player.moveRelative(0, 1, -0.075F, 1);
		}
		
		if (player.movementInput.moveStrafe <= -0.75F) {
			player.moveRelative(1, 0, -0.075F, 1);
		}
	}
	
	public static void insert(EnumFacing facing, EnumFacing inventory, BlockPos pos, Entity entity) {
		TileEntity inventoryTile;
		EnumFacing inventoryDir = facing;
		
		World world = entity.getEntityWorld();

		double distX = Math.abs(pos.offset(facing).getX() + .5 - entity.posX);
		double distZ = Math.abs(pos.offset(facing).getZ() + .5 - entity.posZ);
		double treshold = .9;
		boolean contact = facing.getAxis() == Axis.Z ? distZ < treshold : distX < treshold;

		inventoryTile = world.getTileEntity(pos.offset(inventory));
		contact = Math.abs(facing.getAxis() == Axis.Z ? (pos.getZ() + .5 - entity.posZ) : (pos.getX() + .5 - entity.posX)) < .2;
		inventoryDir = EnumFacing.DOWN;

		if(!world.isRemote && inventoryTile instanceof IInventory)
		{
			if(contact && inventoryTile != null)
			{
				ItemStack stack = ((EntityItem) entity).getItem();
				if(stack != ItemStack.EMPTY)
				{
					if(TileEntityFurnace.isItemFuel(stack))
					{
						ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, EnumFacing.DOWN);
						if(ret == ItemStack.EMPTY)
							entity.setDead();
						else if(ret.getCount() < stack.getCount())
							((EntityItem) entity).setItem(ret);
					}
					else if(!TileEntityFurnace.isItemFuel(stack))
					{
						ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, null);
						if(ret == ItemStack.EMPTY)
							entity.setDead();
						else if(ret.getCount() < stack.getCount())
							((EntityItem) entity).setItem(ret);
					}
				}
			}
			else if(contact && world.isAirBlock(pos.offset(inventory)))
			{
				entity.motionX = 0;
				entity.motionZ = 0;
				entity.setPosition(pos.getX() + .5, pos.getY() - .5, pos.getZ() + .5);
			}
		}
	}
	
	public static void insertToFacing(EnumFacing facing, EnumFacing inventory, BlockPos pos, Entity entity) {
		TileEntity inventoryTile;
		EnumFacing inventoryDir = facing;
		
		World world = entity.getEntityWorld();

		double distX = Math.abs(pos.offset(facing).getX() + .5 - entity.posX);
		double distZ = Math.abs(pos.offset(facing).getZ() + .5 - entity.posZ);
		double treshold = .9;
		boolean contact = facing.getAxis() == Axis.Z ? distZ < treshold : distX < treshold;

		inventoryTile = world.getTileEntity(pos.offset(facing));
		contact = Math.abs(facing.getAxis() == Axis.Z ? (pos.getZ() + .5 - entity.posZ) : (pos.getX() + .5 - entity.posX)) < .2;
		inventoryDir = EnumFacing.UP;

		if(!world.isRemote && inventoryTile instanceof IInventory)
		{
			if(inventoryTile != null)
			{
				ItemStack stack = ((EntityItem) entity).getItem();
				if(stack != ItemStack.EMPTY)
				{
					if(TileEntityFurnace.isItemFuel(stack))
					{
						ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, EnumFacing.DOWN);
						if(ret == ItemStack.EMPTY)
							entity.setDead();
						else if(ret.getCount() < stack.getCount())
							((EntityItem) entity).setItem(ret);
					}
					else if(!TileEntityFurnace.isItemFuel(stack))
					{
						ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, null);
						if(ret == ItemStack.EMPTY)
							entity.setDead();
						else if(ret.getCount() < stack.getCount())
							((EntityItem) entity).setItem(ret);
					}
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
     * Insert the specified stack to the specified inventory and return any leftover items
     */
    private static ItemStack insertStack(IInventory inventoryIn, ItemStack index, int side, EnumFacing p_174916_4_)
    {
        ItemStack itemstack = inventoryIn.getStackInSlot(side);

        if (canInsertItemInSlot(inventoryIn, index, side, p_174916_4_))
        {
            boolean flag = false;
            boolean flag1 = true;
			for (int i = 0; i < inventoryIn.getSizeInventory(); i++) {
				if (inventoryIn.getStackInSlot(i) != ItemStack.EMPTY) {
					flag1 = false;
					break;
				}
			}

            if (itemstack == ItemStack.EMPTY)
            {
                inventoryIn.setInventorySlotContents(side, index);
                index = ItemStack.EMPTY;
                flag = true;
            }
            else if (canCombine(itemstack, index))
            {
                int i = index.getMaxStackSize() - itemstack.getCount();
                int j = Math.min(itemstack.getCount(), i);
                index.shrink(1);
                itemstack.grow(1);
                flag = j > 0;
            }

            if (flag)
            {
                if (flag1 && inventoryIn instanceof TileEntityHopper)
                {
                    TileEntityHopper tileentityhopper1 = (TileEntityHopper)inventoryIn;

                    if (!tileentityhopper1.mayTransfer())
                    {
                        int k = 0;

                        if (inventoryIn != null && inventoryIn instanceof TileEntityHopper)
                        {
                            TileEntityHopper tileentityhopper = (TileEntityHopper)inventoryIn;

                            if (tileentityhopper1.mayTransfer())
                            {
                                k = 1;
                            }
                        }

                        tileentityhopper1.setTransferCooldown(8 - k);
                    }
                }

                inventoryIn.markDirty();
            }
        }

        return index;
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
			ItemStack itemstack = itemIn.getItem().copy();
			ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemstack, (EnumFacing) null);

			if(itemstack1 != ItemStack.EMPTY && itemstack1.getCount() != 0)
			{
				itemIn.setItem(itemstack1);
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
