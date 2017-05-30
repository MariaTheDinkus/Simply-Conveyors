package com.momnop.simplyconveyors.blocks.base;

import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;

import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class BlockNormal extends CompatBlock
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public BlockNormal(String unlocalizedName, Material material, float hardness, SoundType type, CreativeTabs tab)
	{
		super(material);
		setCreativeTab(tab);
		setHardness(hardness);
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		RenderRegistry.registry.add(this);
		setSoundType(type);
	}
}
