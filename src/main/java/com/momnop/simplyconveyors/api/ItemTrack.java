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

public class ItemTrack extends Item implements ITrack
{
	public ArrayList<String> incompatibles = new ArrayList<String>();
	private EnumTrack enumTrack;
	
	public ItemTrack(String unlocalizedName, EnumTrack enumTrack)
	{
		super();
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		RenderRegistry.registry.add(this);
		this.enumTrack = enumTrack;
	}
	
	public EnumTrack getEnumTrack() {
		return enumTrack;
	}

	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		
	}
}
