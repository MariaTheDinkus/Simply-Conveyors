package com.momnop.simplyconveyors.items;

import mcjty.lib.compat.CompatItemArmor;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class ItemConveyorResistanceBoots extends CompatItemArmor
{
	public ItemConveyorResistanceBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot, String unlocalizedName)
	{
		super(material, renderIndex, slot);
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyors.tabGeneral);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(1);
		RenderRegistry.registry.add(this);
	}
}