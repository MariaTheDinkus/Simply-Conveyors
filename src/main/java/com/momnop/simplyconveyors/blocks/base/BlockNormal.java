package com.momnop.simplyconveyors.blocks.base;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.client.RenderRegistry;
import com.momnop.simplyconveyors.info.ModInfo;

public class BlockNormal extends BlockHorizontal
{
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
