package com.momnop.simplyconveyors.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class ItemEntityFilter extends Item
{

	public ItemEntityFilter(String unlocalizedName)
	{
		super();
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(1);
		SimplyConveyorsItems.register(this);
		RenderRegistry.registry.add(this);
	}

	public void setFilter(ItemStack stack, Class filterClass)
	{
		stack.getTagCompound().setString("filter", filterClass.getName());
	}

	public Class getFilter(ItemStack stack)
	{
		try
		{
			return Class.forName(stack.getTagCompound().getString("filter"));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("Right click on a mob to set the filter!");
		tooltip.add("Works with the Grabber Conveyor and Detector Conveyors.");
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			if(stack.hasTagCompound())
			{
				tooltip.add("Right-clicking on a compatible conveyor adds this filter's type to the filter list of the conveyor.");
			}
		}
		else
		{
			if(stack.hasTagCompound())
			{
				tooltip.add("Press " + TextFormatting.WHITE + "Shift " + TextFormatting.GRAY + "to see more information.");
			}
		}
		if(stack.hasTagCompound())
		{
			try
			{
				if (!stack.getTagCompound().getString("filter").equals("net.minecraft.entity.Entity")) {
					tooltip.add("Filter: " + TextFormatting.WHITE + Class.forName(stack.getTagCompound().getString("filter")).getSimpleName());
				} else {
					tooltip.add("Filter: " + TextFormatting.WHITE + "Empty");
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if(playerIn.isSneaking())
		{
			playerIn.getHeldItem(hand).getTagCompound().setString("filter", "net.minecraft.entity.Entity");
			return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(hand));
		}
		return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(hand));
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		initTagCompound(stack);
	}

	public void initTagCompound(ItemStack stack)
	{
		if(!stack.hasTagCompound())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("filter", "net.minecraft.entity.Entity");
			stack.setTagCompound(nbt);
		}
	}
}