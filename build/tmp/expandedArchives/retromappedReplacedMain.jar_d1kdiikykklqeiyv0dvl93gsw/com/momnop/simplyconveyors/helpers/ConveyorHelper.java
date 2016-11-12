package com.momnop.simplyconveyors.helpers;

import javax.annotation.Nullable;

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

public class ConveyorHelper {
	public static double getEntityPosFromFacing(boolean inverse, BlockPos pos, Entity entity, EnumFacing enumFacing) {
    	if (enumFacing == EnumFacing.SOUTH && inverse == false || enumFacing == EnumFacing.NORTH && inverse == false || enumFacing == EnumFacing.WEST && inverse == true || enumFacing == EnumFacing.EAST && inverse == true) {
    		return entity.field_70165_t;
    	} else if (enumFacing == EnumFacing.SOUTH && inverse == true || enumFacing == EnumFacing.NORTH && inverse == true || enumFacing == EnumFacing.WEST && inverse == false || enumFacing == EnumFacing.EAST && inverse == false) {
    		return entity.field_70161_v;
    	}
    	return 0;
    }
    
    public static void centerBasedOnFacing(boolean additive, BlockPos pos, Entity entity, EnumFacing enumFacing) {
    	if (additive == true) {
    		if (getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.field_70165_t) {
    			if (entity.field_70165_t > pos.func_177958_n() + .55) {
    				entity.field_70159_w += -0.1F;
    			} else if (entity.field_70165_t < pos.func_177958_n() + .45) {
    				entity.field_70159_w += 0.1F;
    			} else {
    				entity.field_70159_w = 0;
    			}
    		} else if (getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.field_70161_v) {
    			if (entity.field_70161_v > pos.func_177952_p() + .55) {
    				entity.field_70179_y += -0.1F;
    			} else if (entity.field_70161_v < pos.func_177952_p() + .45) {
    				entity.field_70179_y += 0.1F;
    			} else {
    				entity.field_70179_y = 0;
    			}
    		}
    	} else if (additive == false) {
    		if (getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.field_70165_t) {
    			if (entity.field_70165_t > pos.func_177958_n() + .55) {
    				entity.field_70159_w = -0.1F;
    			} else if (entity.field_70165_t < pos.func_177958_n() + .45) {
    				entity.field_70159_w = 0.1F;
    			} else {
    				entity.field_70159_w = 0;
    			}
    		} else if (getEntityPosFromFacing(false, pos, entity, enumFacing) == entity.field_70161_v) {
    			if (entity.field_70161_v > pos.func_177952_p() + .55) {
    				entity.field_70179_y = -0.1F;
    			} else if (entity.field_70161_v < pos.func_177952_p() + .45) {
    				entity.field_70179_y = 0.1F;
    			} else {
    				entity.field_70179_y = 0;
    			}
    		}
    	}
    }
    
    public static void lockSpeed(boolean z, double speed, Entity entity, EnumFacing direction) {
    	if (z == true) {
    		if (speed * direction.func_82599_e() > 0) {
    			if (entity.field_70179_y > speed) {
    				entity.field_70179_y = speed;
    			}
    		} else if (speed * direction.func_82599_e() < 0) {
				if (entity.field_70179_y < -speed) {
					entity.field_70179_y = -speed;
				}
			}
    	} else if (z == false) {
    		if (speed * direction.func_82601_c() > 0) {
    			if (entity.field_70159_w > speed) {
    				entity.field_70159_w = speed;
    			}
    		} else if (speed * direction.func_82601_c() < 0) {
				if (entity.field_70159_w < -speed) {
					entity.field_70159_w = -speed;
				}
			}
    	}
    }
    
