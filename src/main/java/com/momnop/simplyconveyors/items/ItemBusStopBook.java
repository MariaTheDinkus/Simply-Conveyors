package com.momnop.simplyconveyors.items;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.helpers.BusStopManager;

public class ItemBusStopBook extends ItemBasic
{
	public ItemBusStopBook(String unlocalizedName)
	{
		super(unlocalizedName, 1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if(!BusStopManager.busStops.isEmpty())
		{
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Double> list1 = new ArrayList<Double>();
			ArrayList<BlockPos> list2 = new ArrayList<BlockPos>();
			for(int i = 0; i < BusStopManager.busStops.size(); i++)
			{
				BlockPos pos = BusStopManager.busStops.get(i);
				String name = BusStopManager.busStopsNames.get(pos);

				list.add(name);
				list1.add(Math.sqrt(Math.pow(pos.getX() + 0.5 - playerIn.posX, 2) + Math.pow(pos.getY() + 0.5 - playerIn.posY, 2) + Math.pow(pos.getZ() + 0.5 - playerIn.posZ, 2)));
				list2.add(pos);
			}

			int minIndex = list1.indexOf(Collections.min(list1));

			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);

			BlockPos pos = list2.get(minIndex);

			if (list1.get(minIndex) <= 750) {
				tooltip.add("The closest Bus Stop to you is " + TextFormatting.WHITE + list.get(minIndex) + TextFormatting.GRAY + " which is located " + df.format(list1.get(minIndex))
						+ " blocks away from you.");
				tooltip.add("It is located at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if(worldIn.isRemote)
		{
			playerIn.openGui(SimplyConveyors.INSTANCE, 1, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(hand));
		}
		return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(hand));
	}
}