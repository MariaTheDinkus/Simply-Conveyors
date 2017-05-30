package com.momnop.simplyconveyors.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public void clGetSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (int i = 0; i < 3; i++) {
        	list.add(new ItemStack(itemIn, 1, i));
        }
    }
}