package com.momnop.simplyconveyors.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class ItemConveyorResistanceBoots extends ItemArmor
{
	public ItemConveyorResistanceBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot, String unlocalizedName)
	{
		super(material, renderIndex, slot);
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(1);
		RenderRegistry.registry.add(this);
	}
}