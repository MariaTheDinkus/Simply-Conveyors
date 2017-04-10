package com.momnop.simplyconveyors.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.helpers.BusStopManager;

public class ItemBusTicket extends ItemBasic
{
	public ItemBusTicket(String unlocalizedName)
	{
		super(unlocalizedName, 1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("Admission for 1");
		for(int i = 0; i < BusStopManager.busStopsNames.values().size(); i++)
		{
			BlockPos pos1 = BusStopManager.busStops.get(i);
			String name = BusStopManager.busStopsNames.get(pos1);
			if(stack.getDisplayName().equals(name))
			{
				tooltip.add("Heads to the bus stop: " + name + ".");
				tooltip.add("Found at the location: " + pos1.getX() + ", " + pos1.getY() + ", " + pos1.getZ());
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if(worldIn.isRemote && playerIn.isSneaking() && hand.equals(EnumHand.MAIN_HAND))
		{
			playerIn.openGui(SimplyConveyors.INSTANCE, 3, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(hand));
		}
		return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(hand));
	}
}