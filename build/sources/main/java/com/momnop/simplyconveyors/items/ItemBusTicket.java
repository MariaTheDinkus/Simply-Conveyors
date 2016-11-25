package com.momnop.simplyconveyors.items;

import java.util.List;

import mcjty.lib.compat.CompatItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.helpers.BusStopManager;

public class ItemBusTicket extends CompatItem
{
    public ItemBusTicket(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        setMaxStackSize(1);
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn,
    		List<String> tooltip, boolean advanced) {
    	tooltip.add("Admission for 1");
    	for (int i = 0; i < BusStopManager.busStopsNames.size(); i++) {
    		String name = BusStopManager.busStopsNames.get(i);
    		BlockPos pos1 = BusStopManager.busStops.get(i);
    		if (stack.getDisplayName().equals(name)) {
    			tooltip.add("Heads to the bus stop: " + name + ".");
    			tooltip.add("Found at the location: " + pos1.getX() + ", " + pos1.getY() + ", " + pos1.getZ());
    		}
    	}
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn,
    		World worldIn, EntityPlayer playerIn, EnumHand hand) {
    	if (worldIn.isRemote && playerIn.isSneaking() && hand.equals(EnumHand.MAIN_HAND)) {
			playerIn.openGui(SimplyConveyors.INSTANCE, 2, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
		}
    	return ActionResult.newResult(EnumActionResult.FAIL, itemStackIn);
    }
}