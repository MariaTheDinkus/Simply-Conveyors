package com.momnop.simplyconveyors.items;

import java.util.List;

import com.momnop.simplyconveyors.client.render.guis.GuiBusMachine;
import com.momnop.simplyconveyors.client.render.guis.GuiSetNameTicket;
import com.momnop.simplyconveyors.helpers.BusStopManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBusTicket extends Item
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
    	if (worldIn.isRemote && playerIn.isSneaking()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiSetNameTicket(itemStackIn));
			return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
		}
    	return ActionResult.newResult(EnumActionResult.FAIL, itemStackIn);
    }
}