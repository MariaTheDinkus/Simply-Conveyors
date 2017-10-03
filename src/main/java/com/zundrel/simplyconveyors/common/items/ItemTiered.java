package com.zundrel.simplyconveyors.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zundrel.simplyconveyors.SimplyConveyors;

public class ItemTiered extends ItemBasic
{
	public ItemTiered(String unlocalizedName, int maxStackSize)
	{
		super(unlocalizedName, maxStackSize);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		switch(stack.getMetadata()) {
		case 0:
			return super.getUnlocalizedName() + ".iron";
		case 1:
			return super.getUnlocalizedName() + ".gold";
		case 2:
			return super.getUnlocalizedName() + ".diamond";
		default:
			return super.getUnlocalizedName();	
		}
	}
	
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
		if (tab == SimplyConveyors.tabGeneral) {
			for (int i = 0; i < 3; i++) {
				list.add(new ItemStack(this, 1, i));
			}
		}
    }
	
	@Override
	public void registerItemModel(Item i) {
		for (int i2 = 0; i2 < 3; i2++) {
			SimplyConveyors.proxy.registerItemRenderer(i, i2, this.getUnlocalizedName().substring(5));
		}
	}
}