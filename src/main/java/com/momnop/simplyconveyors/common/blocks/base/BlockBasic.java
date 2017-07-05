package com.momnop.simplyconveyors.common.blocks.base;

import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.info.ModInfo;

public class BlockBasic extends CompatBlock
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public BlockBasic(String unlocalizedName, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(material);
		setCreativeTab(tab);
		setHardness(hardness);
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		setSoundType(type);
		SimplyConveyorsBlocks.register(this);
	}
	
	public void registerItemModel(ItemBlock ib) {
		SimplyConveyors.proxy.registerItemRenderer(ib, 0, this.getUnlocalizedName().substring(5));
	}
}
