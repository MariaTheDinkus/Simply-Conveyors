package com.momnop.simplyconveyors.compat;

import java.util.ArrayList;

import net.minecraftforge.fml.common.Loader;

public class ModCompatibility
{
    private static ArrayList<ICompatibility> compatibilities = new ArrayList<ICompatibility>();

    public static void registerModCompat()
    {
        compatibilities.add(new CompatibilityJustEnoughItems());
    }

    public static void loadCompat(ICompatibility.InitializationPhase phase)
    {
        for (ICompatibility compatibility : compatibilities)
            if (Loader.isModLoaded(compatibility.getModId()) && compatibility.enableCompat())
                compatibility.loadCompatibility(phase);
    }
}