    public static boolean canInsertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side)
	{
		if(stack!=null && inventory!=null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side))
		{
			IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			ItemStack temp = ItemHandlerHelper.insertItem(handler, stack.func_77946_l(), true);
			if(temp==null || temp.field_77994_a<stack.field_77994_a)
				return true;
		}
		return false;
	}
	public static ItemStack insertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side)
	{
		if(stack!=null && inventory!=null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side))
		{
			IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			ItemStack temp = ItemHandlerHelper.insertItem(handler, stack.func_77946_l(), true);
			if(temp==null || temp.field_77994_a<stack.field_77994_a)
				return ItemHandlerHelper.insertItem(handler, stack, false);
		}
		return stack;
	}
	public static ItemStack insertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side, boolean simulate)
	{
		if(inventory!=null && stack!=null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side))
		{
			IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			return ItemHandlerHelper.insertItem(handler, stack.func_77946_l(), simulate);
		}
		return stack;
	}
	
	private static boolean canCombine(ItemStack stack1, ItemStack stack2)
    {
        return stack1.func_77973_b() != stack2.func_77973_b() ? false : (stack1.func_77960_j() != stack2.func_77960_j() ? false : (stack1.field_77994_a > stack1.func_77976_d() ? false : ItemStack.func_77970_a(stack1, stack2)));
    }
	
	/**
     * Can this hopper insert the specified item from the specified slot on the specified side?
     */
    private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side)
    {
        return !inventoryIn.func_94041_b(index, stack) ? false : !(inventoryIn instanceof ISidedInventory) || ((ISidedInventory)inventoryIn).func_180462_a(index, stack, side);
    }
	
	/**
     * Insert the specified stack to the specified inventory and return any leftover items
     */
    private static ItemStack insertStack(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side)
    {
        ItemStack itemstack = inventoryIn.func_70301_a(index);

        if (canInsertItemInSlot(inventoryIn, stack, index, side))
        {
            boolean flag = false;

            if (itemstack == null)
            {
                //Forge: BUGFIX: Again, make things respect max stack sizes.
                int max = Math.min(stack.func_77976_d(), inventoryIn.func_70297_j_());
                if (max >= stack.field_77994_a)
                {
                inventoryIn.func_70299_a(index, stack);
                stack = null;
                }
                else
                {
                    inventoryIn.func_70299_a(index, stack.func_77979_a(max));
                }
                flag = true;
            }
            else if (canCombine(itemstack, stack))
            {
                //Forge: BUGFIX: Again, make things respect max stack sizes.
                int max = Math.min(stack.func_77976_d(), inventoryIn.func_70297_j_());
                if (max > itemstack.field_77994_a)
                {
                int i = max - itemstack.field_77994_a;
                int j = Math.min(stack.field_77994_a, i);
                stack.field_77994_a -= j;
                itemstack.field_77994_a += j;
                flag = j > 0;
                }
            }

            if (flag)
            {
                if (inventoryIn instanceof TileEntityHopper)
                {
                    TileEntityHopper tileentityhopper = (TileEntityHopper)inventoryIn;

                    if (tileentityhopper.func_174914_o())
                    {
                        tileentityhopper.func_145896_c(8);
                    }

                    inventoryIn.func_70296_d();
                }

                inventoryIn.func_70296_d();
            }
        }

        return stack;
    }
	
	/**
     * Attempts to place the passed stack in the inventory, using as many slots as required. Returns leftover items
     */
    public static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, ItemStack stack, @Nullable EnumFacing side)
    {
        if (inventoryIn instanceof ISidedInventory && side != null)
        {
            ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
            int[] aint = isidedinventory.func_180463_a(side);

            for (int k = 0; k < aint.length && stack != null && stack.field_77994_a > 0; ++k)
            {
                stack = insertStack(inventoryIn, stack, aint[k], side);
            }
        }
        else
        {
            int i = inventoryIn.func_70302_i_();

            for (int j = 0; j < i && stack != null && stack.field_77994_a > 0; ++j)
            {
                stack = insertStack(inventoryIn, stack, j, side);
            }
        }

        if (stack != null && stack.field_77994_a == 0)
        {
            stack = null;
        }

        return stack;
    }
    
    public static boolean putDropInInventoryAllSlots(IInventory p_145898_0_, EntityItem itemIn)
    {
        boolean flag = false;

        if (itemIn == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = itemIn.func_92059_d().func_77946_l();
            ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemstack, (EnumFacing)null);

            if (itemstack1 != null && itemstack1.field_77994_a != 0)
            {
                itemIn.func_92058_a(itemstack1);
            }
            else
            {
                flag = true;
                itemIn.func_70106_y();
            }

            return flag;
        }
    }
}
