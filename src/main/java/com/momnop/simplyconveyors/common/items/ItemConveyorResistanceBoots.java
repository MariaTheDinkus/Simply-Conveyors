package com.momnop.simplyconveyors.common.items;

import mcjty.lib.compat.CompatItemArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.common.info.ModInfo;

public class ItemConveyorResistanceBoots extends CompatItemArmor
{
	public ItemConveyorResistanceBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot, String unlocalizedName)
	{
		super(material, renderIndex, slot);
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyors.tabGeneral);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(1);
		SimplyConveyorsItems.register(this);
	}
	
	public void registerItemModel(Item i) {
		SimplyConveyors.proxy.registerItemRenderer(i, 0, this.getUnlocalizedName().substring(5));
	}
}