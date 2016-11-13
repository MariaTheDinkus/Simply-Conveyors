package com.momnop.simplyconveyors.items;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.client.render.guis.GuiBusMachine;
import com.momnop.simplyconveyors.helpers.BusStopManager;

public class ItemBusStopBook extends Item
{
    public ItemBusStopBook(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        setMaxStackSize(1);
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn,
    		List<String> tooltip, boolean advanced) {
    	if (!BusStopManager.busStops.isEmpty()) {
    		ArrayList<String> list = new ArrayList<String>();
        	ArrayList<Double> list1 = new ArrayList<Double>();
        	ArrayList<BlockPos> list2 = new ArrayList<BlockPos>();
        	for (int i = 0; i < BusStopManager.busStopsNames.size(); i++) {
        		String name = BusStopManager.busStopsNames.get(i);
        		BlockPos pos = BusStopManager.busStops.get(i);
        		
        		list.add(name);
        		list1.add(Math.sqrt(Math.pow(pos.getX() - playerIn.posX, 2) + Math.pow(pos.getY() - playerIn.posY, 2) + Math.pow(pos.getZ() - playerIn.posZ, 2)));
        		list2.add(pos);
        	}
        	
        	int minIndex = list1.indexOf(Collections.min(list1));
        	
        	DecimalFormat df = new DecimalFormat("#.##");
        	df.setRoundingMode(RoundingMode.CEILING);
        	
        	BlockPos pos = list2.get(minIndex);
        	
        	tooltip.add("The closest Bus Stop to you is " + TextFormatting.WHITE + list.get(minIndex) + TextFormatting.GRAY + " which is located " + df.format(list1.get(minIndex)) + " blocks away from you.");
        	tooltip.add("It is located at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    	}
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn,
    		World worldIn, EntityPlayer playerIn, EnumHand hand) {
    	if (worldIn.isRemote) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiBusMachine());
			return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
		}
    	return ActionResult.newResult(EnumActionResult.FAIL, itemStackIn);
    }
}