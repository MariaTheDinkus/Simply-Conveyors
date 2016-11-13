package com.momnop.simplyconveyors.compat;

import javax.annotation.Nonnull;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

import com.momnop.interdictionpillar.blocks.BlockHandler;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;

@JEIPlugin
public class SimplyConveyorsPlugin extends BlankModPlugin
{
    public static IJeiHelpers jeiHelper;

    @Override
    public void register(@Nonnull IModRegistry registry)
    {
        jeiHelper = registry.getJeiHelpers();
    }
}