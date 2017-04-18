package com.momnop.simplyconveyors.api;

import java.util.ArrayList;
import java.util.List;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class ItemModule extends Item implements IModule
{
	public ArrayList<String> incompatibles = new ArrayList<String>();
	private EnumModule enumModule;
	
	public ItemModule(String unlocalizedName, EnumModule enumModule)
	{
		super();
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		RenderRegistry.registry.add(this);
		this.enumModule = enumModule;
	}
	
	public EnumModule getEnumModule() {
		return enumModule;
	}

	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		
	}

	@Override
	public boolean isCompatible(ItemModule upgrade)
	{
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		isCompatible(this);
		tooltip.add("Incompatible with:");
		for (String string : incompatibles) {
			tooltip.add(string);
		}
	} 
}
