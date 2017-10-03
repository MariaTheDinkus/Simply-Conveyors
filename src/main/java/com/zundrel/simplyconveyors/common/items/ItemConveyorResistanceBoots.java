package com.zundrel.simplyconveyors.common.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;

import com.zundrel.simplyconveyors.SimplyConveyors;
import com.zundrel.simplyconveyors.common.info.ModInfo;

public class ItemConveyorResistanceBoots extends ItemArmor
{
	public ItemConveyorResistanceBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot, String unlocalizedName)
	{
		super(material, renderIndex, slot);
		setRegistryName(unlocalizedName);
		setCreativeTab(SimplyConveyors.tabGeneral);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setMaxStackSize(1);
	}
	
	public void registerItemModel(Item i) {
		SimplyConveyors.proxy.registerItemRenderer(i, 0, this.getUnlocalizedName().substring(5));
	}
}