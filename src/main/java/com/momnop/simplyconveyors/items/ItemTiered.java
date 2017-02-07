package com.momnop.simplyconveyors.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;

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
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int i = 0; i < 3; i++) {
        	list.add(new ItemStack(itemIn, 1, i));
        }
    }
}